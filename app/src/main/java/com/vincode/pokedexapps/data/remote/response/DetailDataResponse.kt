package com.vincode.pokedexapps.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailDataResponse(

    @field:SerializedName("abilities")
    val abilities: List<AbilityResponses>,

    @field:SerializedName("name")
    val namePokemon: String,

    @field:SerializedName("sprites")
    val sprites : SpritesResponse
)

data class AbilityResponses(
    @field:SerializedName("ability")
    val listAbility: ItemAbilityResponse
)

data class ItemAbilityResponse(

    @field:SerializedName("name")
    val nameAbility: String
)

data class SpritesResponse(
    @field:SerializedName("front_default")
    val imageSprites : String
)
