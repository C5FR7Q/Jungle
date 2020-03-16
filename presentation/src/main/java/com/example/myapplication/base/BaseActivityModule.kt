package com.example.myapplication.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.myapplication.di.inject.PerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of [Activity].
 */
@Module
abstract class BaseActivityModule {

	@Module
	companion object {
		private const val ACTIVITY_FRAGMENT_MANAGER = "BaseActivityModule.activityFragmentManager"

		/*
		 * This is a valid way to declare static provides methods.
		 * See https://github.com/google/dagger/issues/900#issuecomment-337043187
		 */
		@JvmStatic
		@Provides
		@Named(ACTIVITY_FRAGMENT_MANAGER)
		@PerActivity
		fun activityFragmentManager(activity: AppCompatActivity): FragmentManager = activity.supportFragmentManager
	}

	@Binds
	@PerActivity
	abstract fun activityContext(activity: AppCompatActivity): Context
}