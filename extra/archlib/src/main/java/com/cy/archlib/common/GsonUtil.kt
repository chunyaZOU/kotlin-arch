package com.cy.archlib.common

import com.google.gson.Gson

object GsonUtil {
    val gson = Gson().apply {
        this.newBuilder().create()
    }
}