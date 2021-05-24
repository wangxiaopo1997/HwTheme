package com.wxp.huaweitheme.uitls

import okhttp3.*
import java.io.IOException

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/02
 *     desc   : 网络请求工具类
 * </pre>
 */
object NetUtil {

    private var okHttpClient = OkHttpClient()

    fun doConnect(url: String, callBack:NetCallBack) {

        val request = Request.Builder()
            .url(url)
            .build()

        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                callBack.onFailed(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                callBack.onSuccess(call, response)
            }
        })
    }



    interface NetCallBack {
        fun onSuccess(call: Call, response: Response)
        fun onFailed(call: Call, e: IOException)
    }

    /**
     * 获取将要下载的数据内容长度
     * @param downloadUrl
     * @return
     */
    fun getContentLength(downloadUrl: String): Long {
        try {
            val client = OkHttpClient()
            val request = Request.Builder().url(downloadUrl).build()
            val response = client.newCall(request).execute()
            if (response != null && response.isSuccessful) {
                val contentLength = response.body()!!.contentLength()
                response.body()!!.close()
                return contentLength
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0
    }
}