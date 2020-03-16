package com.example.myapplication.app.screen.main

import com.example.myapplication.R
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder

class MainAdapter : RendererRecyclerViewAdapter() {
	init {
		registerRenderer(
			ViewBinder<MainModel>(
				R.layout.item_main,
				MainModel::class.java,
				ViewBinder.Binder { model, finder, _ ->
					finder.setText(R.id.main_item_text, model.name)
				}
			)
		)
	}

	data class MainModel(val name: String) : ViewModel
}