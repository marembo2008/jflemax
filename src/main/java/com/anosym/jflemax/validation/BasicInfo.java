package com.anosym.jflemax.validation;

import javax.enterprise.context.ApplicationScoped;

import static com.anosym.jflemax.validation.controller.JFlemaxController.lookup;

/**
 *
 * @author marembo
 */
public abstract class BasicInfo {

    private Class<?> controllerClass;
    private Object controllerBean;
    private boolean applicationScoped;

    public BasicInfo(Class<?> controllerClass) {
        setControllerClass(controllerClass);
    }

    public BasicInfo() {
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public final void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
        this.applicationScoped = controllerClass.isAnnotationPresent(ApplicationScoped.class);
    }

    public Object getController() {
        //We cache this bean since we call this method so often, it brings some overhead.
        if (controllerBean != null && applicationScoped) {
            return controllerBean;
        }
        if (controllerClass != null) {
            return lookup(controllerClass);
        }
        return null;
    }
}
