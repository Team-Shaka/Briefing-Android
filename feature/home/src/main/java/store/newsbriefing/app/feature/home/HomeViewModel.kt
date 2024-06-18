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
import store.newsbriefing.app.core.model.BriefingCategoryArticles
import javax.inject.Inject

sealed class HomeEvent {
    data class ErrorOccurred(val message: String) : HomeEvent()
}

data class HomeUiState(
    val articles: HashMap<BriefingArticleCategory, BriefingArticleUiState>
)

sealed interface BriefingArticleUiState {
    data object None : BriefingArticleUiState
    data class Success(val categoryArticles: BriefingCategoryArticles) : BriefingArticleUiState
    data object Error : BriefingArticleUiState
    data object Loading : BriefingArticleUiState
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
                BriefingArticleCategory.SOCIAL to BriefingArticleUiState.None,
                BriefingArticleCategory.SCIENCE to BriefingArticleUiState.None,
                BriefingArticleCategory.GLOBAL to BriefingArticleUiState.None,
                BriefingArticleCategory.ECONOMY to BriefingArticleUiState.None
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
        val isLoading = _uiState.value.articles[category] is BriefingArticleUiState.Loading
        val isLoaded = _uiState.value.articles[category] is BriefingArticleUiState.Success

        if (isLoading || (isLoaded && !isRefresh)) {
            return@launch
        }

        briefingRepository.getBriefingArticleSummaries(
            briefingArticleCategory = category
        ).onStart {
            _uiState.update {
                it.copy(
                    articles = HashMap(it.articles.toMutableMap().apply {
                        this[category] = BriefingArticleUiState.Loading
                    })
                )
            }
        }.collect { summaries ->
            _uiState.update {
                it.copy(
                    articles = HashMap(it.articles.toMutableMap().apply {
                        this[category] = BriefingArticleUiState.Success(
                            categoryArticles = summaries
                        )
                    })
                )
            }
        }
    }
}