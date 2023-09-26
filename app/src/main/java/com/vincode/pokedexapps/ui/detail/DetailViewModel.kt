package com.vincode.pokedexapps.ui.detail

import androidx.lifecycle.ViewModel
import com.vincode.pokedexapps.data.DataRepository

class DetailViewModel (private val dataRepository: DataRepository) : ViewModel() {

    fun getDetailPokemon(pokemonName : String) = dataRepository.getDetailPokemon(pokemonName)
}