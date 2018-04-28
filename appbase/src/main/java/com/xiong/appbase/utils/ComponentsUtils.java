package com.xiong.appbase.utils;

import android.content.Context;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.xiong.appbase.custom.QMUITipDialogWrapper;


/**
 * Created by iiMedia on 2017/12/11.
 * 提示工具
 */

public class ComponentsUtils {

    //加载dialog
    public static QMUITipDialogWrapper getLoadingDialog(Context context, String text) {
        QMUITipDialogWrapper tipDialog = new QMUITipDialogWrapper.Builder(context)
                .setIconType(QMUITipDialogWrapper.Builder.ICON_TYPE_LOADING)
                .setTipWord(text)
                .create();
        return tipDialog;
    }

//    public static QMUITipDialog getLoadingDialog(Context context, String text) {
//        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .setTipWord(text)
//                .create();
//        return tipDialog;
//    }

    //成功提示dialog
    public static QMUITipDialog getSuccessDialog(Context context, String text) {
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(text)
                .create();
        return tipDialog;
    }

    //失败的提示dialog
    public static QMUITipDialog getFailDialog(Context context, String text) {
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(text)
                .create();
        return tipDialog;
    }

    //加载失败重试提示
    public static void showFailHint(QMUIEmptyView emptyView, View.OnClickListener clickListener) {
        emptyView.show(false, "加载失败", null, "点击重试", clickListener);
    }
}
