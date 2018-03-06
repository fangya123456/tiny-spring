package com.fy.example.spring.web.util;

import com.fy.example.spring.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * URL 路径匹配帮助类
 *
 * @author: ya.fang
 * @create: 2017-12-18
 **/
public class UrlPathHelper {

    private static final String WEBSPHERE_URI_ATTRIBUTE = "com.ibm.websphere.servlet.uri_non_decoded";

    /**web范围内统一标志位*/
    static volatile Boolean websphereComplianceFlag;
    /**false-相对路径 true-绝对路径*/
    private boolean alwaysUseFullPath = false;

    private boolean urlDecode = true;
    /**是否移除分隔符*/
    private boolean removeSemicolonContent = true;

    public boolean isAlwaysUseFullPath() {
        return alwaysUseFullPath;
    }

    public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
        this.alwaysUseFullPath = alwaysUseFullPath;
    }

    public boolean isRemoveSemicolonContent() {
        return removeSemicolonContent;
    }

    public void setRemoveSemicolonContent(boolean removeSemicolonContent) {
        this.removeSemicolonContent = removeSemicolonContent;
    }

    /**
     * 获取查找到的请求路径
     * @param request
     * @return
     */
    public String getLookupPathForRequest(HttpServletRequest request){
        // Always use full path within current servlet context?
        if (this.alwaysUseFullPath) {
            return getPathWithinApplication(request);
        }
        // Else, use path within current servlet mapping if applicable
        String rest = getPathWithinServletMapping(request);
        if (!"".equals(rest)) {
            return rest;
        }
        else {
            return getPathWithinApplication(request);
        }
    }



    /**
     * 获取请求的URI:
     *  例如web应用名为news，请求路径为http://localhost:8080/news/main/list.jsp，则该方法返回/news/main/list.jsp
     * @param request
     * @return
     */
    public String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null) {
            uri = request.getRequestURI();
        }
        return uri;
    }

    /**
     * 获取应用上下路径：
     *  例如web应用名为news，请求路径为http://localhost:8080/news/main/list.jsp，则该方法返回/news
     * @param request
     * @return
     */
    public String getContextPath(HttpServletRequest request){
        String contextPath = (String) request.getAttribute(WebUtils.INCLUDE_CONTEXT_PATH_ATTRIBUTE);
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }
        if ("/".equals(contextPath)) {
            contextPath = "";
        }
        return contextPath;
    }

    /**
     * 获取servlet路径:
     *  例如web应用名为news，请求路径为http://localhost:8080/news/main/list.jsp，则该方法返回/main/list.jso
     * @param request
     * @return
     */
    public String getServletPath(HttpServletRequest request) {
        String servletPath = (String) request.getAttribute(WebUtils.INCLUDE_SERVLET_PATH_ATTRIBUTE);
        if (servletPath == null) {
            servletPath = request.getServletPath();
        }
        if (servletPath.length() > 1 && servletPath.endsWith("/") && shouldRemoveTrailingServletPathSlash(request)) {
            servletPath = servletPath.substring(0, servletPath.length() - 1);
        }
        return servletPath;
    }

    /**
     * Return the path within the servlet mapping for the given request,
     * i.e. the part of the request's URL beyond the part that called the servlet,
     * or "" if the whole URL has been used to identify the servlet.
     * <p>Detects include request URL if called within a RequestDispatcher include.
     * <p>E.g.: servlet mapping = "/*"; request URI = "/test/a" -> "/test/a".
     * <p>E.g.: servlet mapping = "/"; request URI = "/test/a" -> "/test/a".
     * <p>E.g.: servlet mapping = "/test/*"; request URI = "/test/a" -> "/a".
     * <p>E.g.: servlet mapping = "/test"; request URI = "/test" -> "".
     * <p>E.g.: servlet mapping = "/*.test"; request URI = "/a.test" -> "".
     * @param request current HTTP request
     * @return the path within the servlet mapping, or ""
     */
    public String getPathWithinServletMapping(HttpServletRequest request) {
        String pathWithinApp = getPathWithinApplication(request);
        String servletPath = getServletPath(request);
        String sanitizedPathWithinApp = getSanitizedPath(pathWithinApp);
        String path;

        // If the app container sanitized the servletPath, check against the sanitized version
        if (servletPath.contains(sanitizedPathWithinApp)) {
            path = getRemainingPath(sanitizedPathWithinApp, servletPath, false);
        }
        else {
            path = getRemainingPath(pathWithinApp, servletPath, false);
        }

        if (path != null) {
            // Normal case: URI contains servlet path.
            return path;
        }
        else {
            // 获取请求URL中的额外路径信息。额外路径信息是请求URL中的位于Servlet的路径之后和查询参数之前的内容，它以“/”开头。
            String pathInfo = request.getPathInfo();
            if (pathInfo != null) {
                // Use path info if available. Indicates index page within a servlet mapping?
                // e.g. with index page: URI="/", servletPath="/index.html"
                return pathInfo;
            }
            if (!this.urlDecode) {
                // No path info... (not mapped by prefix, nor by extension, nor "/*")
                // For the default servlet mapping (i.e. "/"), urlDecode=false can
                // cause issues since getServletPath() returns a decoded path.
                // If decoding pathWithinApp yields a match just use pathWithinApp.
                path = getRemainingPath(pathWithinApp, servletPath, false);
                if (path != null) {
                    return pathWithinApp;
                }
            }
            // Otherwise, use the full servlet path.
            return servletPath;
        }
    }

    /**
     * 获取给定请求的在web应用上下文的路径
     * @param request
     * @return
     */
    public String getPathWithinApplication(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        String path = getRemainingPath(requestUri, contextPath, true);
        if (path != null) {
            // Normal case: URI contains context path.
            return (StringUtils.hasText(path) ? path : "/");
        }
        else {
            return requestUri;
        }
    }

    /**
     * 获取请求URI相对于mapping路径的相对路径
     * @param requestUri
     * @param mapping
     * @param ignoreCase
     * @return
     */
    private String getRemainingPath(String requestUri, String mapping, boolean ignoreCase) {
        int index1 = 0;
        int index2 = 0;
        for (; (index1 < requestUri.length()) && (index2 < mapping.length()); index1++, index2++) {
            char c1 = requestUri.charAt(index1);
            char c2 = mapping.charAt(index2);
            if (c1 == ';') {
                index1 = requestUri.indexOf('/', index1);
                if (index1 == -1) {
                    return null;
                }
                c1 = requestUri.charAt(index1);
            }
            if (c1 == c2) {
                continue;
            }
            else if (ignoreCase && (Character.toLowerCase(c1) == Character.toLowerCase(c2))) {
                continue;
            }
            return null;
        }
        if (index2 != mapping.length()) {
            return null;
        }
        else if (index1 == requestUri.length()) {
            return "";
        }
        else if (requestUri.charAt(index1) == ';') {
            index1 = requestUri.indexOf('/', index1);
        }
        return (index1 != -1 ? requestUri.substring(index1) : "");
    }

    /**
     * 是否需要移除servlet路径尾部的反斜杠
     * @param request
     * @return
     */
    private boolean shouldRemoveTrailingServletPathSlash(HttpServletRequest request) {
        if (request.getAttribute(WEBSPHERE_URI_ATTRIBUTE) == null) {
            // Regular servlet container: behaves as expected in any case,
            // so the trailing slash is the result of a "/" url-pattern mapping.
            // Don't remove that slash.
            return false;
        }
        if (websphereComplianceFlag == null) {
            ClassLoader classLoader = UrlPathHelper.class.getClassLoader();
            String className = "com.ibm.ws.webcontainer.WebContainer";
            String methodName = "getWebContainerProperties";
            String propName = "com.ibm.ws.webcontainer.removetrailingservletpathslash";
            boolean flag = false;
            try {
                Class<?> cl = classLoader.loadClass(className);
                Properties prop = (Properties) cl.getMethod(methodName).invoke(null);
                flag = Boolean.parseBoolean(prop.getProperty(propName));
            }
            catch (Throwable ex) {
                System.out.println("Could not introspect WebSphere web container properties: " + ex);
            }
            websphereComplianceFlag = flag;
        }
        // Don't bother if WebSphere is configured to be fully Servlet compliant.
        // However, if it is not compliant, do remove the improper trailing slash!
        return !websphereComplianceFlag;
    }

    private String getSanitizedPath(final String path) {
        String sanitized = path;
        while (true) {
            int index = sanitized.indexOf("//");
            if (index < 0) {
                break;
            }
            else {
                sanitized = sanitized.substring(0, index) + sanitized.substring(index + 1);
            }
        }
        return sanitized;
    }
}
