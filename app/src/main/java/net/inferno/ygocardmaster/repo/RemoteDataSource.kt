package net.inferno.ygocardmaster.repo

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.inferno.ygocardmaster.model.CardSet
import net.inferno.ygocardmaster.model.CardSetDetails
import net.inferno.ygocardmaster.model.PlayCard
import net.inferno.ygocardmaster.model.response.DataResponse
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSource @Inject constructor(
    private val remoteService: YGOProService,
) : YGOProService {
    override suspend fun getSets(): List<CardSet> {
        return remoteService.getSets()
    }

    override suspend fun getCardInSet(setName: String): DataResponse<List<PlayCard>> {
        return remoteService.getCardInSet(setName)
    }

    override suspend fun getSetDetails(setCode: String): CardSetDetails {
        return remoteService.getSetDetails(setCode)
    }

    override suspend fun getCardDetails(cardName: String): DataResponse<List<PlayCard>> {
        return remoteService.getCardDetails(cardName)
    }

    override suspend fun searchCards(query: String): DataResponse<List<PlayCard>> {
        return remoteService.searchCards(query)
    }
}