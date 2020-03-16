package com.example.myapplication.di.util

import androidx.fragment.app.Fragment
import com.example.myapplication.base.BaseFragmentModule
import com.example.myapplication.di.inject.PerFragment
import javax.inject.Inject
import javax.inject.Named

/**
 * A class that does something.
 *
 * This class has the [PerFragment] scope. This means that the Fragment and all of its child
 * fragments and their dependencies will share the same instance of this class. However, different
 * fragment instances will have their own instances.
 *
 * This is not available at the Activity and Application.
 */
@PerFragment
class PerFragmentUtil @Inject constructor(
	@param:Named(BaseFragmentModule.FRAGMENT)
	private val fragment: Fragment
) {

}