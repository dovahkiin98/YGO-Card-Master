package net.inferno.ygocardmaster.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BanlistInfo(
    @Json(name = "ban_goat")
    val banGoat: BanState?,

    @Json(name = "ban_ocg")
    val banOCG: BanState?,

    @Json(name = "ban_tcg")
    val banTCG: BanState?,
)