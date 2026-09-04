package com.example.headhanter.config;

import com.example.headhanter.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;
import java.util.List;


@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Value("${app.locale.supported}")
    private String supportedRaw;
    @Value("${app.locale.default}")
    private String defaultTag;
    @Value("${app.locale.cookie-name}")
    private String cookieName;
    @Value("${app.locale.cookie-max-age-days}")
    private int cookieMaxAgeDays;

    @Bean
    public LocaleResolver localeResolver(UserService userService) {
        List<String> supported = Arrays.asList(supportedRaw.split(","));
        return new SiteLocaleResolver(supported, defaultTag, cookieName, cookieMaxAgeDays, userService);
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        interceptor.setIgnoreInvalidLocale(true);
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
