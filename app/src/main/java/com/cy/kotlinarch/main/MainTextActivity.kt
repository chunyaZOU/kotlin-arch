package com.cy.kotlinarch.main

import android.view.WindowManager
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cy.archlib.base.BaseActivity
import com.cy.kotlinarch.R
import com.cy.kotlinarch.fragment.index.IndexFragment
import com.cy.kotlinarch.utils.VMProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainTextActivity : BaseActivity() {


    companion object {
        private const val TAG = "MainActivity"
    }

    val mainModel = VMProvider.of(MainModel::class.java)

    override fun beforeContentView() {
        super.beforeContentView()
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        val fragment: IndexFragment = IndexFragment.instance("", "")
        addFragment(fragment, R.id.container)

        tv.text = mainModel.data.value?.name + "\n" + mainModel.data.value?.desc

        Glide.with(this)
            .load("https://oscimg.oschina.net/oscnet/2400a17723e230344e744131c40faf98b87.jpg")
            .into(img)
    }

    override fun initData() {


        mainModel.data.observe(this,
            Observer<MainInfo> { t ->
                tv.text = t?.name + "\n" + t?.desc
            })
        tv.setOnClickListener {
            mainModel.updateData()
        }
    }
}