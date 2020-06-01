package com.example.myapplication.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : DialogFragment(), HasAndroidInjector {

	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Any>

	abstract val containerID: Int

	@SuppressWarnings("DEPRECATION")
	override fun onAttach(activity: Activity) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			AndroidSupportInjection.inject(this)
		}
		super.onAttach(activity)
	}

	override fun onAttach(context: Context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			AndroidSupportInjection.inject(this)
		}
		super.onAttach(context)
	}

	override fun androidInjector() = androidInjector

	final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(containerID, container, false)
	}
}