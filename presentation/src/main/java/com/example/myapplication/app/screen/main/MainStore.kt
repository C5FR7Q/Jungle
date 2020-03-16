package com.example.myapplication.app.screen.main

import com.example.domain.country.Country
import com.example.myapplication.base.mvi.EventMapper
import com.example.myapplication.base.mvi.MiddlewareAssembler
import com.example.myapplication.base.mvi.Reducer
import com.example.myapplication.base.mvi.Store
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class MainStore @Inject constructor(
	private val countryMiddleware: CountryMiddleware,
	foregroundScheduler: Scheduler,
	backgroundScheduler: Scheduler,
	eventMapper: EventMapper<MainEvent, InputAction>
) : Store<MainEvent, MainStore.InputAction, MainStore.InternalAction, MainState>(
	foregroundScheduler,
	backgroundScheduler,
	eventMapper
) {

	sealed class InputAction {
		object Load : InputAction()
	}

	sealed class InternalAction {
		object Loading : InternalAction()
		data class Loaded(val countries: List<Country>) : InternalAction()
		data class Failed(val error: String) : InternalAction()
	}

	override fun createMiddlewareAssembler() = object : MiddlewareAssembler<InputAction, InternalAction, MainState>() {
		override fun Observable<InputAction>.splitByMiddleware(lastState: MainState): List<Observable<out InternalAction>> {
			return listOf(
				bind(countryMiddleware)
			)
		}
	}

	override fun createReducer(): Reducer<MainState, InternalAction> = object : Reducer<MainState, InternalAction> {
		override val initialState = MainState()
		override fun reduce(state: MainState, internalAction: InternalAction) = when (internalAction) {
			is InternalAction.Loading -> state.copy(loading = true, errorMessage = "")
			is InternalAction.Loaded -> state.copy(loading = false, countries = internalAction.countries)
			is InternalAction.Failed -> state.copy(loading = false, errorMessage = internalAction.error)
		}
	}
}