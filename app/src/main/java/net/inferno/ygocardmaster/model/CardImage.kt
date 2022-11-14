package net.inferno.ygocardmaster.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardImage(
    @Json(name = "id")
    val id: Int,

    @Json(name = "image_url")
    val urlLarge: String,

    @Json(name = "image_url_small")
    val urlSmall: String,
)
