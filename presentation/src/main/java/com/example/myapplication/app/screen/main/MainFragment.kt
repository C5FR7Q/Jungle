package com.example.myapplication.app.screen.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.base.mvi.MviView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment(), MviView<MainState> {

	override val containerID = R.layout.fragment_main

	@Inject
	lateinit var mainStore: MainStore

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		main_recycler.layoutManager = LinearLayoutManager(requireContext())
		main_recycler.adapter = MainAdapter()

		mainStore.run {
			attach(this@MainFragment)
			dispatchEvent(RxView.clicks(main_load).map { MainEvent.Load })
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		mainStore.detach()
	}

	override fun render(state: MainState) {
		main_load.visibility = if (state.loading) View.GONE else View.VISIBLE
		main_progress.visibility = if (!state.loading) View.GONE else View.VISIBLE
		main_recycler.visibility = if (state.countries.isNotEmpty()) View.GONE else View.VISIBLE
		(main_recycler.adapter as? MainAdapter)?.let { adapter ->
			adapter.setItems(state.countries.map { MainAdapter.MainModel(it.name) })
			adapter.notifyDataSetChanged()
		}
	}
}