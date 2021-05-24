package com.wxp.huaweitheme.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.wxp.huaweitheme.R

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/10/29
 *     desc   : 弹窗
 * </pre>
 */
class WarningDialog(context: Context, resId:Int) : Dialog(context, resId) {

    class Builder constructor(context: Context) {

        private var dialog:WarningDialog? = null
        init {
            dialog = WarningDialog(context, R.style.WarningDialogTheme)
        }

        private var view:View? = null

        fun setView(view: View): Builder{
            this.view = view
            return this;
        }

        fun create() :WarningDialog {
            dialog?.setContentView(view!!)
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
            return dialog!!
        }
    }

}