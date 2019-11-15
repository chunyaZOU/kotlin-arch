package com.cy.kotlinarch.fragment.index

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.cy.kotlinarch.R
import com.cy.archlib.base.BaseFragment
import com.cy.kotlinarch.main.MainTextActivity
import com.cy.kotlinarch.utils.VMProvider
import com.cy.kotlinarch.utils.toastLong
import com.cy.kotlinarch.utils.toastShort
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class IndexFragment : BaseFragment() {

    private val indexVM = VMProvider.of(IndexVM::class.java)
    private var tvId: Int = R.id.tv0


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
        arguments?.let {
        }
    }

    override fun getLayout() = R.layout.fragment_index


    override fun initView() {

        lifecycle.addObserver(indexVM)

        tv0.setOnClickListener {
            tvId = it.id
            indexVM.index0()
        }

        tv1.setOnClickListener {
            tvId = it.id
            indexVM.index1()
        }
        tv2.setOnClickListener {

            launch {
                indexVM.repos()
                delay(500)
                startActivity(Intent(activity, MainTextActivity::class.java))
                activity?.finish()
            }

        }

        indexVM.dataIndexBean.observe(this, Observer {
            Log.i("IndexFragment0", Thread.currentThread().name)
            when (tvId) {
                R.id.tv0 -> tv0.text = it.login
                R.id.tv1 -> tv1.text = it.gists_url
            }
        })

        indexVM.dataReposBean.observe(this, Observer {
            Log.i("IndexFragment1", Thread.currentThread().name)
            tv2.text = it[0].full_name
        })
    }

    override fun initData() {
    }
}