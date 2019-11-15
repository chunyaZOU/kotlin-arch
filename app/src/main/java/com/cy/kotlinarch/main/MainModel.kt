package com.cy.kotlinarch.main

import androidx.lifecycle.MutableLiveData
import com.cy.archlib.base.BaseVM

class MainModel : BaseVM<MainRepository>() {

    private val mainInfo = MainInfo()
    val data = MutableLiveData<MainInfo>()
    fun updateData() {
        mainInfo.name = "MainPage"
        mainInfo.desc = "This is MainPage"
        data.postValue(mainInfo)
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun destroy() {
        super.destroy()
    }

}