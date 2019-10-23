package com.cy.archlib.base

import androidx.lifecycle.*

abstract class BaseModel<T> : ViewModel(),LifecycleObserver{

    var data=MutableLiveData<T>()
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        onCleared()
    }
}