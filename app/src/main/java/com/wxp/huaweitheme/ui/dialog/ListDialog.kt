package com.wxp.huaweitheme.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import com.wxp.huaweitheme.R

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/12
 *     desc   :
 * </pre>
 */
class ListDialog constructor(context: Context, resId: Int): Dialog(context, resId){


    class Builder constructor(context: Context) {

        private var dialog: ListDialog? = null
        private var mView: View? = null
        private var mTitle: String? = null

        init {
            dialog = ListDialog(context, R.style.WarningDialogTheme)
        }

        fun setView(view: View): Builder {
            this.mView = view
            return this
        }

        fun setTitle(title:String): Builder {
            this.mTitle = title
            return this
        }

        fun create():ListDialog {
            dialog?.setCancelable(true)
            dialog?.setContentView(mView!!)
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.setTitle(mTitle)
            return dialog!!
        }

    }
}