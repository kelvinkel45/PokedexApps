package com.vincode.pokedexapps.ui.main

import androidx.lifecycle.ViewModel
import com.vincode.pokedexapps.data.DataRepository

class MainViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getListPokemon() = dataRepository.getListPokemon()

}