package com.wxp.huaweitheme.ui.fragment

import com.wxp.huaweitheme.R
import com.wxp.huaweitheme.databinding.FragmentAuthorBinding
import kotlinx.android.synthetic.main.dialog_list.view.*

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/12
 *     desc   :
 * </pre>
 */
class AuthorFragment: BaseFragment<FragmentAuthorBinding>() {
    override fun getResId(): Int {
        return R.layout.fragment_author
    }

    override fun initFirst() {

    }

    override fun getTitle(): String {
        return "关于作者"
    }
}