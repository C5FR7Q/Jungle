package com.example.myapplication.app.screen.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

	override val containerID = R.layout.fragment_main

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		main_recycler.layoutManager = LinearLayoutManager(requireContext())
		main_recycler.adapter = MainAdapter()
	}

}