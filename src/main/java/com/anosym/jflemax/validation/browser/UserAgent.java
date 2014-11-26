/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.browser;

import com.google.common.base.Strings;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author marembo
 */
public class UserAgent implements Serializable {

    private static final long serialVersionUID = 9373625762831L;
    private static final String GOOGLE_CHROME = "AppleWebKit";
    private static final String MOZILLA_FIREFOX = "Firefox";
    private static final String INTERNET_EXPLORER = "MSIE";
    private static final String OPERA = "Opera";
    private static final String MULTIZILLA = "MultiZilla";
    private static final String SAFARI = "Safari";
    private static final String OS_WINDOWS = "Windows";
    private static final String OS_LINUX = "Linux";
    private static final String OS_MACINTOSH = "Macintosh";
    private String browserType;
    private String browserVersion;
    private String os;
    private String osVersion;
    private Locale locale;

    public boolean isWindows() {
        return os != null && os.trim().equals(OS_WINDOWS);
    }

    public boolean isLinux() {
        return os != null && os.trim().equals(OS_LINUX);
    }

    public boolean isMacintosh() {
        return os != null && os.trim().equals(OS_MACINTOSH);
    }

    public boolean isChrome() {
        return browserType != null && browserType.trim().equalsIgnoreCase(GOOGLE_CHROME);
    }

    public boolean isIE() {
        return browserType != null && browserType.trim().equalsIgnoreCase(INTERNET_EXPLORER);
    }

    public boolean isFirefox() {
        return browserType != null && browserType.trim().equalsIgnoreCase(MOZILLA_FIREFOX);
    }

    public boolean isOpera() {
        return browserType != null && browserType.trim().equalsIgnoreCase(OPERA);
    }

    public boolean isSafari() {
        return browserType != null && browserType.trim().equalsIgnoreCase(SAFARI);
    }

    public boolean isMultizilla() {
        return browserType != null && browserType.trim().equalsIgnoreCase(MULTIZILLA);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    private float getVersion() {
        try {
            final int ix = !Strings.isNullOrEmpty(browserVersion) ? browserVersion.indexOf(';') : -1;
            final String versionNumber;
            if (ix > -1) {
                versionNumber = browserVersion.substring(0, ix);
            } else {
                versionNumber = browserVersion;
            }
            if (!Strings.isNullOrEmpty(versionNumber)) {
                return Float.parseFloat(versionNumber);
            }
        } catch (Exception e) {
            //ignore
        }
        return Float.MAX_VALUE;
    }

    public boolean isIE8OrLower() {
        return (isWindows() && getVersion() <= 8.0);
    }

    public boolean isIE11OrHigher() {
        final Float version = getVersion();
        final boolean isValidVersion = version >= 11.0 && version < Float.MAX_VALUE;
        return (isWindows() && isValidVersion);
    }

    public static String getWindowsVersion(String ntVersion) {
        return WINDOWS_VERSION_MAPPING.get(ntVersion);
    }
    private static final Map<String, String> WINDOWS_VERSION_MAPPING = new HashMap<>();

    static {
        WINDOWS_VERSION_MAPPING.put("windows nt 6.3", "	windows 8.1");
        WINDOWS_VERSION_MAPPING.put("windows nt 6.2", "	windows 8");
        WINDOWS_VERSION_MAPPING.put("windows nt 6.1", "	windows 7");
        WINDOWS_VERSION_MAPPING.put("windows nt 6.0", "	windows Vista");
        WINDOWS_VERSION_MAPPING.put("windows nt 5.2", "	windows Server 2003; windows XP x64 Edition");
        WINDOWS_VERSION_MAPPING.put("windows nt 5.1", "	windows XP");
        WINDOWS_VERSION_MAPPING.put("windows nt 5.01", "windows 2000, Service Pack 1");
        WINDOWS_VERSION_MAPPING.put("windows nt 5.0", "	windows 2000");
        WINDOWS_VERSION_MAPPING.put("windows nt 4.0", "Microsoft windows nt 4.0");
        WINDOWS_VERSION_MAPPING.put("windows 98", "windows 98");
        WINDOWS_VERSION_MAPPING.put("windows 95", "windows 95");
        WINDOWS_VERSION_MAPPING.put("windows ce", "windows CE");
    }
}
