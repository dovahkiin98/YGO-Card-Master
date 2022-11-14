package net.inferno.ygocardmaster.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardPrice(
    @Json(name = "cardmarket_price")
    val cardmarket: String?,

    @Json(name = "tcgplayer_price")
    val tcgplayer: String?,

    @Json(name = "ebay_price")
    val ebay: String?,

    @Json(name = "amazon_price")
    val amazon: String?,

    @Json(name = "coolstuffinc_price")
    val coolstuffinc: String?,
)
