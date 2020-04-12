package com.example.myapplication.app.screen.main

sealed class MainAction {
	data class ShowError(val error: String) : MainAction()
}