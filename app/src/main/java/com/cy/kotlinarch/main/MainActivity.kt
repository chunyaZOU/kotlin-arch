package com.cy.kotlinarch.main

import android.content.Intent
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cy.archlib.base.BaseActivity
import com.cy.kotlinarch.R
import com.cy.kotlinarch.fragment.index.IndexFragment
import com.cy.kotlinarch.utils.AppUtil
import com.cy.kotlinarch.utils.lastChar

import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun beforeContentView() {
        super.beforeContentView()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        val fragment: IndexFragment = IndexFragment.instance("", "")

        addFragment(fragment, R.id.container)

        tv.text = "Hello Kotlin"
        Glide.with(this)
            .load("https://oscimg.oschina.net/oscnet/2400a17723e230344e744131c40faf98b87.jpg")
            .into(img)
    }

    override fun initPresenter() {
        super.initPresenter()
        mPresenter = MainPresenter()
        mPresenter.attachView(this)
    }

    override fun initData() {
        Observable.interval(0, 5, TimeUnit.SECONDS)
            .autoDisposable(mLifecycleScopeProvider)
            .subscribe {
                println(TAG + it)
            }

        val data = mPresenter.getData()
        tv.text = data

        val imgUrl = mPresenter.getImgUrl()

        Glide.with(this)
            .load(imgUrl)
            .into(img)

        tv.setOnClickListener {
            if (AppUtil.isNoUseOption()) {
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                startActivity(intent)
            } else Toast.makeText(this, "No Usage Option", Toast.LENGTH_LONG).show()

        }


        "asdfsaf".lastChar()
    }
}