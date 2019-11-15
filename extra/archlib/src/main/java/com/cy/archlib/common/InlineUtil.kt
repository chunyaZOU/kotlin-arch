package com.cy.archlib.common

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cy.archlib.BuildConfig
import com.cy.archlib.common.GsonUtil.gson
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass

inline fun <reified T> parseBean(jsonString: String): T = gson.fromJson(jsonString, T::class.java)
inline fun <T> jsonString(json: T): String = gson.toJson(json)

val UI = Dispatchers.Main
val DEFAULT = Dispatchers.Default
val IO = Dispatchers.IO


inline fun <reified T : Any> logd(clazz: KClass<T>, str: String) {
    if (BuildConfig.DEBUG) Log.d(clazz.simpleName, str)
}

inline fun <reified T : Any> logi(clazz: KClass<T>, str: String) {
    if (BuildConfig.DEBUG) Log.i(clazz.simpleName, str)
}

inline fun <reified T : Any> logw(clazz: KClass<T>, str: String) {
    if (BuildConfig.DEBUG) Log.w(clazz.simpleName, str)
}

inline fun <reified T : Any> loge(clazz: KClass<T>, str: String) {
    if (BuildConfig.DEBUG) Log.e(clazz.simpleName, str)
}