package com.luxoft.bankapp.test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import static com.luxoft.bankapp.annotation.annotation.*;

/**
 * Created by SCJP on 02.02.2015.
 */
public class TestService {


    public static boolean isEquals (Object o1, Object o2) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

    if (o1.getClass() != o2.getClass()) {

            return false;
        }
        if (!(o1.getClass().getCanonicalName().equals(o2.getClass().getCanonicalName()))) {
            return false;
        }

        Field[] allFields1 = o1.getClass().getSuperclass().getDeclaredFields();
        Field[] allFields2 = o2.getClass().getSuperclass().getDeclaredFields();
        Field[] fields1 = o1.getClass().getDeclaredFields();
        Field[] fields2 = o2.getClass().getDeclaredFields();

        try {
            if (!(isEqualsField(o1, o2, allFields1, allFields2))) {
                return false;
            }

        if(!(isEqualsField(o1, o2, fields1, fields2))) {
            return false;
        }

        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
            return true;
    }

    public static boolean isEqualsField(Object o1, Object o2, Field[] fields1, Field[] fields2) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

             if(fields1.length != fields2.length) {

            return false;
        }

            for (int i = 0; i < fields1.length; i++) {
                Field f1 = fields1[i];
                Field f2 = fields2[i];

                if (f1.getType() != f2.getType()) {
                    return false;
                }

            if ((f1.isAnnotationPresent(NoDB.class)) && (f2.isAnnotationPresent(NoDB.class))) {
                continue;
            }

                Object value1 =
                        new PropertyDescriptor(f1.getName(), o1.getClass())
                                .getReadMethod().invoke(o1);

                Object value2 =
                        new PropertyDescriptor(f1.getName(), o2.getClass())
                                .getReadMethod().invoke(o2);


                if ((Collection.class.isAssignableFrom(f1.getDeclaringClass())) && (Collection.class.isAssignableFrom(f2.getDeclaringClass()))) {

                    Collection collection1 = ((Collection) value1);
                    Collection collection2 = ((Collection) value2);

                    if(collection1.size() != collection2.size()) {
                        return false;
                    }

                    if(!isEqualsCollection(collection1, collection2)) {
                        return false;
                    }
                }

                if(value1 == null && value2 == null) {
                    continue;
                }

                if(value1 == null || value2 == null) {
                    return false;
                }


                if(!value1.equals(value2)) {
                    return false;
                }
            }

        return true;
    }

    public static boolean isEqualsCollection(Collection c1, Collection c2) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Iterator iter1 = c1.iterator();
        Iterator iter2 = c2.iterator();

        while (iter1.hasNext()) {
            Object o1 = iter1.next();
            Object o2 = iter2.next();
            return isEquals(o1, o2);
        }
        return true;
    }


}