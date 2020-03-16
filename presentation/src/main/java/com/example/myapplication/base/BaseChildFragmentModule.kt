package com.example.myapplication.base

import com.example.myapplication.base.BaseChildFragmentModule.Companion.CHILD_FRAGMENT
import dagger.Module

/**
 * Provides base fragment dependencies. This must be included in all child fragment modules, which
 * must provide a concrete implementation of the child [android.app.Fragment] and named
 * [CHILD_FRAGMENT].
 *
 * Note that a different [javax.inject.Named] for the [android.app.Fragment] is required in order to
 * remove any ambiguity about which fragment is being provided in a child fragment. For example,
 * we have parent fragment P and child fragment C. Parent fragment P will provide the Fragment
 * reference using the [BaseFragmentModule.FRAGMENT] name. Child fragment C will provide the
 * Fragment reference using the [CHILD_FRAGMENT] name.
 *
 * If the parent and child fragments are not uniquely named, then the child fragment and its
 * dependencies will not know which Fragment is provided to it. It could be the parent fragment
 * or the child fragment. Hence the ambiguity, which causes a compile error of
 * "android.app.Fragment is bound multiple times".
 */
@Module
abstract class BaseChildFragmentModule {

	companion object {
		/**
		 * See class documentation regarding the need for this name.
		 */
		const val CHILD_FRAGMENT = "BaseChildFragmentModule.childFragment"
	}
}