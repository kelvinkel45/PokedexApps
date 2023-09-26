package com.vincode.pokedexapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vincode.pokedexapps.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")// ORDER BY name ASC
    fun getListPokemon(): LiveData<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()
}