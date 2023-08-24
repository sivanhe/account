package com.account.demo;

import android.app.Activity;
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
 * <p>
 * （1）首先在 AndroidManifest.xml 中添加如下：
 * <p>
 *     <queries>
 *         <intent>
 *             <action android:name="android.intent.action.MAIN" />
 *         </intent>
 *     </queries>
 * <p>
 *（2）需要支付时，调用 {@link #startPay(Activity, String, String, String)} 方法
 * <p>
 *（3）需要绑定时，调用 {@link #startBind(Activity, String, String)} 方法
 * <p>
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
//  <intent>
//    <action android:name="android.intent.action.MAIN" />
//  </intent>
//</queries>

    /**
     * 启动 XWorld 支付页面
     *
     * @param context 上下文
     * @param machId 商户 ID
     * @param orderId 订单 ID
     * @param appName 你们的应用名称
     */
    public static void startPay(Activity context, String machId, String orderId, String appName) {
        String payLink = String.format("xworld://pro.xworld.app/pay?mch_id=%s&order_id=%s&app_name=%s", machId, orderId, appName);
        if (!openOtherApp(payLink, context)) {
            // 打开XWorld失败，则跳转链接去下载
            String link = "https://usdt2.onelink.me/rrNp/6izr7y7j";
            openOtherApp(link, context);
        }
    }

    /**
     * 启动 XWorld 绑定页面
     *
     * @param context 上下文
     * @param machId 商户 ID
     * @param userId 商户的用户 ID
     */
    public static void startBind(Activity context, String machId, String userId) {
        String bindLink = String.format("xworld://pro.xworld.app/bind?mch_id=%s&user_id=%s", machId, userId);
        if (!openOtherApp(bindLink, context)) {
            // 打开XWorld失败，则跳转链接去下载
            String link = "https://usdt2.onelink.me/rrNp/6izr7y7j";
            openOtherApp(link, context);
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
     * @param url 链接
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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
