package com.example.m16_architecture.presentation

sealed class State {
    object Loading: State()
    data class Loaded(var response: String): State()
    object Error: State()
}