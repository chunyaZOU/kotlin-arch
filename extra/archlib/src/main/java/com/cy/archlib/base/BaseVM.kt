package com.cy.archlib.base

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.cy.archlib.common.IO
import com.cy.archlib.common.UI
import com.cy.archlib.common.logi
import kotlinx.coroutines.*

abstract class BaseVM<T : BaseRepository> : ViewModel(), LifecycleObserver {

    private val mException: MutableLiveData<Throwable> = MutableLiveData()

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block }
    }

   /* fun launchOnIO(block: suspend CoroutineScope.() -> T):Job{
        return viewModelScope.launch(IO) { block }
    }*/

    suspend fun <T> withOnUI(block: suspend CoroutineScope.() -> T) {
        withContext(UI) { block }
    }

    suspend fun <T> withOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(IO) { block }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handlerCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handlerCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        handlerCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handlerCancellationExceptionManually)
        }
    }

    fun tryCatch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }


    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handlerCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock
            } catch (e: Throwable) {
                if (e !is CancellationException || handlerCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock
            }
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        logi(BaseVM::class, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        logi(BaseVM::class, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        logi(BaseVM::class, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        logi(BaseVM::class, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        logi(BaseVM::class, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        logi(BaseVM::class, "onDestroy")
        destroy()
    }

    open fun create() {}
    open fun start() {}
    open fun resume() {}
    open fun pause() {}
    open fun stop() {}
    @CallSuper
    open fun destroy() {
        viewModelScope.cancel()
        onCleared()
    }
}


