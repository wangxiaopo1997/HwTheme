package com.wxp.huaweitheme.ui.fragment

import android.os.Build
import android.view.KeyEvent
import android.view.View
import com.wxp.huaweitheme.R
import com.wxp.huaweitheme.databinding.FragmentTeachBinding
import com.wxp.huaweitheme.uitls.AppUtil

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/03
 *     desc   : 主题使用教程
 * </pre>
 */
class TeachFragment:BaseFragment<FragmentTeachBinding>() {
    override fun getResId(): Int {
        return R.layout.fragment_teach
    }

    override fun initFirst() {

    }

    override fun getTitle(): String {
        return "主题应用教程"
    }


    override fun onResume() {
        super.onResume()
        monitor()
    }
    /**
     * 返回监听
     */
    private fun monitor() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            view?.isFocusableInTouchMode = true
            view?.requestFocus()
            view?.setOnKeyListener(object : View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                        AppUtil.changeFragment(activity!!, MainFragment())
                        return true
                    }
                    return false
                }
            })
        }
    }
}