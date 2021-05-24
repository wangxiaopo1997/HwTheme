package com.wxp.huaweitheme.uitls

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.wxp.huaweitheme.R
import okhttp3.Interceptor

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/10/29
 *     desc   : 工具类
 * </pre>
 */
object AppUtil {

    var context:Context? = null

    /**
     * 吐丝
     */
    fun showToast(msg:String ) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    //切换Fragment
    fun changeFragment(activity: FragmentActivity, targetFragment: Fragment) {

        val fragmentManager = activity.supportFragmentManager
        val tc = fragmentManager.beginTransaction()
        tc.addToBackStack("")
        tc.replace(R.id.frame_main, targetFragment)
        tc.commit()
    }
}