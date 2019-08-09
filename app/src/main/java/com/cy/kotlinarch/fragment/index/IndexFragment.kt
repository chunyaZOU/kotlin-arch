package com.cy.kotlinarch.fragment.index

import android.os.Bundle
import com.bumptech.glide.Glide
import com.cy.kotlinarch.R
import com.cy.archlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_index.*


class IndexFragment : BaseFragment<IndexPresenter>(), IndexView {

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

    override fun initPresenter() {
        super.initPresenter()
        mPresenter = IndexPresenter()
        mPresenter.attachView(this)
    }

    override fun initData() {

        tv.text = "Get Data"
        tv.setOnClickListener {
            mPresenter.getData(mScopeProvider)
        }
        val imgUrl = mPresenter.getImgUrl()
        imgUrl?.let {
            Glide.with(this)
                .load(it)
                .into(img)
        }
    }


}