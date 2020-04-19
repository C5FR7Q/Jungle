package com.example.myapplication.app.screen.demo

sealed class DemoAction {
	data class ShowError(val error: String) : DemoAction()
}