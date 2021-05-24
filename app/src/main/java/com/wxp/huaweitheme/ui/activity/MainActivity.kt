package com.wxp.huaweitheme.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wxp.huaweitheme.R
import com.wxp.huaweitheme.ui.dialog.ListDialog
import com.wxp.huaweitheme.ui.dialog.WarningDialog
import com.wxp.huaweitheme.ui.fragment.AuthorFragment
import com.wxp.huaweitheme.ui.fragment.MainFragment
import com.wxp.huaweitheme.ui.fragment.TeachFragment
import com.wxp.huaweitheme.uitls.AppUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this).asGif().load(R.drawable.loading).into(image_loading)

        //弹窗提示
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_info, null, false)
        val btnOk = view.findViewById<Button>(R.id.dialog_button_ok)
        val btnCancel = view.findViewById<Button>(R.id.dialog_button_cancel)
        val tvTips = view.findViewById<TextView>(R.id.tv_tips)

        val dialog:WarningDialog = WarningDialog.Builder(this)
            .setView(view)
            .create()
        dialog.show()

        //按钮事件
        btnOk.setOnClickListener {
            dialog.dismiss()
            AppUtil.changeFragment(this, TeachFragment())
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
            AppUtil.changeFragment(this, MainFragment())
        }

        val strSpan = SpannableString("此应用内主题大部分来自【花粉俱乐部@小生我怕怕ii】，仅供学习交流，请勿用于商业，侵删，谢谢。")
        val clickableSpan = object :ClickableSpan(){
            override fun onClick(widget: View) {
                //跳转
                val url = "https://cn.club.vmall.com/space-uid-60675284.html"
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = resources.getColor(R.color.blue_cloud_scheme, null)
                ds.isUnderlineText = false
            }
        }

        strSpan.setSpan(clickableSpan, 17, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvTips.movementMethod = LinkMovementMethod.getInstance()
        tvTips.text = strSpan

        //右上角三点菜单事件
        head_right.setOnClickListener {

            val view = LayoutInflater.from(this).inflate(R.layout.dialog_list, null, false)
            val dialog = ListDialog.Builder(this)
                .setTitle("选择列表")
                .setView(view)
                .create()
            dialog.show()

            val list = view.findViewById<ListView>(R.id.list_item)
            val funcList = arrayListOf("关于作者", "查看教程", "主题制作者")
            list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, 0, funcList)
            //item点击事件
            list.setOnItemClickListener { _, _, position, _ ->
                dialog.dismiss()
                when(position) {
                    //关于作者
                    0 -> {
                        AppUtil.changeFragment(this, AuthorFragment())
                    }
                    //查看教程
                    1 -> {
                        AppUtil.changeFragment(this, TeachFragment())
                    }
                    //主题制作者
                    2 -> {
                        val url = "https://cn.club.vmall.com/space-uid-60675284.html"
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}