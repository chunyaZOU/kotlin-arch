package com.cy.archlib.base

abstract class BasePresenter<BM : BaseModel, BV : BaseView> {

    constructor() {
        println("this is primary constructor")
    }

    init {
        println("this is init block")
        createModel()
    }

    var mModel: BM? = null
    var mView: BV? = null
    abstract fun attachView(view: BV)
    abstract fun createModel()
    open fun detachView() {
        mView = null
        mModel = null
    }
}