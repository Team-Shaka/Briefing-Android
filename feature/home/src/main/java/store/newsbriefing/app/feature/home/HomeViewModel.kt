package store.newsbriefing.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.data.repository.BriefingRepository
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingCategoryArticle
import javax.inject.Inject

sealed class HomeEvent {
    data class ErrorOccurred(val message: String) : HomeEvent()
}

data class HomeUiState(
    val articles: HashMap<BriefingArticleCategory, BriefingCategoryArticleUiState>
)

sealed interface BriefingCategoryArticleUiState {
    data object None : BriefingCategoryArticleUiState
    data class Success(val categoryArticles: BriefingCategoryArticle) : BriefingCategoryArticleUiState
    data object Error : BriefingCategoryArticleUiState
    data object Loading : BriefingCategoryArticleUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val briefingRepository: BriefingRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState>
        get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState(
            articles = hashMapOf(
                BriefingArticleCategory.SOCIAL to BriefingCategoryArticleUiState.None,
                BriefingArticleCategory.SCIENCE to BriefingCategoryArticleUiState.None,
                BriefingArticleCategory.GLOBAL to BriefingCategoryArticleUiState.None,
                BriefingArticleCategory.ECONOMY to BriefingCategoryArticleUiState.None
            )
        )
    )

    val eventFlow: EventFlow<HomeEvent>
        get() = _eventFlow.asEventFlow()
    private val _eventFlow: MutableEventFlow<HomeEvent> = MutableEventFlow()

    fun loadBriefings(
        category: BriefingArticleCategory,
        isRefresh: Boolean
    ) = viewModelScope.launch {
        val isLoading = _uiState.value.articles[category] is BriefingCategoryArticleUiState.Loading
        val isLoaded = _uiState.value.articles[category] is BriefingCategoryArticleUiState.Success

        if (isLoading || (isLoaded && !isRefresh)) {
            return@launch
        }

        try {
            briefingRepository.getBriefingArticleSummaries(
                briefingArticleCategory = category
            ).onStart {
                _uiState.update {
                    it.copy(
                        articles = HashMap(it.articles.toMutableMap().apply {
                            this[category] = BriefingCategoryArticleUiState.Loading
                        })
                    )
                }
            }.collect { summaries ->
                _uiState.update {
                    it.copy(
                        articles = HashMap(it.articles.toMutableMap().apply {
                            this[category] = BriefingCategoryArticleUiState.Success(
                                categoryArticles = summaries
                            )
                        })
                    )
                }
            }
        } catch (e: Exception) {
            _eventFlow.emit(HomeEvent.ErrorOccurred(e.toString()))
            _uiState.update {
                it.copy(
                    articles = HashMap(it.articles.toMutableMap().apply {
                        this[category] = BriefingCategoryArticleUiState.Error
                    })
                )
            }
        }
    }
}