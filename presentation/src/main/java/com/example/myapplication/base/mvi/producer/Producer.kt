package com.example.myapplication.base.mvi.producer

interface Producer<Input, Output> {
	fun produce(input: Input): Output
}