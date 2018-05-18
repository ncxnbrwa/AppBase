package com.xiong.appbase.social

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.widget.Toast
import com.myxianwen.share.adapter.ShareItemAdpater
import com.myxianwen.share.entity.SHARE_ITEM_LIST
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.xiong.appbase.R
import com.xiong.appbase.base.BaseActivity
import com.xiong.appbase.extension.addGridDivider
import kotlinx.android.synthetic.main.activity_social_share.*
import org.jetbrains.anko.toast

/**
 * Created by iiMedia on 2018/4/19.
 * 分享弹窗
 */
class SocialShareActivity : BaseActivity() {
    private val mContext = this

    private var url: String? = null
    private var title: String? = null
    private var content: String? = null
    private var image: String? = null
    private var id: Int = 0
    private var type: Int = 0
    private var sn: String? = null
    private val requestPermissionCode = 123
    private var firstOpenResume = false

    override fun getLayoutId(): Int {
        return R.layout.activity_social_share
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()

        initView()
        initShareData(intent)
        //设置4列的layoutmanager
        val gridlayoutmanager = GridLayoutManager(mActivity, 4)
        share_list.layoutManager = gridlayoutmanager
        share_list.addGridDivider(mActivity)
        share_list.adapter = ShareItemAdpater(SHARE_ITEM_LIST, {
            shareIt(it.type)
        })
    }

    //初始化分享数据
    private fun initShareData(intent: Intent) {
        this.url = intent.getStringExtra("url")
        this.title = intent.getStringExtra("title")
        this.content = intent.getStringExtra("content")
        this.image = intent.getStringExtra("image")
        this.id = intent.getIntExtra("id", 0)
        this.type = intent.getIntExtra("type", TYPE_NEWS)
        this.sn = intent.getStringExtra("sn")
        if (TextUtils.isEmpty(this.url)) {
//            this.url = "${_BASE_URL_OTHER}/shareArticle.action?news_id=${id}"
        }

        if (content == null || content == "") {
            //没有内容简介，内容设置为文章标题
            content = title
        }
    }

    private fun initView() {
        cancel.setOnClickListener {
            closeTheWindows()
        }
    }

    private fun closeTheWindows() {
        finish()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            if (firstOpenResume) {
                closeTheWindows()
            }
            firstOpenResume = true
        }
    }

    //分享的点击事件
    private fun shareIt(type: Int) {
        when (type) {
            0 -> {
//                platform_id = PLATFORM4WEXIN
                if (type == TYPE_AD) {
                    shareWeixinFrend(url!!, title!!, content!!, image!!)
                } else if (type == TYPE_TOPIC) {
//                    shareWeixinFrend(url + "&platform_id=" + platform_id, topicPre + title!!, content!!, image!!)
                } else {
//                    shareWeixinFrend(url + "&platform_id=" + platform_id, title!!, content!!, image!!)
                }
            }
            1 -> {
//                platform_id = PLATFORM4CIRCLE
                if (type == TYPE_AD) {
                    shareWeixinCircle(url!!, title!!, content!!, image!!)
                } else if (type == TYPE_TOPIC) {
//                    shareWeixinCircle(url + "&platform_id=" + platform_id, topicPre + title!!, content!!, image!!)
                } else {
//                    shareWeixinCircle(url + "&platform_id=" + platform_id, title!!, content!!, image!!)
                }
            }
            2 -> {
//                platform_id = PLATFORM4WEIBO
                if (type == TYPE_AD) {
                    shareSina(url!!, title!!, content!!, image!!)
                } else if (type == TYPE_TOPIC) {
//                    shareSina(url + "&platform_id=" + platform_id, topicPre + title!!, content!!, image!!)
                } else {
//                    shareSina(url + "&platform_id=" + platform_id, title!!, content!!, image!!)
                }
            }
            3 -> {
//                platform_id = PLATFORM4QQ
                if (type == TYPE_AD) {
                    shareQQFriend(url!!, title!!, content!!, image!!)
                } else {
//                    shareQQFriend(url + "&platform_id=" + platform_id, title!!, content!!, image!!)
                }
            }
            4 -> {
//                platform_id = PLATFORM4COPY
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val cd: ClipData
                if (type == TYPE_AD) {
                    cd = ClipData.newPlainText("label", url)
                } else {
                    cd = ClipData.newPlainText("label", url)
                }
                clipboardManager.primaryClip = cd
                toast("已放入剪切板")
                closeTheWindows()
            }
        }
    }

    private fun ShareWeb(platform: SHARE_MEDIA, url: String, title: String, content: String, image: String) {
        val thumb = UMImage(mContext, image)
        val web = UMWeb(url)
        web.setThumb(thumb) //缩略图
        web.description = content //描述
        web.title = title //标题

        ShareAction(mActivity).withMedia(web).setPlatform(platform).setCallback(shareListener).share()
    }

    private fun shareQQFriend(url: String, title: String, content: String, image: String) {
        ShareWeb(SHARE_MEDIA.QQ, url, title, content, image)
    }

    private fun shareWeixinCircle(url: String, title: String, content: String, image: String) {
        ShareWeb(SHARE_MEDIA.WEIXIN_CIRCLE, url, title, content, image)
    }

    private fun shareWeixinFrend(url: String, title: String, content: String, image: String) {
        ShareWeb(SHARE_MEDIA.WEIXIN, url, title, content, image)
    }

    private fun shareTecnetWeibo(url: String, title: String, content: String, image: String) {
        ShareWeb(SHARE_MEDIA.TENCENT, url, title, content, image)
    }

    private fun shareQQZone(url: String, title: String, content: String, image: String) {
        ShareWeb(SHARE_MEDIA.QZONE, url, title, content, image)
    }

    private fun shareSina(url: String, title: String, content: String, image: String) {
        ShareWeb(SHARE_MEDIA.SINA, url, title, content, image)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestPermissionCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPermissionCode) {
            if (grantResults.size > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    private val shareListener = object : UMShareListener {
        override fun onStart(platform: SHARE_MEDIA) {

        }

        override fun onResult(platform: SHARE_MEDIA) {
            Toast.makeText(mContext, "分享成功", Toast.LENGTH_LONG).show()
        }

        override fun onError(platform: SHARE_MEDIA, t: Throwable) {
            val tMsg = t.message
            val msg1 = tMsg?.substring(tMsg.indexOf("：") + 1)
            val msg2 = msg1?.substring(msg1.indexOf("：") + 1)
            Toast.makeText(mContext, "分享失败:" + msg2, Toast.LENGTH_LONG).show()
        }

        override fun onCancel(platform: SHARE_MEDIA) {
            Toast.makeText(mContext, "分享已取消", Toast.LENGTH_LONG).show()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        UMShareAPI.get(this).onSaveInstanceState(outState)
    }

    companion object {
        private val IVSIBLE = 0
        private val FINISH = 1

        val PLATFORM4WEXIN = 0
        val PLATFORM4CIRCLE = 1
        val PLATFORM4WEIBO = 2
        val PLATFORM4QQ = 3
        val PLATFORM4QQZONE = 4
        val PLATFORM4EMILE = 5
        val PLATFORM4COPY = 6
        val PLATFORM4QQWEIBO = 7

        val TYPE_NEWS = 0
        val TYPE_AD = 1
        val TYPE_TOPIC = 2

        private fun newIntent(context: Context, url: String, title: String, content: String, image: String, id: Int, type: Int): Intent {
            val intent = Intent(context, SocialShareActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("image", image)
            intent.putExtra("id", id)
            intent.putExtra("type", type)
            return intent
        }

        fun intentTo(activity: Activity, url: String, title: String, content: String, image: String, id: Int, type: Int) {
            activity.startActivity(newIntent(activity, url, title, content, image, id, type))
            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }

        fun intentTo(activity: Activity) {
            activity.startActivity(Intent(activity, SocialShareActivity::class.java))
            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }
    }
}