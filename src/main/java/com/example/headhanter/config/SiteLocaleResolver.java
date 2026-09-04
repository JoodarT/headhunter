package com.example.headhanter.config;

import com.example.headhanter.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SiteLocaleResolver implements LocaleResolver {

    static final String SESSION_ATTR = "SITE_LANG";

    private final List<Locale> supported = new ArrayList<>();
    private final Locale defaultLocale;
    private final String cookieName;
    private final int cookieMaxAge;
    private final UserService userService;

    public SiteLocaleResolver(List<String> supportedTags, String defaultTag, String cookieName,
                              int cookieMaxAgeDays, UserService userService) {
        for (String tag : supportedTags) {
            this.supported.add(Locale.forLanguageTag(tag));
        }
        this.defaultLocale = Locale.forLanguageTag(defaultTag);
        this.cookieName = cookieName;
        this.cookieMaxAge = cookieMaxAgeDays * 24 * 60 * 60;
        this.userService = userService;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_ATTR) instanceof Locale) {
            return (Locale) session.getAttribute(SESSION_ATTR);
        }

        String email = getEmail();
        if (email != null) {
            try {
                String tag = userService.getUserByEmail(email).getLocale();
                if (tag != null && !tag.isBlank()) {
                    Locale fromDb = normalize(Locale.forLanguageTag(tag));
                    request.getSession(true).setAttribute(SESSION_ATTR, fromDb);
                    return fromDb;
                }
            } catch (RuntimeException e) {
                return defaultLocale;
            }
        }

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (cookieName.equals(c.getName()) && c.getValue() != null && !c.getValue().isBlank()) {
                    return normalize(Locale.forLanguageTag(c.getValue()));
                }
            }
        }

        return defaultLocale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Locale chosen = normalize(locale);

        request.getSession(true).setAttribute(SESSION_ATTR, chosen);

        if (response != null) {
            Cookie cookie = new Cookie(cookieName, chosen.toLanguageTag());
            cookie.setPath("/");
            cookie.setMaxAge(cookieMaxAge);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        String email = getEmail();
        if (email != null) {
            userService.updateLocale(email, chosen.getLanguage());
        }
    }

    private String getEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            return null;
        }
        return auth.getName();
    }

    private Locale normalize(Locale candidate) {
        if (candidate != null) {
            for (Locale s : supported) {
                if (s.getLanguage().equals(candidate.getLanguage())) {
                    return s;
                }
            }
        }
        return defaultLocale;
    }
}
