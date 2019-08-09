package com.cy.kotlinarch.fragment.index

import com.cy.kotlinarch.api.ApiUtil
import com.cy.archlib.base.BaseModel
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class IndexModel : BaseModel {

    fun getData(scopeProvider: ScopeProvider): ObservableSubscribeProxy<IndexData> {
        return ApiUtil.apiService().index()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
    }

    fun getImgUrl(): String {
        return "http://attach.bbs.miui.com/forum/201312/03/165620x7cknad7vruvec1z.jpg"
    }
}