package org.example.app.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.example.app.model.Highlight
import org.example.app.model.Match
import org.example.app.model.Score
import org.example.app.model.Team

/**
 * PUBLIC_INTERFACE
 * FirebaseRepository provides realtime Flow streams of matches under "/matches".
 * It maps Firebase DataSnapshot into strongly-typed models with null safety.
 */
class FirebaseRepository {

    private val db by lazy {
        // Uses default FirebaseApp - requires google-services.json placed in app module.
        FirebaseDatabase.getInstance().reference
    }

    /**
     * PUBLIC_INTERFACE
     * Observe all matches in realtime.
     */
    fun observeMatches(): Flow<List<Match>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Match>()
                for (child in snapshot.children) {
                    parseMatch(child)?.let { result.add(it) }
                }
                trySend(result.toList()).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                // Emit empty on error; UI can show error banner.
                trySend(emptyList()).isSuccess
            }
        }
        val ref = db.child("matches")
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    /**
     * PUBLIC_INTERFACE
     * Observe a single match by ID.
     */
    fun observeMatch(matchId: String): Flow<Match?> = callbackFlow {
        val ref = db.child("matches").child(matchId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(parseMatch(snapshot)).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(null).isSuccess
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    private fun parseMatch(node: DataSnapshot): Match? {
        val id = node.key ?: return null

        val homeName = node.child("home/name").getValue(String::class.java).orEmpty()
        val homeLogo = node.child("home/logoUrl").getValue(String::class.java)

        val awayName = node.child("away/name").getValue(String::class.java).orEmpty()
        val awayLogo = node.child("away/logoUrl").getValue(String::class.java)

        val homeScore = node.child("score/home").getValue(Int::class.java) ?: 0
        val awayScore = node.child("score/away").getValue(Int::class.java) ?: 0

        val status = node.child("status").getValue(String::class.java).orEmpty()

        val highlightTitle = node.child("highlight/title").getValue(String::class.java)
        val highlightUrl = node.child("highlight/videoUrl").getValue(String::class.java)
        val highlight = if (highlightTitle != null || highlightUrl != null) {
            Highlight(highlightTitle, highlightUrl)
        } else null

        return Match(
            id = id,
            home = Team(homeName, homeLogo),
            away = Team(awayName, awayLogo),
            score = Score(homeScore, awayScore),
            status = status,
            highlight = highlight
        )
    }
}
