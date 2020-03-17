package com.example.myapplication.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager

abstract class BaseRouter(private val context: Context, private val fragmentManager: FragmentManager) {
	protected abstract val layoutResId: Int

//	TODO: Make it private?
	protected val empty: Boolean
		get() = backStack.isEmpty()

	protected fun show(fragment: BaseFragment, showAsDialog: Boolean = false) {
		if (showAsDialog) {
			showAsDialog(fragment)
		} else {
			showEmbedded(fragment)
		}
	}

	protected fun <T : BaseFragment> wasOpened(clazz: Class<T>) = backStack.contains(getTag(clazz))

	protected fun <T : BaseFragment> isOpened(clazz: Class<T>) =
		backStack.takeIf { it.isNotEmpty() }?.let { it.last() == getTag(clazz) } ?: false

	private fun <T : BaseFragment> getTag(clazz: Class<T>) = clazz.simpleName

	private fun getTag(fragment: BaseFragment) = getTag(fragment.javaClass)

	private fun showEmbedded(fragment: BaseFragment) {
		fragmentManager.beginTransaction()
			.replace(layoutResId, fragment, getTag(fragment))
			.addToBackStack(getTag(fragment))
			.commit()
	}

	private fun showAsDialog(fragment: BaseFragment) {
		fragment.show(fragmentManager, getTag(fragment))
	}

	private val backStack: List<String>
		get() {
			val count = fragmentManager.backStackEntryCount
			val backStack = mutableListOf<String>()
			for (i in 0 until count) {
				backStack.add(fragmentManager.getBackStackEntryAt(i).name!!)
			}
			return backStack
		}





	fun showToast(message: String) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
	}
}
