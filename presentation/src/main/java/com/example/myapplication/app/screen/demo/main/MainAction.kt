package com.example.myapplication.app.screen.demo.main

sealed class MainAction {
	data class ShowError(val error: String) : MainAction()
}