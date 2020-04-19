package com.example.myapplication.app

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.app.screen.demo.DemoFragment
import com.example.myapplication.base.BaseRouter

class MainRouter(context: Context, fragmentManager: FragmentManager) : BaseRouter(context, fragmentManager) {
	override val layoutResId = R.id.activity_container

	fun openStartPage() {
		if (empty) {
			show(DemoFragment())
		}
	}
}