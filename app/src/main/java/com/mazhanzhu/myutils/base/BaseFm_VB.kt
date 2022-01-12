package com.mazhanzhu.myutils.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/12 13:49
 * Desc   :
 */
abstract class BaseFm_VB<Mzz : ViewBinding> : Fragment(), View.OnClickListener {
    protected lateinit var vb: Mzz
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = getViewBinding(layoutInflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getViewBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): Mzz

    override fun onClick(v: View?) {
        click(v)
    }

    abstract fun click(v: View?)

    /**
     * 页面跳转
     */
    fun startActivity(clz: Class<*>?) {
        context?.startActivity(Intent(context, clz))
    }
}