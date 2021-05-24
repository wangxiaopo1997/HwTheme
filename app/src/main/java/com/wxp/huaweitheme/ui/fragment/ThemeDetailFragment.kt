package com.wxp.huaweitheme.ui.fragment

import android.Manifest
import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.wxp.huaweitheme.R
import com.wxp.huaweitheme.databinding.FragmentThemeDetailBinding
import com.wxp.huaweitheme.domain.Theme
import com.wxp.huaweitheme.uitls.AppUtil
import com.wxp.huaweitheme.uitls.Constant
import okhttp3.*
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.lang.reflect.Method

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/03
 *     desc   : 详情
 * </pre>
 */
class ThemeDetailFragment: BaseFragment<FragmentThemeDetailBinding>() {

    private var themeData:Theme? = null
    private var position:Int? = null
    private var isDownloaded:Boolean = false

    private var mHandler: MyHandler? = null
    inner class MyHandler :Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> {
                    binding?.btnDownload?.text = "下载中...${msg.arg1} %"
                    binding?.btnDownload?.isEnabled = false
                    binding?.btnDownload?.isClickable = false
                    binding?.btnDownload?.isFocusable = false
                }
                1 -> {
                    isDownloaded = true
                    binding?.btnDownload?.text = "下载完成！"
                    binding?.btnDownload?.isEnabled = true
                    binding?.btnDownload?.isClickable = true
                    binding?.btnDownload?.isFocusable = true
                    showDialog()
                }

                2 -> {
                    AppUtil.showToast("抱歉，您的手机暂不支持~")
                }
            }
        }
    }

    /**
     * 下载完成，弹窗
     */
    private var dialog:Dialog? = null
    private fun showDialog() {

            dialog= AlertDialog.Builder(context)
            .setCancelable(true)
            .setTitle("下载完成！是否打开主题管理进行安装？\n 打开前请确保【主题】应用没有在后台运行，否则主题可能不显示。")
            .setPositiveButton("确定") { _, _ ->

                //关闭主题程序
                val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val apps = am.runningAppProcesses
                for (app in apps) {
                    if (app.processName == "com.huawei.android.thememanager") {
                        Process.killProcess(app.pid);
                    }
                }

                //打开主题应用程序
                val packageManager: PackageManager = context?.packageManager!!
                val intent: Intent? = packageManager.getLaunchIntentForPackage("com.huawei.android.thememanager")
                if (intent == null) {
                    AppUtil.showToast("未安装")
                } else {
                    startActivity(intent)
                }

                dialog?.dismiss()

            }
            .setNegativeButton("取消") { _, _ ->
                dialog?.dismiss()
            }
            .create()

        dialog?.show()

    }

    override fun getResId(): Int {
        return R.layout.fragment_theme_detail
    }

    override fun initFirst() {
        mHandler = MyHandler()

        Glide.with(activity!!).load(Constant.BASE_URL+"getThemeFile/$position/picIcon").into(binding?.imagePic1!!)
        Glide.with(activity!!).load(Constant.BASE_URL+"getThemeFile/$position/picUnlock").into(binding?.imagePic2!!)

        binding?.btnDownload?.setOnClickListener {

            //没读取文件权限 申请
            if (ContextCompat.checkSelfPermission((context!!),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),2)
            } else{
                //有权限 开始下载
                if (!isDownloaded) {
                    startDownload()
                } else {
                    showDialog()
                }
            }

        }
    }

    /**
     * 下载主题
     */
    private fun startDownload() {

        try {
            //已经下载文件的长度
            val downloadLength = 0L
            val url = Constant.BASE_URL+"getThemeFile/$position/theme"
            val client = OkHttpClient()
            val request = Request.Builder()
                .addHeader("RANGE", "bytes=$downloadLength-")//断点续传
                .url(url)
                .build()
            client.newCall(request).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    //获取输出流
                    val inputStream = response.body()?.byteStream()
                    //文件数据总长度
                    val contentLength:Long = themeData?.themeSize?.toLong()!!
                    //下载路径
                    val directory = Environment.getExternalStoragePublicDirectory("Huawei/Themes").path
                    //如果路径不存在，则不是华为手机
                    if (!File(directory).exists()) {
                        val msg = Message()
                        msg.what = 2
                        mHandler?.sendMessage(msg)
                        return
                    }
                    //下载的名称
                    val file = File(directory+"/"+themeData?.themeName+".hwt")
                    //文件输出
                    val randomAccessFile = RandomAccessFile(file,"rw")
                    randomAccessFile.seek(downloadLength)
                    var len:Int
                    var total = 0
                    //输出文件
                    val b = ByteArray(1024)
                    while (inputStream?.read(b).also {
                            len = it!!
                        } != -1) {
                        randomAccessFile.write(b,0,len)
                        total+=len

                        //计算下载的百分比
                        val progress = ((total + downloadLength) * 100 / contentLength).toInt()
                        val msg = Message()
                        msg.what = 0
                        msg.arg1 = progress
                        mHandler?.sendMessage(msg)

                    }
                    inputStream?.close()
                    response.body()?.close()

                    val msg = Message()
                    msg.what = 1
                    mHandler?.sendMessage(msg)
                }

            })

        }catch (ex : Exception){
            ex.printStackTrace()
        }

    }

    override fun getTitle(): String {
        val title = themeData?.themeName
        return if (title == null) {
            ""
        } else {
            themeData?.themeName!!
        }
    }

    fun setData(data:Theme, position:Int) {
        this.themeData = data
        this.position = position
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //读取文件
        when(requestCode){
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    startDownload()
                } else {
                    AppUtil.showToast("请授予权限~")
                }
            }
        }
    }

}