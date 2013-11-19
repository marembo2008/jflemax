/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.CacheControl;
import com.anosym.jflemax.validation.annotation.Debug;
import com.anosym.jflemax.validation.annotation.IndexPath;
import com.anosym.jflemax.validation.annotation.JsfPhase;
import com.anosym.jflemax.validation.annotation.JsfPhaseId;
import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.OnRequest;
import com.anosym.jflemax.validation.annotation.OnRequests;
import com.anosym.jflemax.validation.annotation.Principal;
import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.event.PhaseId;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 *
 * @author marembo
 */
public class PageInformation implements Serializable {

  static {
    System.out.println("PageInformation: should not be created more than once!");
  }
  private static final long serialVersionUID = IdGenerator.serialVersionUID(PageInformation.class);
  private static PageInformation pageInformation;
  private Map<String, Set<RequestInfo>> onRequestInfos = new HashMap<String, Set<RequestInfo>>();
  private String contextPath;
  private Comparator<RequestInfo> comparator = new Comparator<RequestInfo>() {
    @Override
    public int compare(RequestInfo o1, RequestInfo o2) {
      Integer p0 = o1.getPriority();
      Integer p1 = o2.getPriority();
      return p1.compareTo(p0);
    }
  };
  private PrincipalInfo principalInfo;
  private IndexPathInfo indexPathInfo;
  private boolean debugging;
  private Calendar lastReload = Calendar.getInstance();
  private int reloadPeriod;
  /**
   * Information relating to page caches.
   */
  private List<CacheControlInfo> cacheControlInfos;
  private ViewExpiredPages viewExpiredPages;

  public ViewExpiredPages getViewExpiredPages() {
    return viewExpiredPages;
  }

  public void setDebugging(boolean debugging) {
    this.debugging = debugging;
  }

  public boolean isDebugging() {
    return debugging;
  }

  public PrincipalInfo getPrincipalInfo() {
    return principalInfo;
  }

  public IndexPathInfo getIndexPathInfo() {
    return indexPathInfo;
  }

  public void setContextName(String contextPath) {
    this.contextPath = contextPath;
  }

  public String getContextName() {
    return contextPath;
  }

  public Map<String, Set<RequestInfo>> getOnRequestInfos() {
    return onRequestInfos;
  }

  public void setOnRequestInfos(Map<String, Set<RequestInfo>> onRequestInfos) {
    this.onRequestInfos = onRequestInfos;
  }

  public void processController(Object controller) {
    try {
      Class<?> cls = controller.getClass();
      addRequest(cls);
    } catch (Exception e) {
      Logger.getLogger(PageInformation.class.getSimpleName()).log(Level.SEVERE, controller + "", e);
    }
  }

  public void processController(Class<?> cls) {
    try {
      addRequest(cls);
      //scan for Principal info
      Method[] ms = cls.getMethods();
      if (pageInformation.principalInfo == null) {
        for (Method method : ms) {
          if (method.getAnnotation(Principal.class) != null) {
            pageInformation.principalInfo = new PrincipalInfo(cls, method);
            break;
          }
        }
      }
      //scan for index path
      if (pageInformation.indexPathInfo == null) {
        for (Method method : ms) {
          if (method.getAnnotation(IndexPath.class) != null) {
            pageInformation.indexPathInfo = new IndexPathInfo(cls, method);
            break;
          }
        }
      }
    } catch (Exception e) {
      Logger.getLogger(PageInformation.class.getSimpleName()).log(Level.SEVERE, cls.getName() + "", e);
    }
  }

  private void addRequest(Class<?> cls) {
    OnRequest onRequest = cls.getAnnotation(OnRequest.class);
    addRequest(onRequest, cls);
    //do we have arrays
    OnRequests onRequests = cls.getAnnotation(OnRequests.class);
    if (onRequests != null) {
      for (OnRequest or : onRequests.onRequests()) {
        addRequest(or, cls);
      }
    }
  }

  private void addRequest(OnRequest onRequest, Class<?> cls) {
    try {
      if (onRequest != null) {
        String controller = onRequest.controller();
        if (controller == null || controller.trim().isEmpty()) {
          controller = cls.getSimpleName();
          if (!controller.isEmpty()) {
            controller = Character.toLowerCase(controller.charAt(0)) + ((controller.length() > 1)
                    ? controller.substring(1) : "");
          }
        }
        String[] toPages = onRequest.toPages();
        JsfPhase[] phases = onRequest.jsfPhases();
        JsfPhaseInfo[] phaseInfos;
        if (phases.length == 0) {
          phaseInfos = new JsfPhaseInfo[]{new JsfPhaseInfo(JsfPhaseId.RENDER_RESPONSE, JsfPhaseIdOption.BEFORE_PHASE)};
        } else {
          phaseInfos = new JsfPhaseInfo[phases.length];
          for (int i = 0; i < phases.length; i++) {
            JsfPhase phase = phases[i];
            phaseInfos[i] = new JsfPhaseInfo(phase.phaseId(), phase.phaseIdOption());
          }
        }
        RequestInfo info = new RequestInfo(controller, new HashSet<String>(Arrays.asList(toPages)),
                onRequest.onRequestMethod(), onRequest.redirectPage(), onRequest.redirect(),
                onRequest.requestStatus(), onRequest.logInStatus(), onRequest.excludedPages(),
                onRequest.redirectStatus(), phaseInfos, onRequest.execute(), onRequest.redirectFailurePage(),
                onRequest.redirectOnResult(), onRequest.redirectPages());
        info.setPriority(onRequest.priority());
        for (String toPage : toPages) {
          Set<RequestInfo> requestInfos = onRequestInfos.get(toPage);
          if (requestInfos == null) {
            requestInfos = new HashSet<RequestInfo>();
            onRequestInfos.put(toPage, requestInfos);
          }
          requestInfos.add(info);
        }
      }
    } catch (Exception e) {
      Logger.getLogger(PageInformation.class.getSimpleName()).log(Level.SEVERE, onRequest + "", e);
    }
  }

  public Set<RequestInfo> getRequestInfos(String page) {
    if (page == null) {
      return null;
    }
    String normalizedPage = page.contains(".") ? page.substring(0, page.lastIndexOf(".")) : page;
    Set<RequestInfo> infos = onRequestInfos.get(page);
    try {
      if (infos == null && page.contains(".")) {
        infos = onRequestInfos.get(normalizedPage);
      }
      {
        //check for relative path universal requests such as /main/*
        final Pattern p = Pattern.compile("[^/]/");
        final Matcher m = p.matcher(page);
        while (m.find()) {
          String mainPage = (page.substring(0, m.end()));
          //check if this page belongs to any of the parent which may have been specified.
          Set<RequestInfo> _uInfo = onRequestInfos.get(mainPage);
          if (_uInfo != null) {
            if (infos == null) {
              infos = _uInfo;
            } else {
              infos.addAll(_uInfo);
            }
          }
        }
        //add the universal requests too?
        Set<RequestInfo> _uInfo = onRequestInfos.get("*");
        if (infos != null) {
          if (_uInfo != null) {
            infos.addAll(_uInfo);
          }
        } else {
          infos = _uInfo;
        }
      }
    } catch (Exception e) {
      Logger.getLogger(PageInformation.class.getSimpleName()).log(Level.SEVERE, page, e);
    }
    List<RequestInfo> list = new ArrayList<RequestInfo>();
    if (infos != null) {
      list.addAll(infos);
      Collections.sort(list, comparator);
    }
    return new LinkedHashSet<RequestInfo>(list);
  }

  public synchronized Set<RequestInfo> getRequestInfos(String page, LoginStatus loginStatus) {
    Set<RequestInfo> infos = getRequestInfos(page);
    String normalizedPage = page.contains(".") ? page.substring(0, page.lastIndexOf(".")) : page;
    try {
      if (infos != null) {
        for (Iterator<RequestInfo> it = infos.iterator(); it.hasNext();) {
          RequestInfo info = it.next();
          if (info.getLoginStatus() != loginStatus && info.getLoginStatus() != LoginStatus.EITHER) {
            it.remove();
            continue;
          }
          if (info.getExcludedPages() != null) {
            for (String expage : info.getExcludedPages()) {
              if (expage != null && (expage.equals(page) || expage.equals(normalizedPage))) {
                it.remove();
                break;
              }
            }
          }
        }
      }
    } catch (Exception e) {
      Logger.getLogger(PageInformation.class.getSimpleName()).log(Level.SEVERE, page, e);
    }
    return infos;
  }

  public Set<RequestInfo> getRequestInfos(String page, LoginStatus loginStatus, PhaseId phaseId, JsfPhaseIdOption phaseIdOption) {
    Set<RequestInfo> infos = getRequestInfos(page);
    String normalizedPage = page.contains(".") ? page.substring(0, page.lastIndexOf(".")) : page;
    try {
      if (infos != null) {
        JsfPhaseId jsfPhaseId = JsfPhaseId.findJsfPhaseId(phaseId);
        for (Iterator<RequestInfo> it = infos.iterator(); it.hasNext();) {
          RequestInfo info = it.next();
          if (info.getLoginStatus() != loginStatus && info.getLoginStatus() != LoginStatus.EITHER) {
            it.remove();
            continue;
          }
          if (info.getJsfPhaseInfos() != null && info.getJsfPhaseInfos().length > 0) {
            boolean forPhase = false;
            for (JsfPhaseInfo jpi : info.getJsfPhaseInfos()) {
              if (jpi.getPhaseId() == jsfPhaseId && jpi.getPhaseIdOption() == phaseIdOption) {
                forPhase = true;
                break;
              }
            }
            if (!forPhase) {
              it.remove();
              continue;
            }
          }
          if (info.getExcludedPages() != null) {
            for (String expage : info.getExcludedPages()) {
              if (expage != null && (expage.equals(page) || expage.equals(normalizedPage))) {
                it.remove();
                break;
              }
            }
          }
        }
      }
    } catch (Exception e) {
      Logger.getLogger(PageInformation.class.getSimpleName()).log(Level.SEVERE, page, e);
    }
    return infos;
  }

  public synchronized void removeFromQueue(RequestInfo requestInfo) {
    Collection<Set<RequestInfo>> values = onRequestInfos.values();
    for (Iterator<Set<RequestInfo>> i = values.iterator(); i.hasNext();) {
      Set<RequestInfo> set = i.next();
      for (Iterator<RequestInfo> ii = set.iterator(); ii.hasNext();) {
        RequestInfo info = ii.next();
        if (info.equals(requestInfo)) {
          ii.remove();
          break;
        }
      }
    }
  }

  private boolean loadPageInformation() {
    if (isDebugging()) {
      Calendar now = Calendar.getInstance();
      if (lastReload == null) {
        return true;
      }
      long lastSec = lastReload.getTimeInMillis();
      long nowSec = now.getTimeInMillis();
      return ((nowSec - lastSec) / 1000) > this.reloadPeriod;
    }
    return false;
  }

  public static PageInformation getPageProcessor() {
    if (pageInformation == null || pageInformation.loadPageInformation()) {
      onApplicationStart();
    }
    return pageInformation;
  }

  private static void processCacheControlInfo(org.reflections.Reflections reflections, PageInformation pageInformation) {
    Set<Class<?>> annotatedBeans = reflections.getTypesAnnotatedWith(CacheControl.class);
    if (annotatedBeans != null) {
      for (Class<?> c : annotatedBeans) {
        CacheControl cc = c.getAnnotation(CacheControl.class);
        if (cc != null) {
          if (pageInformation.cacheControlInfos == null) {
            pageInformation.cacheControlInfos = new ArrayList<CacheControlInfo>();
          }
          pageInformation.cacheControlInfos.add(new CacheControlInfo(cc.urls(), cc.cached()));
        }
      }
    }
  }

  private static void processViewExpiredPages(org.reflections.Reflections reflections, PageInformation pageInformation) {
    Set<Class<?>> annotatedBeans = reflections.getTypesAnnotatedWith(com.anosym.jflemax.validation.annotation.ViewExpiredPages.class);
    if (annotatedBeans != null) {
      String pages[] = {};
      for (Class<?> c : annotatedBeans) {
        com.anosym.jflemax.validation.annotation.ViewExpiredPages viewExpiredPages =
                c.getAnnotation(com.anosym.jflemax.validation.annotation.ViewExpiredPages.class);
        CacheControl cc = c.getAnnotation(CacheControl.class);
        if (viewExpiredPages != null) {
          pages = Arrays.copyOf(pages, pages.length + viewExpiredPages.pages().length);
          System.arraycopy(viewExpiredPages.pages(), 0, pages, (pages.length - viewExpiredPages.pages().length), viewExpiredPages.pages().length);
        }
      }
      pageInformation.viewExpiredPages = new ViewExpiredPages(pages);
    }
  }

  public static boolean isCurrentPageCached(String page) {
    if (pageInformation.cacheControlInfos != null) {
      for (CacheControlInfo cci : pageInformation.cacheControlInfos) {
        if (cci.isCacheControlled(page)) {
          return cci.isCached();
        }
      }
    }
    //by default the current page is cached.
    return true;
  }

  public static void onApplicationStart() {
    pageInformation = new PageInformation();
    org.reflections.Reflections reflections = new Reflections(new ConfigurationBuilder()
            .filterInputsBy(new FilterBuilder()
            .include(FilterBuilder.prefix(System.getProperty(JFlemaxController.APPLICATION_PACKAGE, "com")))
            .include(FilterBuilder.prefix("com.flemax")))
            .setUrls(ClasspathHelper.forPackage("com"))
            .setScanners(new SubTypesScanner(),
            new TypeAnnotationsScanner(),
            new ResourcesScanner()));
    //process cached controlls.
    processCacheControlInfo(reflections, pageInformation);
    processViewExpiredPages(reflections, pageInformation);
    //scan for onrequests and onrequest
    Set<Class<?>> annotatedBeans = reflections.getTypesAnnotatedWith(OnRequests.class);
    Set<Class<?>> debugBeans = reflections.getTypesAnnotatedWith(Debug.class);
    if (debugBeans != null) {
      for (Class<?> cls : debugBeans) {
        Debug dbg = cls.getAnnotation(Debug.class);
        if (dbg != null) {
          if (dbg.value()) {
            pageInformation.setDebugging(true);
            pageInformation.reloadPeriod = dbg.reloadPeriodInSeconds();
            break;
          }
        }
      }
    }
    for (Class<?> c : annotatedBeans) {
      pageInformation.processController(c);
    }
    annotatedBeans = reflections.getTypesAnnotatedWith(OnRequest.class);
    for (Class<?> c : annotatedBeans) {
      pageInformation.processController(c);
    }
  }

  public static void onApplicationStart(String applicationPackage, String contextName) {
    pageInformation = new PageInformation();
    pageInformation.setContextName(contextName);
    org.reflections.Reflections reflections = new Reflections(new ConfigurationBuilder()
            .filterInputsBy(new FilterBuilder()
            .include(FilterBuilder.prefix(applicationPackage))
            .include(FilterBuilder.prefix("com.flemax")))
            .setUrls(ClasspathHelper.forPackage("com"))
            .setScanners(new SubTypesScanner(),
            new TypeAnnotationsScanner(),
            new ResourcesScanner()));
    //scan for current principal
    Set<Method> m = reflections.getMethodsAnnotatedWith(Principal.class);
    if (m != null && !m.isEmpty()) {
      Method method = m.iterator().next();
      pageInformation.principalInfo = new PrincipalInfo(method.getDeclaringClass(), method);
    }
    //scan for index path
    Set<Method> im = reflections.getMethodsAnnotatedWith(IndexPath.class);
    if (im != null && !im.isEmpty()) {
      Method method = im.iterator().next();
      pageInformation.indexPathInfo = new IndexPathInfo(method.getDeclaringClass(), method);
    }
    //scan for onrequests and onrequest
    Set<Class<?>> annotatedBeans = reflections.getTypesAnnotatedWith(OnRequests.class);
    for (Class<?> c : annotatedBeans) {
      pageInformation.processController(c);
    }
    annotatedBeans = reflections.getTypesAnnotatedWith(OnRequest.class);
    for (Class<?> c : annotatedBeans) {
      pageInformation.processController(c);
    }
  }
}
