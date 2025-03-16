package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.AuthRepository
import mk.ukim.finki.my_distributor.domain.dto.LoginResponseDto
import mk.ukim.finki.my_distributor.util.decodeJwtToken

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponseDto>()
    val loginResponse: LiveData<LoginResponseDto> get() = _loginResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password)
                .onSuccess { response ->
                    userPreferences.saveToken(response.token)
                    val decoded = decodeJwtToken(response.token)

                    _loginResponse.postValue(response)
                }
                .onFailure { exception ->
                    _error.postValue(exception.message)
                }
        }
    }
}