package com.cy.kotlinarch.fragment.index

import com.cy.archlib.base.BasePresenter
import com.uber.autodispose.ScopeProvider
import io.reactivex.functions.Consumer

class IndexPresenter : BasePresenter<IndexModel, IndexView>() {

    override fun attachView(view: IndexView) {
        mView = view
    }

    override fun createModel() {
        mModel = IndexModel()
    }

    fun getData(scopeProvider: ScopeProvider) {

        mModel!!.getData(scopeProvider)
            .subscribe(Consumer<IndexData> {
                println("IndexPresenter$it")
            }, Consumer<Throwable> {
                println("IndexPresenter$it")
            })

    }

    fun getImgUrl(): String? {
        return mModel?.getImgUrl()
    }
}