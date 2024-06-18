package store.newsbriefing.app.feature.newsdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.data.repository.BriefingRepository
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import store.newsbriefing.app.core.data.repository.ScrapRepository
import store.newsbriefing.app.core.model.BriefingArticle
import javax.inject.Inject

sealed class NewsDetailEvent {
    data class ErrorOccurred(val message: String) : NewsDetailEvent()
}

data class NewsDetailUiState(
    val article: BriefingArticleUiState = BriefingArticleUiState.Loading
)

sealed interface BriefingArticleUiState {
    data class Success(val data: BriefingArticle) : BriefingArticleUiState
    data object Error : BriefingArticleUiState
    data object Loading : BriefingArticleUiState
}

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val briefingRepository: BriefingRepository,
    private val scrapRepository: ScrapRepository,
    private val memberTokenRepository: MemberTokenRepository
) : ViewModel() {

    private val newsDetailArgs = NewsDetailArgs(savedStateHandle)
    private val newsId = newsDetailArgs.newsId.toLong()

    val uiState: StateFlow<NewsDetailUiState>
        get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<NewsDetailUiState> = MutableStateFlow(NewsDetailUiState())

    val eventFlow: EventFlow<NewsDetailEvent>
        get() = _eventFlow.asEventFlow()
    private val _eventFlow: MutableEventFlow<NewsDetailEvent> = MutableEventFlow()

    init {
        viewModelScope.launch {
            loadBriefingArticle(newsId)
        }
    }

    private fun loadBriefingArticle(id: Long) = viewModelScope.launch {
        briefingRepository.getBriefingArticle(articleId = id).collect { news ->
            _uiState.update {
                it.copy(
                    article = BriefingArticleUiState.Success(news)
                )
            }
        }
    }

    fun setScrap(id: Long) {
        viewModelScope.launch {
            val memberId = memberTokenRepository.getMemberToken().first().memberId
            scrapRepository.setScrap(memberId, id)
            _uiState.update { currentState ->
                currentState.copy(
                    article = when (val currentNews = currentState.article) {
                        is BriefingArticleUiState.Success ->
                            currentNews.copy(data = currentNews.data.copy(isScrap = true))
                        else -> currentNews
                    }
                )
            }
        }
    }

    fun unScrap(id: Long) {
        viewModelScope.launch {
            val memberId = memberTokenRepository.getMemberToken().first().memberId
            scrapRepository.unScrap(memberId, id)
            _uiState.update { currentState ->
                currentState.copy(
                    article = when (val currentNews = currentState.article) {
                        is BriefingArticleUiState.Success ->
                            currentNews.copy(data = currentNews.data.copy(isScrap = false))
                        else -> currentNews
                    }
                )
            }
        }
    }
}