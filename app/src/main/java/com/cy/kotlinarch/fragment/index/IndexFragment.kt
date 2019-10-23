package com.cy.kotlinarch.fragment.index

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cy.kotlinarch.R
import com.cy.archlib.base.BaseFragment
import com.cy.kotlinarch.utils.VMProvider
import kotlinx.android.synthetic.main.fragment_index.img
import kotlinx.android.synthetic.main.fragment_index.tv


class IndexFragment : BaseFragment() {

    private lateinit var arg0: String
    private lateinit var arg1: String

    companion object {
        fun instance(arg0: String, arg1: String): IndexFragment {
            val fragment = IndexFragment()
            val bundle = Bundle()
            bundle.putString("arg0", arg0)
            bundle.putString("arg1", arg1)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arg0 = arguments?.getString("arg0")!!
        arg1 = arguments?.getString("arg1")!!
    }

    override fun getLayout(): Int {
        return R.layout.fragment_index
    }

    override fun initView() {
        tv.text = "Hello Kotlin"
    }

    override fun initData() {

        val indexModel = VMProvider.of(IndexModel::class.java)
        indexModel.data.observe(this, Observer {
            tv.text = it.avatar_url

            Glide.with(this)
                .load("http://attach.bbs.miui.com/forum/201312/03/165620x7cknad7vruvec1z.jpg")
                .circleCrop()
                .into(img)
        })


        tv.setOnClickListener {
            indexModel.indexData()
        }
    }
}