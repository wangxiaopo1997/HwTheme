package com.wxp.huaweitheme.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.wxp.huaweitheme.R
import java.util.ArrayList

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/08/07
 *     desc   : fragment基类
 * </pre>
 */
abstract class BaseFragment<T : ViewDataBinding?>: Fragment() {

    var fragmentList = ArrayList<Fragment>()
    var binding: T? = null
    private lateinit var headTitle: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentList.add(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<T>(inflater, getResId(), container, false)
        initFirst()
        //设置标题
        headTitle = activity?.findViewById<TextView>(R.id.head_title)!!
        headTitle.text = getTitle()
        return binding?.root
    }

    abstract fun getResId(): Int
    abstract fun initFirst()
    abstract fun getTitle():String
}