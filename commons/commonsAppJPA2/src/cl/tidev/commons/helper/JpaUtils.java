package cl.tidev.commons.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class JpaUtils {

    public static Boolean isToManyAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();
        
        for(Annotation annotation: annotations ) {
            if (OneToMany.class.isAssignableFrom(annotation.getClass())
                    || ManyToMany.class.isAssignableFrom(annotation.getClass())) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isManyToManyAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            if (ManyToMany.class.isAssignableFrom(annotations[i].getClass())) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isManyToOneAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            if (ManyToOne.class.isAssignableFrom(annotations[i].getClass())) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isOneToManyAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            if (OneToMany.class.isAssignableFrom(annotations[i].getClass())) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isIdAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            if (Id.class.isAssignableFrom(annotations[i].getClass())
                    || EmbeddedId.class.isAssignableFrom(annotations[i].getClass())) {
                return true;
            }
        }

        return false;
    }
}