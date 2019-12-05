/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521.controller;

import com.ds.aspectjspring521.DSAspectInterface;
import com.ds.aspectjspring521.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 21:53
 */
@RestController
@RequestMapping(path = "/", produces = MediaType.TEXT_PLAIN_VALUE)
public class AppRestController {

    @Autowired
    @Qualifier("beanFromXmlConfigNoInterface")
    private BeanFromXmlConfigNoInterface xmlConfigNoInterface;
    @Autowired
    @Qualifier("beanFromXmlConfigWithInterface")
    private DSAspectInterface xmlConfigWithInterface;

    @Autowired
    @Qualifier("beanViaAnnotationConfigNoInterface")
    private BeanViaAnnotationConfigNoInterface annotationConfigNoInterface;
    @Autowired
    @Qualifier("beanViaAnnotationConfigWithInterface")
    private DSAspectInterface annotationConfigWithInterface;

    @Autowired
    @Qualifier("beanFromAutoscanNoInterface")
    private BeanFromAutoscanNoInterface scanNoInterface;
    @Autowired
    @Qualifier("beanFromAutoscanWithInterface")
    private DSAspectInterface scanWithInterface;

    @Autowired
    private Util util;

    private NotABean notABean;

    @PostConstruct
    private void init() {
        this.notABean = new NotABean(this.util);
    }

    @GetMapping(path = "/test")
    public String testAspects() {
        StringBuilder sb = new StringBuilder();
        sb.append("autoscan, no interface, aspect: ").append(this.scanNoInterface.doAspect()).append(System.lineSeparator());
        sb.append("autoscan, no interface, no aspect: ").append(this.scanNoInterface.doNoAspect()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("autoscan, with interface, aspect: ").append(this.scanWithInterface.doAspect()).append(System.lineSeparator());
        sb.append("autoscan, with interface, no aspect: ").append(this.scanWithInterface.doNoAspect()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("xml, no interface, aspect: ").append(this.xmlConfigNoInterface.doAspect()).append(System.lineSeparator());
        sb.append("xml, no interface, no aspect: ").append(this.xmlConfigNoInterface.doNoAspect()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("xml, with interface, aspect: ").append(this.xmlConfigWithInterface.doAspect()).append(System.lineSeparator());
        sb.append("xml, with interface, no aspect: ").append(this.xmlConfigWithInterface.doNoAspect()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("annotation config, no interface, aspect: ").append(this.annotationConfigNoInterface.doAspect()).append(System.lineSeparator());
        sb.append("annotation config, no interface, no aspect: ").append(this.annotationConfigNoInterface.doNoAspect()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("annotation config, with interface, aspect: ").append(this.annotationConfigWithInterface.doAspect()).append(System.lineSeparator());
        sb.append("annotation config, with interface, no aspect: ").append(this.annotationConfigWithInterface.doNoAspect()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("not a bean, no interface, aspect: ").append(this.notABean.doAspect()).append(System.lineSeparator());
        sb.append("not a bean, no interface, no aspect: ").append(this.notABean.doNoAspect()).append(System.lineSeparator());
        return sb.toString();
    }

}
