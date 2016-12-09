package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    private static Class<Application> applicationClass = Application.class;
    private volatile static WebApplicationContext webApplicationContext;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(applicationClass, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass, JBossProxyInitializer.class);
    }

    @Override
    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        webApplicationContext = super.createRootApplicationContext(servletContext);
        return webApplicationContext;
    }

    public static class DispatcherServletProxy implements Servlet {

        private Servlet delegate;

        @Override
        public void init(ServletConfig config) throws ServletException {
            delegate = Application.webApplicationContext.getBean(DispatcherServlet.class);
            delegate.init(config);
        }

        @Override
        public ServletConfig getServletConfig() {
            return delegate.getServletConfig();
        }

        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            delegate.service(req, res);
        }

        @Override
        public String getServletInfo() {
            return delegate.getServletInfo();
        }

        @Override
        public void destroy() {
            delegate.destroy();
        }
    }

}
