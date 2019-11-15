package com.cy.archlib.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeContentView()
        setContentView(getLayout())
        initView()
        initData()
    }

    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initData()


    open fun beforeContentView() {
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    protected fun startActivity(z: Class<*>) {
        startActivity(Intent(this, z))
    }

    protected fun startActivity(z: Class<*>, key: String, bundle: Bundle) {
        val intent = Intent(this, z)
        intent.putExtra(key, bundle)
        startActivity(intent)
    }


    fun FragmentActivity.addFragment(fragment: BaseFragment, fragmentId: Int) {
        supportFragmentManager.inTransaction {
            add(fragmentId, fragment)
        }
    }

    fun FragmentActivity.replaceFragment(fragment: BaseFragment, fragmentId: Int) {
        supportFragmentManager.inTransaction {
            replace(fragmentId, fragment)
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commitAllowingStateLoss()
    }
}




