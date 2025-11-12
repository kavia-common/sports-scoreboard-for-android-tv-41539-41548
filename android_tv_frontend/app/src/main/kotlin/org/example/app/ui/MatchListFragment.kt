package org.example.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.app.R
import org.example.app.viewmodel.MatchViewModel

/**
 PUBLIC_INTERFACE
 Fragment showing a horizontally scrollable row of live matches with TV focus.
 */
class MatchListFragment : Fragment() {

    private val viewModel: MatchViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view.findViewById(R.id.recyclerMatches)
        adapter = MatchAdapter { match ->
            val bundle = Bundle().apply { putString("matchId", match.id) }
            findNavController().navigate(R.id.matchDetailFragment, bundle)
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
        recycler.isFocusable = true

        // Auto focus first item when data arrives
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.matches.collectLatest { list ->
                adapter.submitList(list)
                if (list.isNotEmpty()) {
                    recycler.post {
                        recycler.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                    }
                }
            }
        }
    }
}
