package com.example.myapplication.app

import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.app.screen.demo.DemoFragment
import com.example.myapplication.base.BaseRouter

class MainRouter(fragmentManager: FragmentManager) : BaseRouter(fragmentManager) {
	override val layoutResId = R.id.activity_container

	fun openStartPage() {
		if (empty) {
			show(DemoFragment())
		}
	}
}