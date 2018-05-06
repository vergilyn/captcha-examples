package com.vergilyn.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/30
 */
@SpringBootApplication
public class CaptchaApplication extends WebMvcConfigurerAdapter {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(false);
        super.configurePathMatch(configurer);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CaptchaApplication.class);
        app.run(args);
    }
}
