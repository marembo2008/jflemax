package com.anosym.jflemax.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import org.atteo.classindex.IndexAnnotated;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Container to hold several requests processing info.
 * <p>
 * @author marembo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER})
@Inherited
@IndexAnnotated
public @interface OnRequests {

    @Nonbinding
    OnRequest[] onRequests() default {};
}
