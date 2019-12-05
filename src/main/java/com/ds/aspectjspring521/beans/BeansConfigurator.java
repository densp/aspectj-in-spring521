/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521.beans;

import com.ds.aspectjspring521.DSAspectInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 18:22
 */
@Configuration
public class BeansConfigurator {

    @Bean("beanViaAnnotationConfigNoInterface")
    public BeanViaAnnotationConfigNoInterface beanViaAnnotationConfigNoInterface(Util util) {
        return new BeanViaAnnotationConfigNoInterface(util);
    }

    @Bean("beanViaAnnotationConfigWithInterface")
    public DSAspectInterface beanViaAnnotationConfigWithInterface(Util util) {
        return new BeanViaAnnotationConfigWithInterface(util);
    }

}
