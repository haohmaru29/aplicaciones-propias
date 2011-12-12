package cl.tidev.commons.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cl.tidev.commons.orm.data.DataType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Column {
	String name();
	DataType type();
	boolean isPK() default false;
}
