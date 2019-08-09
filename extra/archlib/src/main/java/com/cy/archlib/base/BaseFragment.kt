package com.cy.archlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseFragment<BP : BasePresenter<*, *>> : Fragment() {

    lateinit var mPresenter: BP
    // start in called lifecycle method & stop in corresponding lifecycle method
    // private val mScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }
    // use it in initPresenter （start in onViewCreated & stop in onDestroyView）
    val mScopeProvider: AndroidLifecycleScopeProvider by lazy { AndroidLifecycleScopeProvider.from(viewLifecycleOwner) }

    companion object {
        private const val TAG = "BaseFragment"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }
}