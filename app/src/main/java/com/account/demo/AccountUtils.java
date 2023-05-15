package com.account.demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 创建人：Sivan
 * 类描述：XWorld应用的支付与体现绑定
 *
 * （1）首先在 AndroidManifest.xml 中添加如下：
 * <p>
 *     <queries>
 *         <package android:name="pro.xworld.app" />
 *     </queries>
 * <p>
 *
 *（2）需要支付时，调用 {@link #startPay(Context, String, String, String)} 方法
 *
 *（3）需要绑定时，调用 {@link #startBind(Context, String, String)} 方法
 *
 *（4）h5端调用示例：
 * <p>
 *     <a href="xworld://pro.xworld.app/pay?mch_id={商户ID}&order_id={订单ID}&app_name={你的应用名称}"></a>
 *     <a href="xworld://pro.xworld.app/bind?mch_id={商户ID}&user_id={用户ID}"></a>
 * <p>
 *
 * 日期：2023/4/28
 */
public class AccountUtils {

//first：
//<queries>
//<package android:name="pro.xworld.app" />
//</queries>

    /**
     * 启动 XWorld 支付页面
     *
     * @param context 上下文
     * @param machId 商户 ID
     * @param orderId 订单 ID
     * @param appName 你们的应用名称
     */
    public static void startPay(Context context, String machId, String orderId, String appName) {
        String pkg = "pro.xworld.app";
        String path = "pro.xworld.app.pay.XWorldPayActivity";
        Intent intent = new Intent();
        intent.putExtra("mch_id", machId);
        intent.putExtra("order_id", orderId);
        intent.putExtra("app_name", appName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName(pkg, path);
        intent.setComponent(cn);

        try {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                startStore(context, pkg);// 跳转应用商店
            }
        } catch (Exception e) {
            startStore(context, pkg);
        }
    }

    /**
     * 启动 XWorld 绑定页面
     *
     * @param context 上下文
     * @param machId 商户 ID
     * @param userId 商户的用户 ID
     */
    public static void startBind(Context context, String machId, String userId) {
        String pkg = "pro.xworld.app";
        String path = "pro.xworld.app.pay.XWorldBindActivity";
        Intent intent = new Intent();
        intent.putExtra("mch_id", machId);
        intent.putExtra("user_id", userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName(pkg, path);
        intent.setComponent(cn);

        try {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                startStore(context, pkg);// 跳转应用商店
            }
        } catch (Exception e) {
            startStore(context, pkg);
        }
    }

    /**
     * 若用户未安装 XWorld ，则跳转应用商店去下载
     *
     * @param context 上下文
     * @param pkg 应用包名
     */
    public static void startStore(Context context, String pkg) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse("market://details?id=" + pkg));
        intent1.setPackage("com.android.vending");
        if (intent1.resolveActivityInfo(context.getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(intent1);
            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + pkg));
            if (intent2.resolveActivityInfo(context.getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {
                context.startActivity(intent2);
            } else {
//              Toast.makeText(context, "no app store", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 判断某个app是否已安装
     *
     * @param context 上下文
     * @param pkg 应用包名
     *
     * @return 是否安装
     */
    public static boolean isAppInstalled(Context context, String pkg) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }

        return packageInfo != null;
    }

    /**
     * 打开其他 app
     *
     * @param url 深链
     * @param activity Activity
     *
     * @return 是否打开成功
     */
    public static boolean openOtherApp(String url, Activity activity) {
        if (TextUtils.isEmpty(url)) return false;
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        String scheme = uri.getScheme();
        //host 和 scheme 都不能为null
        if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(scheme)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (activity.getPackageManager().queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                    activity.startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

//web use:
//<a href="xworld://pro.xworld.app/pay?mch_id={商户ID}&order_id={订单ID}&app_name={你的应用名称}"></a>
//<a href="xworld://pro.xworld.app/bind?mch_id={商户ID}&user_id={用户ID}"></a>
}
