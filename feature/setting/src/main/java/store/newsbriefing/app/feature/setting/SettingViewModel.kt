package store.newsbriefing.app.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.data.repository.MemberRepository
import store.newsbriefing.app.core.data.repository.MemberTokenRepository
import javax.inject.Inject

sealed class SettingEvent {
    data class ErrorOccurred(val message: String) : SettingEvent()
    data object Logout : SettingEvent()
    data object DeleteMember : SettingEvent()
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val memberTokenRepository: MemberTokenRepository
) : ViewModel() {
    val eventFlow: EventFlow<SettingEvent>
        get() = _eventFlow.asEventFlow()
    private val _eventFlow = MutableEventFlow<SettingEvent>()

    fun logout() = viewModelScope.launch {
        memberTokenRepository.clearMemberToken()
        _eventFlow.emit(SettingEvent.Logout)
    }

    fun deleteMember() = viewModelScope.launch {
        val userId = memberTokenRepository.getMemberToken().first().memberId
        memberRepository.deleteMember(userId)
        _eventFlow.emit(SettingEvent.DeleteMember)
    }
}