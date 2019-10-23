package com.cy.archlib.retrofit

import retrofit2.Retrofit


object ApiUtil {



    private var mRetrofit: Retrofit? = null

    fun <T> apiService(clazz: Class<T>): T {
        if (mRetrofit == null) throw NullPointerException("Retrofit is null")
        return mRetrofit!!.create(clazz)
    }

    fun initApiService() {
        if (mRetrofit == null)
            mRetrofit = RetrofitCreator.retrofitInstance()
    }
}