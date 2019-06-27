# Permissionlibrary说明
Permissionlibrary是一个使用AOP来处理Android6.0后中运行时权限申请的库。

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
## 用法
在Activity或者Fragment中在需要权限的方法的上加上注解。需要两个参数一个是请求码request，一个是需要的权限的数组permissions。
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
因为权限的处理中用户可能会拒绝或者禁止提示，根据灵活性考虑，这个库默认在禁止的时候会弹出提示给用户，一旦自己加入注解就会覆盖默认实现。其他的交给使用者自己处理，  
这里在相应处理的方法加入相关注解既可。
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
## 注意事项
+ 禁止和拒绝的注解在静态方法上不会生效。请求权限的可以在静态方法上使用，但是不推荐这样使用。因为不好自己实现禁止和拒绝的逻辑。
+ 不要在activity生命周期中的onResume使用，因为这边采取方法是自己开一个透明无界面，没大小的activity去请求权限的做法。在请求完之后会一直触发onResume所以造成无限循环。