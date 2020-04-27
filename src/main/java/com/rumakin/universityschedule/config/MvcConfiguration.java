package com.rumakin.universityschedule.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcConfiguration extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebApplicationInitializer.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
