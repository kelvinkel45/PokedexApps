package com.vincode.pokedexapps.data.remote.network

import com.vincode.pokedexapps.data.remote.response.DataResponse
import com.vincode.pokedexapps.data.remote.response.DetailDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon")
    suspend fun getListPokemon() : DataResponse

    @GET("pokemon/{pokemon_name}")
    suspend fun getDetailPokemon (
        @Path("pokemon_name") pokemonName : String
    ) : DetailDataResponse
}