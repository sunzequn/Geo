package com.sunzequn.geo.data.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Sloriac on 15/12/20.
 */
public class ClassUtils {

    /**
     * 根据属性名得到属性值
     *
     * @param t     属性所属的类
     * @param field 类的属性名
     * @param <T>   泛型
     * @return 该属性的值
     */
    public static <T> Object getFieldValue(T t, String field) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, t.getClass());
            Method method = propertyDescriptor.getReadMethod();
            if (method == null) {
                throw new RuntimeException("No read method for bean property "
                        + t.getClass() + " " + field);
            }
            return method.invoke(t);
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置类的属性值
     *
     * @param t     属性所属的类
     * @param field 属性名
     * @param value 属性值
     * @param <T>   泛型
     */
    public static <T> void setFieldValue(T t, String field, Object value) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, t.getClass());
            Method method = propertyDescriptor.getWriteMethod();
            if (method == null) {
                throw new RuntimeException("No write method for bean property "
                        + t.getClass() + " " + field);
            }
            method.invoke(t, value);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
