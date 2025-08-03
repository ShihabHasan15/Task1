package com.devsyncit.task1

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/v3/b/687374506063391d31aca23a")
    suspend fun getRecord(): Response<Record>

}