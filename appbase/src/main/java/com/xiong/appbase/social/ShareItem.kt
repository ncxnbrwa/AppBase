package com.myxianwen.share.entity

import com.xiong.appbase.R


/**
 * Created by iiMedia on 2017/8/19.
 * 分享填充adapter的数据
 */
data class ShareItem(val type: Int, val name: String, val iconId: Int)

val MY_SHARE_WXFRIEND = 0
val MY_SHARE_WXCIRCLE = 1
val MY_SHARE_WEIBO = 2
val MY_SHARE_QQFRIEND = 3
val MY_SHARE_COPY = 4

val SHARE_ITEM_LIST = arrayListOf<ShareItem>(
        ShareItem(MY_SHARE_WXFRIEND, "微信好友", R.mipmap.btn_wechat),
        ShareItem(MY_SHARE_WXCIRCLE, "微信朋友圈", R.mipmap.btn_friends),
//        ShareItem(MY_SHARE_WEIBO, "新浪微博", R.mipmap.btn_sina),
        ShareItem(MY_SHARE_QQFRIEND, "QQ好友", R.mipmap.btn_qq),
        ShareItem(MY_SHARE_COPY, "复制链接", R.mipmap.btn_copyurl)
)

