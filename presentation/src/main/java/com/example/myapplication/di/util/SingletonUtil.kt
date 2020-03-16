package com.example.myapplication.di.util

import android.app.Application
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A class that does something.
 *
 * This class has the [Singleton] scope. This means that the Application and all Activities,
 * Fragments, and child fragments and their dependencies will share the same instance of this class.
 */
@Singleton
class SingletonUtil @Inject constructor(private val application: Application) {

}