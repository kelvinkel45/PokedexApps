package com.vincode.pokedexapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vincode.pokedexapps.data.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun dao(): PokemonDao

    companion object {
        @Volatile
        private var instance: PokemonDatabase? = null
        fun getInstance(context: Context): PokemonDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java, "Pokemon.db"
                ).build()
            }
    }
}