package com.cy.kotlinarch.fragment.index

import com.cy.archlib.base.ApiResult
import com.cy.archlib.base.BaseRepository
import com.cy.archlib.base.BaseRes
import com.cy.archlib.common.logi
import com.cy.kotlinarch.api.Api.apiService

class IndexRepository : BaseRepository() {

    private lateinit var indexData: IndexBean

    private lateinit var reposData: List<Repos>

    suspend fun indexData0(): ApiResult<IndexBean> {
        return safeApiCall(
            call = { getData0() }
        )
    }

    private suspend fun getData0(): ApiResult<IndexBean> {
        val data = apiService.index0()
        logi(IndexRepository::class, "Success $data")
        return executeResponse(
            BaseRes(0, "请求成功", data),
            { indexData = data }
        )
    }

    suspend fun indexData1(): ApiResult<IndexBean> {
        return safeApiCall(
            call = { getData1() }
        )
    }


    private suspend fun getData1(): ApiResult<IndexBean> {
        val dataCall = apiService.index1()
        logi(IndexRepository::class, "Success $dataCall")
        return executeResponse(dataCall)
    }

    suspend fun reposData(): ApiResult<List<Repos>> {
        return safeApiCall(
            call = { getReposData1() }
        )
    }

    private suspend fun getReposData0(): ApiResult<List<Repos>> {
        val data = apiService.repos0()
        return executeResponse(
            BaseRes(0, "请求成功", data),
            { reposData = data }
        )
    }

    private suspend fun getReposData1(): ApiResult<List<Repos>> {
        val data = apiService.repos1()
        return executeResponse(data)
    }
}