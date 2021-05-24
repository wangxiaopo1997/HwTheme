package com.wxp.huaweitheme.domain

import com.alibaba.fastjson.annotation.JSONField

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/02
 *     desc   :
 * </pre>
 */
data class Themes(@JSONField(name="picIcon")val picIcon:String,
                 @JSONField(name="picUnlock")val picUnlock:String,
                 @JSONField(name="themeName")val themeName:String,
                 @JSONField(name="themeSize")val themeSize:String)
