package com.example.myapplication.app

import android.app.Activity
import com.example.myapplication.app.screen.demo.DemoFragment
import com.example.myapplication.base.BaseActivityModule
import com.example.myapplication.di.inject.PerActivity
import com.example.myapplication.di.inject.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [BaseActivityModule::class])
abstract class MainActivityModule {

	@PerFragment
	@ContributesAndroidInjector/*(modules = [DemoFragmentModule::class])*/
	abstract fun demoFragmentInjector(): DemoFragment

	/**
	 * As per the contract specified in [BaseActivityModule]; "This must be included in all
	 * activity modules, which must provide a concrete implementation of [Activity]."
	 *
	 * @param mainActivity the activity
	 * @return the activity
	 */
	@Binds
	@PerActivity
	abstract fun activity(mainActivity: MainActivity): Activity

	/**
	 * The main activity listens to the events in the [DemoFragment].
	 *
	 * @param mainActivity the activity
	 * @return the main fragment listener
	 */

	@Module
	companion object {
		@JvmStatic
		@Provides
		@PerActivity
		fun mainRouter(mainActivity: MainActivity): MainRouter = MainRouter(mainActivity, mainActivity.supportFragmentManager)
	}

}