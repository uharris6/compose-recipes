package com.uharris.recipes.domain

abstract class UseCase<out T, in Params> {
    abstract suspend fun run(params: Params): T

    suspend operator fun invoke(params: Params) : T  = run(params)
}