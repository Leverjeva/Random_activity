package com.example.m16_architecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m16_architecture.domain.GetUsefulActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getUsefulActivityUseCase: GetUsefulActivityUseCase) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    init{
        viewModelScope.launch {
            try {
                val responseVM = getUsefulActivityUseCase.execute().activity
                _state.value = State.Loaded(responseVM)
            } catch (e: Exception) {
                _error.send(e.toString())
                _state.value = State.Error
            }
        }
    }

    fun reloadUsefulActivity(){
        viewModelScope.launch {
            _state.value = State.Loading
            try{
                val responseVM = getUsefulActivityUseCase.execute().activity
                _state.value = State.Loaded (responseVM)
            } catch (e: Exception) {
                _error.send(e.toString())
                _state.value =State.Error
            }
        }
    }
}