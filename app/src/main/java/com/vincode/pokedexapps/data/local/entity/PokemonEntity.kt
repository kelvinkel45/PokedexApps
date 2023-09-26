package com.vincode.pokedexapps.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
class PokemonEntity (
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name: String
)