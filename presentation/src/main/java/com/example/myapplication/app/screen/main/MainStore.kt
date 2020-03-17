package com.example.myapplication.app.screen.main

import com.example.domain.country.Country
import com.example.myapplication.app.MainRouter
import com.example.myapplication.base.mvi.ActionProcessor
import com.example.myapplication.base.mvi.MiddlewareAssembler
import com.example.myapplication.base.mvi.Reducer
import com.example.myapplication.base.mvi.Store
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MainStore @Inject constructor(
	private val countryMiddleware: CountryMiddleware,
	private val mainRouter: MainRouter,
	@Named("foregroundScheduler") foregroundScheduler: Scheduler,
	@Named("backgroundScheduler") backgroundScheduler: Scheduler,
	mainEventMapper: MainEventMapper
) : Store<MainEvent, MainStore.InputAction, MainStore.InternalAction, MainState>(
	foregroundScheduler,
	backgroundScheduler,
	mainEventMapper
) {

	sealed class InputAction {
		object Load : InputAction()
		data class NotifyLoadFailed(val error: String) : InputAction()
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
			is InternalAction.Loading -> state.copy(loading = true)
			is InternalAction.Loaded -> state.copy(loading = false, countries = internalAction.countries)
			is InternalAction.Failed -> state.copy(loading = false)
		}
	}

	override fun createSideEffectProcessor() = object : ActionProcessor<InternalAction> {
		override fun processAction(action: InternalAction) {
			when (action) {
				is InternalAction.Failed -> dispatchAction(InputAction.NotifyLoadFailed(action.error))
			}
		}
	}

	override fun createDirectActionProcessor() = object : ActionProcessor<InputAction> {
		override fun processAction(action: InputAction) {
			when (action) {
				is InputAction.NotifyLoadFailed -> mainRouter.showToast(action.error)
			}
		}
	}
}