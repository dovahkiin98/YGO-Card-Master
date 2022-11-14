package net.inferno.ygocardmaster.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

enum class BanState {
    BANNED,
    LIMITED,
    SEMI_LIMITED,
    ;

    override fun toString() = when (this) {
        BANNED -> "Banned"
        LIMITED -> "Limited"
        SEMI_LIMITED -> "Semi-Limited"
    }

    companion object {
        fun fromString(name: String?) = when (name) {
            "Banned" -> BANNED
            "Limited" -> LIMITED
            "Semi-Limited" -> SEMI_LIMITED
            else -> null
        }
    }

    object Adapter {
        @ToJson
        fun toJson(type: BanState): String = type.toString()

        @FromJson
        fun fromJson(value: String?): BanState? = BanState.fromString(value)
    }
}