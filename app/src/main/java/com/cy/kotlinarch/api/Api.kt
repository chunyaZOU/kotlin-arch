package com.cy.kotlinarch.api

import com.cy.archlib.retrofit.ApiUtil

object Api {
    val apiService=ApiUtil.apiService(ApiService::class.java)
}