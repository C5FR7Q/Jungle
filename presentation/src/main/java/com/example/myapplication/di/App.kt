package com.example.myapplication.di

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Any>

	override fun onCreate() {
		super.onCreate()
		DaggerAppComponent.builder().create(this).inject(this)
	}

	override fun androidInjector(): AndroidInjector<Any> = androidInjector
}