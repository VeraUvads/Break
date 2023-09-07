package com.uva.fastapp.domain

import com.uva.fastapp.data.RemoteDataSource
import com.uva.fastapp.model.CatDto

class CatRepository(private val remoteDatasource: RemoteDataSource) {

    fun uploadCats(): List<CatDto> {
        return remoteDatasource.uploadCats()
    }
}
