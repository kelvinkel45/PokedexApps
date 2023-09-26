package com.vincode.pokedexapps.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincode.pokedexapps.R
import com.vincode.pokedexapps.databinding.ActivityMainBinding
import com.vincode.pokedexapps.utils.ViewModelFactory
import com.vincode.pokedexapps.data.Result
import com.vincode.pokedexapps.data.local.entity.PokemonEntity
import com.vincode.pokedexapps.ui.detail.DetailActivity
import com.vincode.pokedexapps.ui.detail.DetailActivity.Companion.POKEMON_NAME

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var pokemonAdapter : PokemonAdapter

    private lateinit var listSort : List<PokemonEntity>
    private var flagSort = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PokedexApps)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setUpRecyclerView()
        initObserver()
        initSearchOptionMenu()

    }

    private fun initSearchOptionMenu(){
        with(binding){
            //search menu
            searchView.setupWithSearchBar(searchBar)

            searchView.editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    pokemonAdapter.filter.filter(searchBar.text)
                    false
                }

            //sorting menu
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu1 ->{
                        flagSort = !flagSort
                        setUpAdapter(listSort, flagSort)
                        val text = if (flagSort) { "Sorting Ascending" } else { "Sorting Descending" }
                        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                        rvPokemon.scrollToPosition(0)
                    }
                }
                true
            }
        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun initObserver(){
        viewModel.getListPokemon().observe(this){results->
            if(results != null){
                when(results){
                    is Result.Loading -> onLoading()
                    is Result.Success ->{
                        setUpAdapter(results.data, flagSort)
                        onSuccess()
                    }
                    is Result.Error -> Toast.makeText(this, results.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setUpRecyclerView (){
        pokemonAdapter = PokemonAdapter{itemData,_->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(POKEMON_NAME, itemData.name)
            startActivity(intent)
        }
        binding.rvPokemon.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = pokemonAdapter
        }
    }

    private fun setUpAdapter(data: List<PokemonEntity>, flag : Boolean) {
        listSort = ArrayList()
        listSort = data

        if (flag){
            pokemonAdapter.setList(listSort.sortedWith { t, t2 ->
                if (t.name < t2.name) -1 else 0
            })
        }else{
            pokemonAdapter.setList(listSort.sortedWith { t, t2 ->
                if (t.name > t2.name) -1 else 0
            })
        }
    }

    private fun onLoading(){
        binding.apply {
            progressBar.visibility = VISIBLE
            rvPokemon.visibility = GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = GONE
            rvPokemon.visibility = VISIBLE
        }
    }

}