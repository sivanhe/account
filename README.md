# XWorld应用的支付与体现绑定
## （1）首先在 AndroidManifest.xml 中添加如下：
    <queries>
        <package android:name="pro.xworld.app" />
    </queries>

## （2）需要支付时，调用 {@link #startPay(Context, String, String, String)} 方法
## （3）需要绑定时，调用 {@link #startBind(Context, String, String)} 方法
## （4）h5端调用示例：
    <a href="xworld://pro.xworld.app/pay?mch_id={商户ID}&order_id={订单ID}&app_name={你的应用名称}"></a>
    <a href="xworld://pro.xworld.app/bind?mch_id={商户ID}&user_id={用户ID}"></a>
