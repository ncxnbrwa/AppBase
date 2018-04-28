package com.myxianwen.share.entity

import com.xiong.appbase.R


/**
 * Created by iiMedia on 2017/8/19.
 * 分享填充adapter的数据
 */
data class ShareItem(val type: Int, val name: String, val iconId: Int)

val SHARE_ITEM_LIST = arrayListOf<ShareItem>(
        ShareItem(0, "微信好友", R.mipmap.btn_wechat),
        ShareItem(1, "微信朋友圈", R.mipmap.btn_friends),
        ShareItem(2, "新浪微博", R.mipmap.btn_sina),
        ShareItem(3, "QQ好友", R.mipmap.btn_qq),
        ShareItem(4, "复制链接", R.mipmap.btn_copyurl)
)

