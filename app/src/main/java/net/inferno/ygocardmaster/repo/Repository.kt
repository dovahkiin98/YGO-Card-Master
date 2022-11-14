package net.inferno.ygocardmaster.repo

import net.inferno.ygocardmaster.model.CardSet
import net.inferno.ygocardmaster.model.PlayCard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getSets(): List<CardSet> {
        return remoteDataSource.getSets()
    }

    suspend fun getCardsInSet(setName: String): List<PlayCard> {
        return remoteDataSource.getCardInSet(setName).data
    }

    suspend fun getCardDetails(cardName: String): PlayCard {
        return remoteDataSource.getCardDetails(cardName).data.first()
    }

    suspend fun searchCards(query: String) : List<PlayCard> {
        return remoteDataSource.searchCards(query).data
    }
}