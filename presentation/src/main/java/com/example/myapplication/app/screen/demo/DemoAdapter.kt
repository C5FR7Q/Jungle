package com.example.myapplication.app.screen.demo

import com.example.myapplication.R
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder

class DemoAdapter : RendererRecyclerViewAdapter() {
	init {
		registerRenderer(
			ViewBinder<DemoModel>(
				R.layout.item_demo,
				DemoModel::class.java,
				ViewBinder.Binder { model, finder, _ ->
					finder.setText(R.id.demo_item_text, model.name)
				}
			)
		)
	}

	data class DemoModel(val name: String) : ViewModel
}