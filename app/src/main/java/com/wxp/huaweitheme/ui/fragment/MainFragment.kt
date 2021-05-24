package com.wxp.huaweitheme.ui.fragment

import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONObject
import com.wxp.huaweitheme.R
import com.wxp.huaweitheme.databinding.FragmentMainBinding
import com.wxp.huaweitheme.domain.Theme
import com.wxp.huaweitheme.ui.adapter.MyRecyclerViewAdapter
import com.wxp.huaweitheme.uitls.AppUtil
import com.wxp.huaweitheme.uitls.Constant
import com.wxp.huaweitheme.uitls.NetUtil
import okhttp3.Call
import okhttp3.Response
import java.io.IOException


/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/02
 *     desc   : 主页
 * </pre>
 */
class MainFragment :BaseFragment<FragmentMainBinding>() {

    private var dataList:List<Theme>? = null

    override fun getResId(): Int {
        return R.layout.fragment_main
    }

    private var mHandler: MyHandler? = null
    inner class MyHandler :Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> {
                    refreshView()
//                    activity?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.GONE
                    activity?.findViewById<ImageView>(R.id.image_loading)?.visibility = View.GONE

                }
            }
        }
    }

    override fun initFirst() {
//        activity?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE
        activity?.findViewById<ImageView>(R.id.image_loading)?.visibility = View.VISIBLE
        mHandler = MyHandler()
        //获取主题信息
        NetUtil.doConnect(Constant.BASE_URL+"getThemes", object :NetUtil.NetCallBack {

            override fun onSuccess(call: Call, response: Response) {
                val themesInfo = response.body()?.string()
                val themesJson = JSONObject.parseObject(themesInfo)
                val jsonThemes = themesJson.getString("themes")
                dataList = JSONObject.parseArray(jsonThemes, Theme::class.java) as List<Theme>

                val msg = Message()
                msg.what = 0
                mHandler?.sendMessage(msg)
            }

            override fun onFailed(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    fun refreshView() {

        val adapter = MyRecyclerViewAdapter(context!!, dataList!!)
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewMain?.layoutManager = manager
        binding?.recyclerViewMain?.adapter = adapter
        //图标点击事件
        adapter.setOnItemClickListener(object :MyRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(position:Int) {
                //打开详细页面
                val targetFragment = ThemeDetailFragment()
                targetFragment.setData(dataList!![position], position)
                AppUtil.changeFragment(activity!!, targetFragment)

            }
        })
    }

    override fun getTitle(): String {
        return "主题列表"
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
            var lastSecond:Long = 0
            view?.isFocusableInTouchMode = true
            view?.requestFocus()
            view?.setOnKeyListener(object :View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                        if (System.currentTimeMillis() - lastSecond > 2000) {
                            AppUtil.showToast("请再按一次返回键退出")
                            lastSecond = System.currentTimeMillis()
                        } else {
                            activity?.finish()
                        }
                        return true
                    }
                    return false
                }
            })
        }
    }

}