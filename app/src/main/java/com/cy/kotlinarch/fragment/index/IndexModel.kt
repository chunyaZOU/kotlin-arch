package com.cy.kotlinarch.fragment.index

import com.cy.archlib.base.BaseModel
import com.cy.kotlinarch.repository.NetRepository


class IndexModel : BaseModel<IndexData>() {

    fun indexData(){

        NetRepository.getData().subscribe {
            data.postValue(it)
        }
    }

}