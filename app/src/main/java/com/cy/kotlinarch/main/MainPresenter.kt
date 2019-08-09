package com.cy.kotlinarch.main

import com.cy.archlib.base.BasePresenter

class MainPresenter : BasePresenter<MainModel, MainView>() {
    override fun attachView(view: MainView) {
        mView = view
    }

    override fun createModel() {
        mModel = MainModel()
    }

    fun getData(): String? {
        return mModel?.getData()
    }

    fun getImgUrl(): String {
        return "https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b5e4c905865494ee982209191df4e0e1/c2cec3fdfc03924590b2a9b58d94a4c27d1e2500.jpg"
    }

}