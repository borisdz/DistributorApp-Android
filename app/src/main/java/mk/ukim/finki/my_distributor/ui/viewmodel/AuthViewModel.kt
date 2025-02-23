package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.repository.AuthRepository
import mk.ukim.finki.my_distributor.domain.LoginResponseDto

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository(RetrofitClient.authApiService)

    private val _loginResponse = MutableLiveData<LoginResponseDto>()
    val loginResponse: LiveData<LoginResponseDto> get() = _loginResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(email: String, password: String){
        viewModelScope.launch {
            authRepository.login(email,password)
                .onSuccess { _loginResponse.postValue(it) }
                .onFailure { _error.postValue(it.message) }
        }
    }
}