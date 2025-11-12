package org.example.app.ui

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.example.app.R
import org.example.app.model.Match

/**
 * Adapter renders horizontally scrollable TV cards with focus behavior.
 */
class MatchAdapter(
    private val onClick: (Match) -> Unit
) : ListAdapter<Match, MatchViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Match, newItem: Match) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_match_card, parent, false)
        return MatchViewHolder(v, onClick)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MatchViewHolder(
    itemView: View,
    private val onClick: (Match) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val logoHome: ImageView = itemView.findViewById(R.id.logoHome)
    private val logoAway: ImageView = itemView.findViewById(R.id.logoAway)
    private val nameHome: TextView = itemView.findViewById(R.id.nameHome)
    private val nameAway: TextView = itemView.findViewById(R.id.nameAway)
    private val scoreHome: TextView = itemView.findViewById(R.id.scoreHome)
    private val scoreAway: TextView = itemView.findViewById(R.id.scoreAway)
    private val status: TextView = itemView.findViewById(R.id.status)

    private var current: Match? = null

    init {
        itemView.setOnClickListener { current?.let(onClick) }
        // Enter key triggers click
        itemView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)) {
                current?.let(onClick)
                true
            } else false
        }
    }

    fun bind(match: Match) {
        current = match
        nameHome.text = match.home.name
        nameAway.text = match.away.name
        scoreHome.text = match.score.home.toString()
        scoreAway.text = match.score.away.toString()
        status.text = match.status

        val placeholder = R.drawable.ic_placeholder
        logoHome.load(match.home.logoUrl) {
            crossfade(true)
            placeholder(placeholder)
            error(placeholder)
        }
        logoAway.load(match.away.logoUrl) {
            crossfade(true)
            placeholder(placeholder)
            error(placeholder)
        }
    }
}
