package com.cy.kotlinarch.api

import com.cy.kotlinarch.fragment.index.IndexData
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiService {
    @GET("users/chunyaZOU")
    fun index(): Observable<IndexData>
}