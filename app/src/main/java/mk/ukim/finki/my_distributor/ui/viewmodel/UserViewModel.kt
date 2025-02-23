package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.repository.UserRepository
import mk.ukim.finki.my_distributor.domain.UserDto

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository(RetrofitClient.userApiService)

    private val _userResponse = MutableLiveData<UserDto>()
    val userResponse: LiveData<UserDto> get() = _userResponse


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getUserByEmail(email: String, password: String){
        viewModelScope.launch {
            userRepository.getUserByEmail(email)
                .onSuccess { _userResponse.postValue(it) }
                .onFailure { _error.postValue(it.message) }
        }
    }
}