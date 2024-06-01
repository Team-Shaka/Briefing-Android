package store.newsbriefing.app.feature.auth.signIn

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.EventFlow
import store.newsbriefing.app.core.common.util.MutableEventFlow
import store.newsbriefing.app.core.common.util.asEventFlow
import store.newsbriefing.app.core.ui.BuildConfig
import java.util.UUID
import javax.inject.Inject

sealed class SignInEvent {
    object NavigateToMain : SignInEvent()
    data class ErrorOccurred(val message: String) : SignInEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
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
                        Log.d("SignInViewModel-c", googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        _eventFlow.emit(SignInEvent.ErrorOccurred("Failed to parse Google ID token"))
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