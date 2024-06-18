package store.newsbriefing.app.feature.auth.signIn

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import store.newsbriefing.app.core.common.util.BriefingLogger
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.data.repository.MemberRepository
import store.newsbriefing.app.core.domain.SignInWithSocialProviderUseCase
import store.newsbriefing.app.core.model.SocialProvider
import javax.inject.Inject

sealed class SignInEvent {
    data object NavigateToMain : SignInEvent()
    data class ErrorOccurred(val message: String) : SignInEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val signInWithSocialProviderUseCase: SignInWithSocialProviderUseCase
) : ViewModel() {
    val eventFlow: EventFlow<SignInEvent>
        get() = _eventFlow.asEventFlow()
    private val _eventFlow: MutableEventFlow<SignInEvent> = MutableEventFlow()

    suspend fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken

                        signInWithSocialProviderUseCase(SocialProvider.GOOGLE, googleIdToken)
                        _eventFlow.emit(SignInEvent.NavigateToMain)
                    } catch (e: GoogleIdTokenParsingException) {
                        _eventFlow.emit(SignInEvent.ErrorOccurred("Failed to parse Google ID token"))
                    } catch (e: Exception) {
                        BriefingLogger.e("Failed to sign in with Google ID token: ${e.message}")
                        _eventFlow.emit(SignInEvent.ErrorOccurred("Failed to sign in with Google ID token"))
                    }
                } else {
                    _eventFlow.emit(SignInEvent.ErrorOccurred("Not supported credential type"))
                }
            }

            else -> {
                _eventFlow.emit(SignInEvent.ErrorOccurred("Not supported credential type"))
            }
        }
    }

}