package cn.cglib.example;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Ȩ��У��
 * 
 * @author Maurice Jin
 */
public class AuthProxy implements MethodInterceptor {

    private String name; // ��Ա��¼��

    public AuthProxy(String name) {
        this.name = name;
    }

    /**
     * Ȩ��У�飬�����Ա��Ϊ:maurice������Ȩ����������������ʾû��Ȩ��
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!"maurice".equals(this.name)) {
            System.out.println("AuthProxy:you have no permits to do manager!");
            return null;
        }
        return proxy.invokeSuper(obj, args);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
