package com.cy.archlib.retrofit.interceptors

import android.text.TextUtils
import okhttp3.Response
import org.json.JSONObject
import java.lang.Exception

class HandleResponseInterceptor : ResponseBodyInterceptor() {
    override fun interceptor(response: Response, url: String, body: String): Response {
        /*var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
            //url可处理个别接口返回

            if (jsonObject != null) {
                if (jsonObject.optInt("code") != 200) {
                    if (jsonObject.has("msg") && !TextUtils.isEmpty(jsonObject.optString("msg")))
                        throw RuntimeException(jsonObject.getString("msg"))
                    else throw RuntimeException("No Message")
                }
            }
        } catch (e: Exception) {
            print(e)
        }*/
        return response
    }
}