package net.inferno.ygocardmaster.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class CardSet(
    @Json(name = "set_name")
    val name: String,

    @Json(name = "set_code")
    val code: String,

    @Json(name = "num_of_cards")
    val numberOfCards: Int,

    @Json(name = "tcg_date")
    val releaseDate: Date?,
)
