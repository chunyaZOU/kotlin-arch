package com.cy.kotlinarch

import android.app.Application
import android.content.Context
import com.cy.kotlinarch.api.ApiUtil

class App :Application() {

    companion object{
        lateinit var sCtx:Context
    }

    init {
        sCtx=this
    }

    override fun onCreate() {
        super.onCreate()
        ApiUtil.initApiService()
    }
}