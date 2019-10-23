package com.cy.kotlinarch.main

import com.cy.archlib.base.BaseModel

class MainModel : BaseModel<MainInfo>(){




    fun updateData() {
        var mainInfo=MainInfo()
        mainInfo.name="MainPage"
        mainInfo.desc="This is MainPage"
        data.postValue(mainInfo)
    }

    override fun onCleared() {
        super.onCleared()
    }

}