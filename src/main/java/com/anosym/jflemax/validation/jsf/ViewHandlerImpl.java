/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.jsf;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author marembo
 */
public class ViewHandlerImpl extends ViewHandlerWrapper {

  private ViewHandler parent;

  public ViewHandlerImpl(ViewHandler parent) {
    this.parent = parent;
  }

  @Override
  public ViewHandler getWrapped() {
    return this.parent;
  }

  @Override
  public UIViewRoot restoreView(FacesContext context, String viewId) {
    UIViewRoot viewRoot = parent.restoreView(context, viewId);
    if (viewRoot == null && JFlemaxController.getPageInformation().getViewExpiredPages() != null) {
      for (String page : JFlemaxController.getPageInformation().getViewExpiredPages().getPages()) {
        if (viewId.equals(JFlemaxController.indexPath())
                || viewId.startsWith(page)) {
          parent.initView(context);
          viewRoot = parent.createView(context, viewId);
          context.setViewRoot(viewRoot);
          try {
            parent.getViewDeclarationLanguage(context, viewId).buildView(context, viewRoot);
          } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
          }
          break;
        }
      }
    }
    return viewRoot;
  }
}
