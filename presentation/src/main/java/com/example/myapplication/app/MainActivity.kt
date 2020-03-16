package com.example.myapplication.app

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

	@Inject
	lateinit var router: MainRouter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (savedInstanceState == null) {
			router.openStartPage()
		}
	}
}
