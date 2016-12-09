package com.demo;

import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.stereotype.Component;

@Component
public class JBossProxyInitializer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new Application.DispatcherServletProxy());
        servletRegistrationBean.setUrlMappings(Collections.singletonList("/*"));

        beanFactory.registerSingleton("dispatcherServletProxyRegistrationBean", servletRegistrationBean);
    }
}
