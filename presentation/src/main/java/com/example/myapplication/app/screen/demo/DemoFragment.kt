package com.example.myapplication.app.screen.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.c5fr7q.jungle.MviView
import com.example.myapplication.R
import com.example.myapplication.app.screen.demo.store.DemoStore
import com.example.myapplication.base.BaseFragment
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_demo.*
import javax.inject.Inject

class DemoFragment : BaseFragment(), MviView<DemoState, DemoAction> {

	override val containerID = R.layout.fragment_demo

	@Inject
	lateinit var demoStore: DemoStore

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		demo_recycler.apply {
			layoutManager = LinearLayoutManager(requireContext())
			adapter = DemoAdapter()
		}

		demoStore.run {
			attach(this@DemoFragment)
			dispatchEventSource(RxView.clicks(demo_load).map { DemoEvent.Load })
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		demoStore.detach()
	}

	override fun render(state: DemoState) {
		demo_load.visibility = if (state.loading || state.countries.isNotEmpty()) View.GONE else View.VISIBLE
		demo_progress.visibility = if (!state.loading) View.GONE else View.VISIBLE
		demo_recycler.visibility = if (state.countries.isNotEmpty()) View.VISIBLE else View.GONE
		(demo_recycler.adapter as? DemoAdapter)?.let { adapter ->
			adapter.setItems(state.countries.map { DemoAdapter.DemoModel(it.name) })
			adapter.notifyDataSetChanged()
		}
	}

	override fun processAction(action: DemoAction) {
		when (action) {
			is DemoAction.ShowError -> Toast.makeText(requireContext(), action.error, Toast.LENGTH_SHORT).show()
		}
	}
}