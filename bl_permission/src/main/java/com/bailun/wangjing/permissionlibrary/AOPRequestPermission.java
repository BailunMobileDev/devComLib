package com.bailun.wangjing.permissionlibrary;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.bailun.wangjing.permissionlibrary.annotation.RequestPermission;
import com.bailun.wangjing.permissionlibrary.annotation.RequestPermissionForbid;
import com.bailun.wangjing.permissionlibrary.annotation.RequestPermissionRefuse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * create by wangjing on 2018/12/29 0029
 * description: AOP的写法执行权限请求
 */
@Aspect
public class AOPRequestPermission {

    private static Context context;
    private static PermissionFailDefaultCallBack failDefaultCallBack;


    public static void init(Application application){
        context = application;
    }

    public static void setFailDefaultCallBack(PermissionFailDefaultCallBack failDefaultCallBack) {
        AOPRequestPermission.failDefaultCallBack = failDefaultCallBack;
    }

    private static final String REQUEST_PERMISSION_POINTCUT =
            "execution(@com.bailun.wangjing.permissionlibrary.annotation.RequestPermission * *(..))";

    @Pointcut(REQUEST_PERMISSION_POINTCUT + " && @annotation(requestPermission)")
    public void requestPermissionMethod(RequestPermission requestPermission) {
    }

    @Around("requestPermissionMethod(requestPermission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final RequestPermission requestPermission) {
        final Object object = joinPoint.getThis();
        Context tmpContext = context;
        if (object instanceof Fragment){
            tmpContext = ((Fragment) object).getActivity();
        } else if (object instanceof FragmentActivity){
            tmpContext = (Context) object;
        }
        TLPermissionUtils.requestByActivity(tmpContext, requestPermission.permissions()
                , requestPermission.request(), new PermissionResult() {
            @Override
            public void onAllow() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onRefuse(int request) {
                findMethodByAnnotation(object, RequestPermissionRefuse.class,
                        request, true, requestPermission.refuseTip());
            }

            @Override
            public void onForbid(int request) {
                findMethodByAnnotation(object, RequestPermissionForbid.class,
                        request, false, requestPermission.forbidTip());
            }
        });
    }

    private void findMethodByAnnotation(Object object, Class<? extends Annotation> annotationClass
            , int requestCode, boolean isRefuse, String tip){
        if(object == null){
            return;
        }
        Class<?> cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();
        boolean isHasAnnotation = false;
        for (Method method : methods) {
            isHasAnnotation = method.isAnnotationPresent(annotationClass);
            if (isHasAnnotation) {
                method.setAccessible(true);
                Class<?>[] types = method.getParameterTypes();
                if (types.length != 1 && types[0] == int.class)
                    throw new IllegalArgumentException("Request Permission Callback Parameter Error");
                try {
                    method.invoke(object, requestCode);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (!isHasAnnotation && failDefaultCallBack != null){
            if (isRefuse){
                failDefaultCallBack.onRequestRefuse(requestCode, tip);
            } else {
                failDefaultCallBack.onRequestForbid(requestCode, tip);
            }
        }
    }

}
