package com.vincode.pokedexapps.di

import android.content.Context
import com.vincode.pokedexapps.data.DataRepository
import com.vincode.pokedexapps.data.local.room.PokemonDatabase
import com.vincode.pokedexapps.data.remote.network.ApiClient

object Injection {

    fun provideRepository(context: Context): DataRepository {
        val apiService = ApiClient.getApiService()
        val database = PokemonDatabase.getInstance(context)
        val dao = database.dao()
        return DataRepository.getInstance(apiService, dao)
    }
}