package org.example.app.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.app.R
import org.example.app.viewmodel.MatchViewModel
import androidx.media3.ui.PlayerView
import android.widget.TextView

/**
 PUBLIC_INTERFACE
 Fragment that shows a scoreboard and plays highlight video if available.
 */
class MatchDetailFragment : Fragment() {

    private val viewModel: MatchViewModel by viewModels()
    private var player: ExoPlayer? = null

    private lateinit var title: TextView
    private lateinit var score: TextView
    private lateinit var status: TextView
    private lateinit var playerView: PlayerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = view.findViewById(R.id.detailTitle)
        score = view.findViewById(R.id.detailScore)
        status = view.findViewById(R.id.detailStatus)
        playerView = view.findViewById(R.id.playerView)

        val matchId = requireArguments().getString("matchId") ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.match(matchId).collectLatest { match ->
                if (match != null) {
                    title.text = "${match.home.name} vs ${match.away.name}"
                    score.text = "${match.score.home} - ${match.score.away}"
                    status.text = match.status

                    val url = match.highlight?.videoUrl
                    if (!url.isNullOrBlank()) {
                        ensurePlayer()
                        player?.apply {
                            setMediaItem(MediaItem.fromUri(Uri.parse(url)))
                            prepare()
                            playWhenReady = true
                        }
                        playerView.player = player
                    }
                }
            }
        }
    }

    private fun ensurePlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(requireContext()).build()
        }
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playerView.player = null
        player?.release()
        player = null
    }
}
