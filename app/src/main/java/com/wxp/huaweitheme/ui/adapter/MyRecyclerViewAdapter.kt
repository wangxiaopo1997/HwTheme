package com.wxp.huaweitheme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wxp.huaweitheme.R
import com.wxp.huaweitheme.domain.Theme
import com.wxp.huaweitheme.uitls.AppUtil
import com.wxp.huaweitheme.uitls.Constant
import java.io.File
import java.lang.Exception

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/02
 *     desc   :
 * </pre>
 */
class MyRecyclerViewAdapter constructor(context: Context, themeList: List<Theme>)
    : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    private var themeLists:List<Theme>? = null
    private var mListener: OnItemClickListener? = null
    private var mContext:Context? = null

    init {
        themeLists = themeList
        mContext = context
    }

    inner class MyViewHolder constructor(itemView: View):RecyclerView.ViewHolder(itemView) {
        var themeIcon:ImageView? = null
        var themeName:TextView? = null
        var themeSize:TextView? = null
        var cl:ConstraintLayout? = null
        init {
            themeName = itemView.findViewById(R.id.tv_theme_name)
            themeIcon = itemView.findViewById(R.id.image_theme_icon)
            themeSize = itemView.findViewById(R.id.tv_theme_size)
            cl = itemView.findViewById(R.id.cl_1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theme_item_list, null, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return themeLists?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.themeIcon = themeLists[position].
        try {
            Glide
                .with(mContext!!)
                .load(Constant.BASE_URL+"getThemeFile"+ File.separator + position +"/picIcon")
                .into(holder.themeIcon!!)

            holder.themeName?.text = themeLists?.get(position)?.themeName
            val themeSize = themeLists?.get(position)?.themeSize
            if (themeSize != null) {
                holder.themeSize?.text = "${themeSize.toLong() / 1000000.0} MB"
            }

        } catch (e:Exception) {
            AppUtil.showToast("服务器数据错误，请联系作者。qq：804690347")
        }
        //图标点击事件
        holder.themeIcon?.setOnClickListener {
            mListener?.onClick(position)
        }

        holder.cl?.setOnClickListener {
            mListener?.onClick(position)
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onClick(position: Int);
    }
}