package com.vincode.pokedexapps.data.remote.response

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @field:SerializedName("results")
    val listData : List<ItemResponse>
)

data class ItemResponse(

    @field:SerializedName("name")
    val name: String
)

