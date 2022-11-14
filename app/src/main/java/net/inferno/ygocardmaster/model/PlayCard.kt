package net.inferno.ygocardmaster.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayCard(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "desc")
    val description: String,

    @Json(name = "atk")
    val attackPoints: Int?,

    @Json(name = "def")
    val defensePoints: Int?,

    @Json(name = "level")
    val level: Int?,

    @Json(name = "race")
    val race: String,

    @Json(name = "attribute")
    val attribute: String?,

    @Json(name = "card_sets")
    val sets: List<PlayCardSet> = listOf(),

    @Json(name = "card_images")
    val images: List<CardImage>,

    @Json(name = "card_prices")
    val prices: List<CardPrice> = listOf(),

    @Json(name = "banlist_info")
    val banlistInfo: BanlistInfo?,
)
