package com.sunzequn.geocities.data.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Sloriac on 15/12/20.
 */
public class ClassUtil {

    /**
     * Get the value of a property by it`s name.
     *
     * @param t     The object from which we retrieve values.
     * @param field The name of the property.
     * @return the value of the property.
     */
    public static <T> Object getFieldValue(T t, String field) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, t.getClass());
            Method method = propertyDescriptor.getReadMethod();
            if (method == null) {
                throw new RuntimeException("No read method for bean property "
                        + t.getClass() + " " + field);
            }
            Object value = method.invoke(t, new Object[]{});
            return value;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void setFieldValue(T t, String field, Object value) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, t.getClass());
            Method method = propertyDescriptor.getWriteMethod();
            if (method == null) {
                throw new RuntimeException("No write method for bean property "
                        + t.getClass() + " " + field);
            }
            method.invoke(t, value);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
