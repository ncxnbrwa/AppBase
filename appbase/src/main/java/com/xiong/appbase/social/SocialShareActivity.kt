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
import com.myxianwen.share.entity.*
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

/**
 * Created by iiMedia on 2018/4/19.
 * 分享弹窗
 */
class SocialShareActivity : BaseActivity() {
    private val mContext = this

    private var title: String? = null
    private var content: String? = null
    private var image: String? = null
    private var rankingId: Int = 0
    private var brandId: Int = 0
    private var type: Int = 0
    private val requestPermissionCode = 123
    private var firstOpenResume = false
    private val mActivity = this
    private val rankingUrl = "http://ranking.iimedia.cn/m-brand-list.html?rankId="
    private val brandUrl = "http://ranking.iimedia.cn/m-brand-detail.html?brandId="

    override fun getLayoutId(): Int {
        return R.layout.activity_social_share
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()

        initView()
        initShareData(intent)
        //设置4列
        share_list.layoutManager = GridLayoutManager(mActivity, 4)
        share_list.addGridDivider(mActivity)
        share_list.adapter = ShareItemAdpater(SHARE_ITEM_LIST, {
            shareIt(it.type)
        })
    }

    //初始化分享数据
    private fun initShareData(intent: Intent) {
        this.title = intent.getStringExtra(DATA_TITLE).trim()
        this.content = intent.getStringExtra(DATA_CONTENT).trim()
        this.image = intent.getStringExtra(DATA_IMAGE)
        this.rankingId = intent.getIntExtra(DATA_RANKINGID, 0)
        this.brandId = intent.getIntExtra(DATA_BRANDID, 0)
        this.type = intent.getIntExtra(DATA_TYPE, TYPE_RANKING)

        if (content == null || TextUtils.isEmpty(content)) {
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
    private fun shareIt(platType: Int) {
        when (platType) {
            MY_SHARE_WXFRIEND -> {
                if (type == TYPE_RANKING) {
                    shareWeixinFriend("$rankingUrl$rankingId", title!!, content!!, image!!)
                } else if (type == TYPE_BRAND) {
                    shareWeixinFriend("$brandUrl$brandId", title!!, content!!, image!!)
                }
            }
            MY_SHARE_WXCIRCLE -> {
                if (type == TYPE_RANKING) {
                    shareWeixinCircle("$rankingUrl$rankingId", title!!, content!!, image!!)
                } else if (type == TYPE_BRAND) {
                    shareWeixinCircle("$brandUrl$brandId", title!!, content!!, image!!)
                }
            }
            MY_SHARE_WEIBO -> {
                if (type == TYPE_RANKING) {
                    shareSina("$rankingUrl$rankingId", title!!, content!!, image!!)
                } else if (type == TYPE_BRAND) {
                    shareSina("$brandUrl$brandId", title!!, content!!, image!!)
                }
            }
            MY_SHARE_QQFRIEND -> {
                if (type == TYPE_RANKING) {
                    shareQQFriend("$rankingUrl$rankingId", title!!, content!!, image!!)
                } else if (type == TYPE_BRAND) {
                    shareQQFriend("$brandUrl$brandId", title!!, content!!, image!!)
                }
            }
            MY_SHARE_COPY -> {
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val cd: ClipData
                if (type == TYPE_RANKING) {
                    cd = ClipData.newPlainText("label", "$rankingUrl$rankingId")
                } else {
                    cd = ClipData.newPlainText("label", "$brandUrl$brandId")
                }
                clipboardManager.primaryClip = cd
                showToast("已放入剪切板")
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

    private fun shareWeixinFriend(url: String, title: String, content: String, image: String) {
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
            if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    private val shareListener = object : UMShareListener {
        override fun onStart(platform: SHARE_MEDIA) {}

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

        val TYPE_RANKING = 0
        val TYPE_BRAND = 1
        val TYPE_TOPIC = 2
        val DATA_TITLE = "title"
        val DATA_CONTENT = "content"
        val DATA_IMAGE = "image"
        val DATA_RANKINGID = "rankingId"
        val DATA_BRANDID = "brandId"
        val DATA_TYPE = "type"

        private fun newIntent(context: Context, title: String, content: String
                              , image: String, rankingId: Int, brandId: Int, type: Int): Intent {
            val intent = Intent(context, SocialShareActivity::class.java)
            intent.putExtra(DATA_TITLE, title)
            intent.putExtra(DATA_CONTENT, content)
            intent.putExtra(DATA_IMAGE, image)
            intent.putExtra(DATA_RANKINGID, rankingId)
            intent.putExtra(DATA_BRANDID, brandId)
            intent.putExtra(DATA_TYPE, type)
            return intent
        }

        fun intentTo(activity: Activity, title: String, content: String
                     , image: String, rankingId: Int, brandId: Int, type: Int) {
            activity.startActivity(newIntent(activity, title, content, image, rankingId, brandId, type))
            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }

        fun intentTo(activity: Activity) {
            activity.startActivity(Intent(activity, SocialShareActivity::class.java))
            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }
    }
}