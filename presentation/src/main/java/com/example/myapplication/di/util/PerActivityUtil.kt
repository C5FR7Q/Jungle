package com.example.myapplication.di.util

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.di.inject.PerActivity
import javax.inject.Inject

/**
 * A class that does something.
 *
 * This class has the [PerActivity] scope. This means that the Activity and all of its
 * Fragments and child fragments and their dependencies will share the same instance of this class.
 * However, different activity instances will have their own instances.
 *
 * This is not available at the Application.
 */
@PerActivity
class PerActivityUtil @Inject constructor(private val activity: AppCompatActivity) {
}