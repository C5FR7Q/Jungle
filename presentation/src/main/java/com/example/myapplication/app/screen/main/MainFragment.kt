package com.example.myapplication.app.screen.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.country.GetCountriesInteractor
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment() {

	@Inject
	lateinit var getCountriesInteractor: GetCountriesInteractor

	override val containerID = R.layout.fragment_main

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		main_recycler.layoutManager = LinearLayoutManager(requireContext())
		main_recycler.adapter = MainAdapter()
	}

	override fun onResume() {
		super.onResume()
		val uiEvents: Observable<MainEvent> =
			RxView.clicks(main_load).map { MainEvent.Load }

		val state: Observable<MainState> = uiEvents.flatMap {
			getCountriesInteractor.countries.map<MainState> { MainState.Success(it) }
				.onErrorReturn { MainState.Fail(it.message ?: "No message of error") }
				.observeOn(AndroidSchedulers.mainThread())
				.startWith(MainState.Loading)
		}

		state.subscribe({
			Log.d("VVA", "subscribe called: $it")
			render(it)
		}, {
			Log.d("VVA", "error called: $it")
		})
	}

	private fun render(state: MainState) {
		Log.d("VVA", "render state: $state")
		when (state) {
			is MainState.Loading -> {
				main_load.visibility = View.GONE
				main_progress.visibility = View.VISIBLE
			}
			is MainState.Success -> {
				main_progress.visibility = View.GONE
				main_recycler.visibility = View.VISIBLE
				(main_recycler.adapter as? MainAdapter)?.let { adapter ->
					adapter.setItems(state.countries.map { MainAdapter.MainModel(it.name) })
					adapter.notifyDataSetChanged()
				}
			}
			is MainState.Fail -> {
				main_progress.visibility = View.GONE
				main_load.visibility = View.VISIBLE
			}
		}
	}

}