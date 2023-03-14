package com.develpouk.niu.match.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develpouk.niu.dateformatter.repository.DateFormatterRepository
import com.develpouk.niu.match.model.MatchModel
import com.develpouk.niu.match.repository.MatchRepository
import com.develpouk.niu.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val dateFormatterRepository: DateFormatterRepository,
) : ViewModel() {

    private val _matchesResponse =
        MutableStateFlow<NetworkResponse<List<MatchModel>>>(NetworkResponse.Loading)
    val matchesResponse = _matchesResponse.asStateFlow()

    private val _matchesH2HResponse =
        MutableStateFlow<NetworkResponse<List<MatchModel>>>(NetworkResponse.Loading)
    val matchesH2HResponse = _matchesH2HResponse.asStateFlow()



    private val exception = CoroutineExceptionHandler { _, _ ->
        _matchesResponse.value = NetworkResponse.Loading
        _matchesResponse.value = NetworkResponse.Error("Try Again Later")
    }

    fun getFormattedDays(dayDifference: Int) =
        dateFormatterRepository.getFormattedDays(dayDifference)

    fun getMatches(dayDifference: Int) {
        viewModelScope.launch(exception) {
            _matchesResponse.emit(NetworkResponse.Loading)
            val matches = matchRepository.getMatches(
                dateFormatterRepository.getFormattedDateMatches(dayDifference))
            _matchesResponse.emit(NetworkResponse.Success(matches.response))
        }
    }

    fun getHead2HeadMatches(teamsId: String) {
        viewModelScope.launch(exception) {
            _matchesH2HResponse.emit(NetworkResponse.Loading)
            val head2headMatches = matchRepository.getHead2HeadMatches(teamsId)
            _matchesH2HResponse.emit(NetworkResponse.Success(head2headMatches.response))
        }

    }
}