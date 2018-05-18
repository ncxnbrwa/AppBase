package com.xiong.appbase.utils;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xiong.appbase.R;


/**
 * Created by iiMedia on 2017/12/14.
 */

public class DialogUtils {
    //设置只带标题和内容的对话框
    public static MaterialDialog showTitleContent(Context context, String title, String content) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(context.getResources().getString(R.string.ok))
                .cancelable(true);
        return materialBuilder.build();
    }

    //设置只带标题内容和进度条的对话框，和系统dialog类似
    public static MaterialDialog showContentWithProgress(Context context, String content) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
                .content(content)
                .canceledOnTouchOutside(false)
                .widgetColorRes(R.color.colorPrimary)
                .contentColorRes(R.color.colorPrimary)
                .progress(true, 0);
        return materialBuilder.build();
    }

    //item选择的对话框
    public static MaterialDialog showListDialog(Context context, String title, String positiveText,
                                                @ArrayRes int array,
                                                MaterialDialog.ListCallback listCallback,
                                                MaterialDialog.SingleButtonCallback buttonBack) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .items(array)
                .positiveText(positiveText)
                .positiveColorRes(R.color.colorPrimary)
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .contentColorRes(R.color.primary_text_color)
                .itemsCallback(listCallback)
                .onAny(buttonBack);
        return materialBuilder.build();
    }

    //有按钮内容的对话框
    public static MaterialDialog showPositiveNegativeContent(Context context,
//                                                             String title,
                                                             String content,
                                                             MaterialDialog.SingleButtonCallback
                                                                     callback) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
//                .title(title)
                .content(content)
                .titleColorRes(R.color.primary_text_color)
                .contentColorRes(R.color.dan_gray)
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.dan_gray)
                .canceledOnTouchOutside(false)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onAny(callback);
        return materialBuilder.build();
    }

    //可编辑的对话框
    public static MaterialDialog showEditText(Context context, String title, String hint,
                                              String prefill,
                                              MaterialDialog.InputCallback inputCallback) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.primary_text_color)
                .positiveColorRes(R.color.primary_text_color)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.primary_text_color)
                .inputRangeRes(1, 10, R.color.red)
                .contentColorRes(R.color.primary_text_color)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(hint, prefill, inputCallback);
        return materialBuilder.build();
    }

    //多选对话框
    public static MaterialDialog showCheckbox(Context context, String title, @ArrayRes int arrays,
                                              Integer[] preselect, MaterialDialog
                                                      .ListCallbackMultiChoice
                                                      multiChoiceCallback) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.primary_text_color)
                .contentColorRes(R.color.primary_text_color)
                .items(arrays)
                .itemsCallbackMultiChoice(preselect, multiChoiceCallback)
                .positiveText(R.string.confirm)
                .positiveColorRes(R.color.colorPrimary)
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .widgetColorRes(R.color.colorPrimary);
        return materialBuilder.build();
    }

    //单选对话框
    public static MaterialDialog showSingleChoice(Context context, String title,
                                                  @ArrayRes int array, int index,
                                                  MaterialDialog.ListCallbackSingleChoice
                                                          callback) {
        MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .titleColorRes(R.color.primary_text_color)
                .contentColorRes(R.color.primary_text_color)
                .items(array)
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .widgetColorRes(R.color.colorPrimary)
                .itemsCallbackSingleChoice(index, callback);
        return materialBuilder.build();
    }
}
