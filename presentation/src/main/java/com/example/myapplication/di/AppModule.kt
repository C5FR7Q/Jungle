package com.example.myapplication.di

import android.app.Application
import com.example.data.country.CountryRepositoryImpl
import com.example.domain.country.CountryRepository
import com.example.domain.country.GetCountriesInteractor
import com.example.domain.country.GetCountriesInteractorImpl
import com.example.myapplication.app.MainActivity
import com.example.myapplication.app.MainActivityModule
import com.example.myapplication.di.inject.PerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Provides application-wide dependencies.
 */
@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {

	@Binds
	abstract fun application(app: App): Application

	@PerActivity
	@ContributesAndroidInjector(modules = [MainActivityModule::class])
	abstract fun mainActivityInjector(): MainActivity

	@Binds
	abstract fun getCountriesInteractor(getCountriesInteractor: GetCountriesInteractorImpl): GetCountriesInteractor

	@Binds
	abstract fun countryRepository(countryRepositoryImpl: CountryRepositoryImpl): CountryRepository
}