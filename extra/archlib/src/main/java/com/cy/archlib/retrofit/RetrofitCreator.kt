package com.cy.archlib.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class RetrofitCreator {

    companion object {

        fun retrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(initOKHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        private fun initOKHttp(): OkHttpClient {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(NoHostnameVerifier())
                .sslSocketFactory(trustAll())
                .addNetworkInterceptor(loggingInterceptor)
                .build()
        }

        private fun trustAll(): SSLSocketFactory {
            val trustAllCerts = arrayOf(X509Manager())
            trustAllCerts[0] = X509Manager()
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, trustAllCerts, SecureRandom())
            return sc.socketFactory
        }
    }

    private class X509Manager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }

    }

    private class NoHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            return true
        }
    }
}