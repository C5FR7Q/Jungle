package com.example.myapplication.base

import androidx.fragment.app.FragmentManager

abstract class BaseRouter(private val fragmentManager: FragmentManager) {
	protected abstract val layoutResId: Int

	//	TODO: Make it private?
	protected val empty: Boolean
		get() = backStack.isEmpty()

	protected fun show(fragment: BaseFragment) {
		fragmentManager.beginTransaction()
			.replace(layoutResId, fragment, getTag(fragment))
			.addToBackStack(getTag(fragment))
			.commit()
	}

	private fun getTag(fragment: BaseFragment) = fragment.javaClass.simpleName

	private val backStack: List<String>
		get() {
			val count = fragmentManager.backStackEntryCount
			val backStack = mutableListOf<String>()
			for (i in 0 until count) {
				backStack.add(fragmentManager.getBackStackEntryAt(i).name!!)
			}
			return backStack
		}
}
