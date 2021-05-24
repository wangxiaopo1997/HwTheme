package com.wxp.huaweitheme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wxp.huaweitheme.R
import java.util.concurrent.RecursiveTask

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/03
 *     desc   : 图片列表展示adapter
 * </pre>
 */
class PicListRecyclerViewAdapter constructor(context: Context, imageLists:List<ImageView>) :RecyclerView.Adapter<PicListRecyclerViewAdapter.MyViewHolder>(){

    private var mContext:Context? = null
    private var mImageList:List<ImageView>? = null

    init {
        mContext = context
        mImageList = imageLists
    }

    inner class MyViewHolder constructor(view:View): RecyclerView.ViewHolder(view) {
         var imageView: ImageView? = null
        init {
            imageView = view.findViewById(R.id.image_pic_list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pic_item_list, null, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mImageList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}