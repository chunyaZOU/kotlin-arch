package com.cy.archlib.base

data class BaseRes<T>(
    val code: Int,
    val message: String,
    val data: T?
)