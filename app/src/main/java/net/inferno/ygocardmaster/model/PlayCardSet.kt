package net.inferno.ygocardmaster.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayCardSet(
    @Json(name = "set_name")
    val name: String,

    @Json(name = "set_code")
    val code: String,

    @Json(name = "set_rarity")
    val rarity: String,

    @Json(name = "set_rarity_code")
    val rarityCode: String,

    @Json(name = "set_price")
    val price: Float,
)
