package net.inferno.ygocardmaster.repo

import net.inferno.ygocardmaster.model.CardSet
import net.inferno.ygocardmaster.model.CardSetDetails
import net.inferno.ygocardmaster.model.PlayCard
import net.inferno.ygocardmaster.model.response.DataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YGOProService {
    @GET("cardsets.php")
    suspend fun getSets(
    ): List<CardSet>

    @GET("cardinfo.php")
    suspend fun getCardInSet(
        @Query("cardset") setName: String,
    ): DataResponse<List<PlayCard>>

    @GET("cardsetsinfo.php")
    suspend fun getSetDetails(
        @Query("setcode") setCode: String,
    ): CardSetDetails

    @GET("cardinfo.php")
    suspend fun getCardDetails(
        @Query("name") cardName: String,
    ): DataResponse<List<PlayCard>>

    @GET("cardinfo.php")
    suspend fun searchCards(
        @Query("fname") query: String,
    ): DataResponse<List<PlayCard>>
}