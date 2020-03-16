package com.example.myapplication.app

import android.app.Activity
import com.example.myapplication.app.screen.main.MainFragment
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
	@ContributesAndroidInjector/*(modules = [MainFragmentModule::class])*/
	abstract fun mainFragmentInjector(): MainFragment

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
	 * The main activity listens to the events in the [MainFragment].
	 *
	 * @param mainActivity the activity
	 * @return the main fragment listener
	 */

	@Module
	companion object {
		@JvmStatic
		@Provides
		@PerActivity
		fun mainRouter(mainActivity: MainActivity): MainRouter = MainRouter(mainActivity.supportFragmentManager)
	}

}