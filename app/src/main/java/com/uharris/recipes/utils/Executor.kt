package com.uharris.recipes.utils

interface Executor {
    suspend fun <T> launchInNetworkThread(block: suspend () -> T) : T
}