package com.cy.kotlinarch.fragment.index

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cy.archlib.base.ApiResult
import com.cy.archlib.base.BaseVM
import com.cy.archlib.common.DEFAULT
import com.cy.archlib.common.UI
import com.cy.archlib.common.logi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IndexVM : BaseVM<IndexRepository>() {

    private val repository by lazy { IndexRepository() }


    val dataIndexBean = MutableLiveData<IndexBean>()
    val dataReposBean = MutableLiveData<List<Repos>>()

    fun index0() {

        viewModelScope.launch(DEFAULT) {
            val data = repository.indexData0()
            withContext(UI) {
                when (data) {
                    is ApiResult.Success -> {
                        logi(IndexVM::class, "Success ${Thread.currentThread().name}")
                        dataIndexBean.postValue(data.data)
                    }
                    is ApiResult.Error -> logi(IndexVM::class, "Error ${Thread.currentThread().name}")
                }
            }
        }
    }


    fun index1() {
        viewModelScope.launch(DEFAULT) {
            val data = repository.indexData1()
            withContext(UI) {
                when (data) {
                    is ApiResult.Success -> {
                        logi(IndexVM::class, "Success ${Thread.currentThread().name}")
                        dataIndexBean.postValue(data.data)
                    }
                    is ApiResult.Error -> logi(IndexVM::class, "Error ${Thread.currentThread().name}")
                }
            }
        }
    }

    fun repos(){
        viewModelScope.launch(DEFAULT) {
            val data=repository.reposData()

            withContext(UI) {
                when (data) {
                    is ApiResult.Success -> {
                        logi(IndexVM::class, "Success ${Thread.currentThread().name}")
                        dataReposBean.postValue(data.data)
                    }
                    is ApiResult.Error -> logi(IndexVM::class, "Error ${Thread.currentThread().name}")
                }
            }
        }
    }
}
