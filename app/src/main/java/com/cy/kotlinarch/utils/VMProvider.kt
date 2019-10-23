package com.cy.kotlinarch.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VMProvider {
    companion object {
        fun <T : ViewModel> of(clazz: Class<T>): T {
            return ViewModelProvider.NewInstanceFactory().create(clazz);
        }
    }
}