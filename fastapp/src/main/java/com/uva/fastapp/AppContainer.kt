package com.uva.fastapp

import com.uva.fastapp.data.RemoteDataSource
import com.uva.fastapp.domain.CatRepository

object AppContainer {


    private val dataSource by lazy {
        RemoteDataSource()
    }
    val catRepository by lazy {
        CatRepository(dataSource)
    }
}
