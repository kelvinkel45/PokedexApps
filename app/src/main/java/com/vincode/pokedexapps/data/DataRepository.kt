package com.vincode.pokedexapps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.vincode.pokedexapps.data.local.entity.PokemonEntity
import com.vincode.pokedexapps.data.local.room.PokemonDao
import com.vincode.pokedexapps.data.remote.network.ApiService
import com.vincode.pokedexapps.data.remote.response.DetailDataResponse

class DataRepository private constructor(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao){

    fun getListPokemon(): LiveData<Result<List<PokemonEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getListPokemon()
            val listData = response.listData
            val pokemonList = listData.map {
                PokemonEntity(
                    it.name
                )
            }
            pokemonDao.deleteAll()
            pokemonDao.insertData(pokemonList)
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
        val localData : LiveData<Result<List<PokemonEntity>>> = pokemonDao.getListPokemon().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getDetailPokemon(pokemonName: String) : LiveData<Result<DetailDataResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailPokemon(pokemonName)
            emit(Result.Success(response))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: PokemonDao
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(apiService, newsDao)
            }.also { instance = it }
    }

}