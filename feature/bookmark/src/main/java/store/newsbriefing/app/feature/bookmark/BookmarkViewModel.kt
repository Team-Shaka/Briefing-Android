package store.newsbriefing.app.feature.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.domain.LoadBriefingScrapArticleUseCase
import store.newsbriefing.app.core.model.BriefingScrapArticle
import javax.inject.Inject

sealed class BookmarkEvent {
    data class ErrorOccurred(val message: String) : BookmarkEvent()
}

data class BookmarkUiState(
    val articles: BookmarkArticleUiState = BookmarkArticleUiState.Loading
)

sealed interface BookmarkArticleUiState {
    data class Success(val data: List<BriefingScrapArticle>) : BookmarkArticleUiState
    data object Error : BookmarkArticleUiState
    data object Loading : BookmarkArticleUiState
}

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val loadBriefingScrapArticleUseCase: LoadBriefingScrapArticleUseCase
) : ViewModel() {

    val uiState: StateFlow<BookmarkUiState>
        get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<BookmarkUiState> = MutableStateFlow(BookmarkUiState())

    val eventFlow: EventFlow<BookmarkEvent>
        get() = _eventFlow.asEventFlow()
    private val _eventFlow: MutableEventFlow<BookmarkEvent> = MutableEventFlow()

    init {
        viewModelScope.launch {
            try {
                loadBriefingScrapArticleUseCase().collect {
                    _uiState.update { currentState ->
                        currentState.copy(
                            articles = BookmarkArticleUiState.Success(it)
                        )
                    }
                }
            } catch (e: Exception) {
                _eventFlow.emit(BookmarkEvent.ErrorOccurred(e.toString()))
            }
        }
    }
}