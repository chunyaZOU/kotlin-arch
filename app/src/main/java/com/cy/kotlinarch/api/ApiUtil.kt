package com.cy.kotlinarch.api

import com.cy.archlib.retrofit.RetrofitCreator
import retrofit2.Retrofit


object ApiUtil {


    private var mRetrofit: Retrofit? = null

    fun apiService(): ApiService {
        if (mRetrofit == null) throw NullPointerException("Retrofit is null")
        return mRetrofit!!.create(ApiService::class.java)
    }

    fun initApiService() {
        if (mRetrofit == null)
            mRetrofit = RetrofitCreator.retrofitInstance()
    }
}