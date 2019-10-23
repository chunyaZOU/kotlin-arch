package com.cy.kotlinarch.repository

import com.cy.kotlinarch.api.Api
import com.cy.kotlinarch.fragment.index.IndexData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetRepository {
    companion object{
        fun getData(): Observable<IndexData> {
            return Api.apiService.index()
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
        }
    }
}