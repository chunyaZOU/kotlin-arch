package com.cy.archlib.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseActivity<P : BasePresenter<*, *>> : AppCompatActivity() {

    // use it in initPresenter （start in onCreate & stop in onDestroy）
    val mLifecycleScopeProvider: AndroidLifecycleScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY) }
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeContentView()
        setContentView(getLayout())
        initView()
        initPresenter()
        initData()
    }

    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initData()
    @CallSuper
    open fun initPresenter() {
    }

    open fun beforeContentView() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun FragmentActivity.addFragment(fragment: BaseFragment<*>, fragmentId: Int) {
        supportFragmentManager.inTransaction {
            add(fragmentId, fragment)
        }
    }

    fun FragmentActivity.replaceFragment(fragment: BaseFragment<*>, fragmentId: Int) {
        supportFragmentManager.inTransaction {
            replace(fragmentId, fragment)
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }
}




