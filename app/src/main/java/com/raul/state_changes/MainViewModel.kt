package com.raul.state_changes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


enum class NavigationEvent {
    NavigateToProfile,NONE
}

data class LoginState(
    val loading : Boolean = false,
    val isLoggedIn  : Boolean = false
)

class MainViewModel : ViewModel() {


    var _navigationChannel = Channel<NavigationEvent>()
        private set
    val navChannelFlow = _navigationChannel.receiveAsFlow();


    var _sharedFlow = MutableSharedFlow<NavigationEvent>()
        private set

    val sharedFlow = _sharedFlow.asSharedFlow()



    var state by mutableStateOf(LoginState())






    fun login(){
        viewModelScope.launch(Dispatchers.IO) {
            println("Login in the thread ${Thread.currentThread().name}")
            state = state.copy(loading = true)
            delay(3000)
            // _navigationChannel.send(NavigationEvent.NavigateToProfile)
            //_sharedFlow.emit(NavigationEvent.NavigateToProfile)
            state = state.copy(loading = false, isLoggedIn = true)
        }
    }

    fun logout() {
        state = state.copy(isLoggedIn = false)
    }

}