# bl_permission说明
bl_permission是一个使用AOP来处理Android6.0后中运行时权限申请的库.

## 配置
在项目的build.gradle中增加
<pre>
buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        ......
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'//aop配置
    }
}
</pre>
在编译的主工程和编写Aop代码的模块的build.gradle增加
<pre>
apply plugin: 'android-aspectjx'
</pre>
## 初始化
<pre>
AOPRequestPermission.init(application);
</pre>
## 用法
在Activity或者Fragment中在需要权限的方法的上加上注解.需要两个参数一个是请求码request,一个是需要的权限的数组permissions.  
还有两个非必须参数,refuseTip和forbidTip.分别为默认实现回调参数.
<pre>
@RequestPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE}, request = 0)
    private void requestSinglePermission(){
        tvInfo.setText("申请单个权限被允许");
    }

@RequestPermission(permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, request = 1)
    private void requestMultiplePermission(){
        tvInfo.setText("申请多个权限被允许");
    }
</pre>
因为权限的处理中用户可能会拒绝或者禁止提示,加入注解就会覆盖默认实现.这里在相应处理的方法加入相关注解既可.
<pre>
@RequestPermissionRefuse
    private void requestPermissionRefuse(int request){
        switch (request){
            case 0:
                tvInfo.setText("申请单个权限被拒绝");
                break;
            case 1:
                tvInfo.setText("申请多个权限被拒绝");
                break;
        }
    }

@RequestPermissionForbid
    private void requestPermission(int request){
        switch (request){
            case 0:
                tvInfo.setText("申请单个权限被禁止");
                break;
            case 1:
                tvInfo.setText("申请多个权限被禁止");
                break;
        }
    }
</pre>
因为考虑到不同项目可能会有项目统一实现,这里采取的方案是增加默认回调处理.注解上的refuseTip和forbidTip就是这里各自回调的tip.
<pre>
AOPRequestPermission.setFailDefaultCallBack(new PermissionFailDefaultCallBack() {
    @Override
    public void onRequestRefuse(int requestCode, String refuseTip) {
        Log.d("AOPRequestPermission", "onRequestRefuse: ");
    }
    
    @Override
    public void onRequestForbid(int requestCode, String forbidTip) {
        Log.d("AOPRequestPermission", "onRequestForbid: ");
    }
});
</pre>
## 注意事项
+ 注解不要在静态方法上使用,不保证能生效.

## 版本说明
V0.0.1 实现悬浮窗权限适配,开放权限失败默认回调,逻辑优化,提供跳转设置页方法.

