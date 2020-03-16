package com.example.myapplication.di.util

import androidx.fragment.app.Fragment
import com.example.myapplication.base.BaseChildFragmentModule
import com.example.myapplication.di.inject.PerChildFragment
import javax.inject.Inject
import javax.inject.Named

/**
 * A class that does something.
 *
 * This class has the [PerChildFragment] scope. This means that the child Fragment (a fragment
 * inside a fragment that is added using Fragment.getChildFragmentManager()) and all of its
 * dependencies will share the same instance of this class. However, different child fragments
 * instances will have their own instances of this class.
 *
 * This is not available at the parent Fragment, Activity, and Application.
 */
@PerChildFragment
class PerChildFragmentUtil @Inject constructor(
	@param:Named(BaseChildFragmentModule.CHILD_FRAGMENT)
	private val childFragment: Fragment
) {

}