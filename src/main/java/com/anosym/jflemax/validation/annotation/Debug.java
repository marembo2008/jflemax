package com.anosym.jflemax.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.atteo.classindex.IndexAnnotated;

/**
 * If found on any controller, the application will be set to debug mode and the configuration informations will be loaded everytime a request is
 * made. It has a default value of true. The reload is done after every few seconds. You can change the reload period by setting the {@link Debug#reloadPeriodInSeconds()
 * } value
 * <p>
 * @author marembo
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface Debug {

    boolean value() default true;

    int reloadPeriodInSeconds() default 60;
}
