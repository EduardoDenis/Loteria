package com.eduardodenis.loteria.data

class BetRepository(private val betDao: BetDao) {

    suspend fun getBets(type: String): List<Bet> {
        return betDao.getNumberByType(type)
    }

    suspend fun insertBet(bet: Bet) {
        betDao.insert(bet)
    }

    suspend fun deleteHistoric(betType: String) {
        betDao.deleteHistoric(betType)
    }


    companion object {
        private var instance: BetRepository? = null

        fun getInstance(betDao: BetDao): BetRepository {
            return instance ?: BetRepository(betDao).also {
                instance = it
            }
        }
    }
}