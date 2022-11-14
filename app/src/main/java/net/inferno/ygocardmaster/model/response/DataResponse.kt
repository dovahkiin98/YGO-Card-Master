package net.inferno.ygocardmaster.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DataResponse<T>(
    @Json(name = "data")
    val data: T,
    val meta: MetaData?,
)


@JsonClass(generateAdapter = true)
class MetaData {

}
