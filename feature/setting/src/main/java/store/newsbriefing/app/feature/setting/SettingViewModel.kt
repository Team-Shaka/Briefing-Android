package store.newsbriefing.app.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import store.newsbriefing.app.core.domain.DeleteMemberUseCase
import javax.inject.Inject

sealed class SettingEvent {
    data class ErrorOccurred(val message: String) : SettingEvent()
    data object Logout : SettingEvent()
    data object DeleteMember : SettingEvent()
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val memberTokenRepository: MemberTokenRepository
) : ViewModel() {
    val eventFlow: EventFlow<SettingEvent>
        get() = _eventFlow.asEventFlow()
    private val _eventFlow = MutableEventFlow<SettingEvent>()

    fun logout() = viewModelScope.launch {
        try {
            memberTokenRepository.clearMemberToken()
            _eventFlow.emit(SettingEvent.Logout)
        } catch (e: Exception) {
            _eventFlow.emit(SettingEvent.ErrorOccurred(e.toString()))
        }
    }

    fun deleteMember() = viewModelScope.launch {
        try {
            deleteMemberUseCase()
            _eventFlow.emit(SettingEvent.DeleteMember)
        } catch (e: Exception) {
            _eventFlow.emit(SettingEvent.ErrorOccurred(e.toString()))
        }
    }
}