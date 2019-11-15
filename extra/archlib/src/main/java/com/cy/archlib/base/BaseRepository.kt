package com.cy.archlib.base

import com.cy.archlib.common.logi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

open class BaseRepository {


    suspend fun <T : Any> apiCall(call: suspend () -> T): T {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> ApiResult<T>): ApiResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            ApiResult.Error(Exception(e.message!!))
        }
    }

    suspend fun <T : Any> executeResponse(
        response: BaseRes<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): ApiResult<T> {
        return coroutineScope {

            if (response.code == 0) {
                successBlock?.let { it() }
                ApiResult.Success(response.data!!)
            } else {
                errorBlock?.let { it() }
                ApiResult.Error(IOException(response.message))
            }
        }
    }


    suspend fun <T : Any> executeResponse(
        response: Response<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): ApiResult<T> {
        return coroutineScope {
            //处理返回数据
            logi(BaseRepository::class, Thread.currentThread().name)
            logi(BaseRepository::class, "Success data ${response.body()}")
            if (response.isSuccessful) {
                successBlock?.let { it() }
                ApiResult.Success(response.body()!!)
            } else {
                errorBlock?.let { it() }
                ApiResult.Error(Exception(response.message()))
            }
        }
    }
}