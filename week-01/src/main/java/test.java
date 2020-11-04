/**
 * fshows.com
 * Copyright (C) 2020-2020 All Rights Reserved.
 */

import java.lang.reflect.Method;

/**
 * @author yangj
 * @version test.java, v 0.1 2020-11-04 3:42 下午 yangj
 */
public class test {

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/Hello.xlass";
        MyClassLoader loader = new MyClassLoader(path);
        Class<?> aClass = loader.findClass("Hello");
        try {
            Object obj = aClass.newInstance();
            Method method = aClass.getMethod("hello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
