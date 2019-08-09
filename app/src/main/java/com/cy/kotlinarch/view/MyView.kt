package com.cy.kotlinarch.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class MyView: View{
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initView()
    }

    private fun initView(){
        setBackgroundColor(Color.GRAY)
    }
}