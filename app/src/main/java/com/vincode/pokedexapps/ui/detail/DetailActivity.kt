package com.vincode.pokedexapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vincode.pokedexapps.R
import com.vincode.pokedexapps.data.Result
import com.vincode.pokedexapps.data.remote.response.AbilityResponses
import com.vincode.pokedexapps.data.remote.response.DetailDataResponse
import com.vincode.pokedexapps.databinding.ActivityDetailBinding
import com.vincode.pokedexapps.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object{
        const val POKEMON_NAME = "POKEMON_NAME"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra(POKEMON_NAME)

        initViewModel()
        pokemonName?.let { initObserverData(it) }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun initObserverData(pokemonName : String) {
        viewModel.getDetailPokemon(pokemonName).observe(this){
            if(it != null){
                when(it){
                    is Result.Error -> Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    is Result.Loading -> onLoading()
                    is Result.Success -> {
                        setDetailData(it.data)
                        onSuccess()
                    }
                }
            }
        }
    }

    private fun onLoading(){
        binding.apply {
            progressBar.visibility = VISIBLE
            imgSprites.visibility = GONE
            tvNamePokemon.visibility = GONE
            tvAbility.visibility = GONE
            cgAbility.visibility = GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = GONE
            imgSprites.visibility = VISIBLE
            tvNamePokemon.visibility = VISIBLE
            tvAbility.visibility = VISIBLE
            cgAbility.visibility = VISIBLE
        }
    }

    private fun setDetailData(data: DetailDataResponse) {
        binding.apply {
            tvNamePokemon.text = data.namePokemon
            Glide.with(imgSprites)
                .load(data.sprites.imageSprites)
                .centerCrop()
                .into(imgSprites)

            setChipAbility(cgAbility, data.abilities)
        }
    }

    private fun setChipAbility(cgAbility: ChipGroup, abilities: List<AbilityResponses>) {
        cgAbility.removeAllViews()
        for (text in abilities) {
            val chip =
                layoutInflater.inflate(R.layout.item_ability, cgAbility, false) as Chip
            chip.text = text.listAbility.nameAbility
            cgAbility.addView(chip)
        }
    }
}