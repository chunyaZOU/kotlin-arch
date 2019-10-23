package com.cy.archlib.retrofit.interceptors

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

abstract class ResponseBodyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val response = chain.proceed(request)
        response.body()?.let {
            val contentLength = it.contentLength()
            val source = it.source()
            source.request(Long.MAX_VALUE)
            var buffer = source.buffer()

            if (response.headers()["Content-Encoding"].equals("gzip", ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gzipSource ->
                    buffer = Buffer()
                    buffer.writeAll(gzipSource)
                }
            }

            val contentType = it.contentType()
            val charset = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
            } else {
                Charset.forName("UTF-8")
            }
            if (contentLength != 0L) {
                interceptor(response, url, buffer.clone().readString(charset))
            }
        }
        return response
    }

    abstract fun interceptor(response: Response, url: String, body: String): Response
}