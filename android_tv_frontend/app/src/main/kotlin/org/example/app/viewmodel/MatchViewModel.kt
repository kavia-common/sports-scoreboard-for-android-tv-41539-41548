package org.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import org.example.app.data.FirebaseRepository
import org.example.app.model.Match

/**
 * PUBLIC_INTERFACE
 * MatchViewModel exposes realtime matches and a selected match.
 */
class MatchViewModel(
    private val repo: FirebaseRepository = FirebaseRepository()
) : ViewModel() {

    // PUBLIC_INTERFACE
    val matches: StateFlow<List<Match>> =
        repo.observeMatches()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // PUBLIC_INTERFACE
    fun match(matchId: String): StateFlow<Match?> =
        repo.observeMatch(matchId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
