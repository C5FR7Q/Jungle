package com.example.myapplication.di

import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<App> {

	@Component.Builder
	abstract class Builder : AndroidInjector.Builder<App>()
}