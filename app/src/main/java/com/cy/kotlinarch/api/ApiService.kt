package com.cy.kotlinarch.api

import com.cy.kotlinarch.fragment.index.IndexBean
import com.cy.kotlinarch.fragment.index.Repos
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("users/chunyaZOU")
    suspend fun index0(): IndexBean

    @GET("users/chunyaZOU")
    suspend fun index1(): Response<IndexBean>

    @GET("users/chunyaZOU/repos")
    suspend fun repos0(): List<Repos>

    @GET("users/chunyaZOU/repos")
    suspend fun repos1(): Response<List<Repos>>
}