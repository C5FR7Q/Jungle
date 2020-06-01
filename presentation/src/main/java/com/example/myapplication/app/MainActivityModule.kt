package com.example.myapplication.app

import android.app.Activity
import com.example.myapplication.app.screen.demo.DemoFragment
import com.example.myapplication.di.inject.PerActivity
import com.example.myapplication.di.inject.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module()
abstract class MainActivityModule {

	@PerFragment
	@ContributesAndroidInjector
	abstract fun demoFragmentInjector(): DemoFragment

	@Binds
	@PerActivity
	abstract fun activity(mainActivity: MainActivity): Activity

	@Module
	companion object {
		@JvmStatic
		@Provides
		@PerActivity
		fun mainRouter(mainActivity: MainActivity): MainRouter = MainRouter(mainActivity.supportFragmentManager)
	}

}