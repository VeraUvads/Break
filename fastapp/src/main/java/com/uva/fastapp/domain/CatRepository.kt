package com.uva.fastapp.domain

import com.uva.fastapp.data.RemoteDataSource
import com.uva.fastapp.model.CatDto

class CatRepository(private val remoteDatasource: RemoteDataSource) {

    suspend fun uploadCats(limit: Int): List<CatDto> {
        return remoteDatasource.uploadCats(limit)
    }

}
