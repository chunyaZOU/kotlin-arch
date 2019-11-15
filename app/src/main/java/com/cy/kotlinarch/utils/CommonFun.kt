package com.cy.kotlinarch.utils

import android.widget.Toast
import com.cy.kotlinarch.App


inline fun toastShort(str: String) = Toast.makeText(App.sCtx, str, Toast.LENGTH_SHORT).show()

inline fun toastLong(str: String) = Toast.makeText(App.sCtx, str, Toast.LENGTH_LONG).show()