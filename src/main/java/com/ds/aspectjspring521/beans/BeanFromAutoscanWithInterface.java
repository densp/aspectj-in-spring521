/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521.beans;

import com.ds.aspectjspring521.DSAspectAnnotation;
import com.ds.aspectjspring521.DSAspectInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 17:54
 */
@Component
public class BeanFromAutoscanWithInterface implements DSAspectInterface {

    private final Util util;

    @Autowired
    public BeanFromAutoscanWithInterface(Util util) {
        this.util = util;
    }

    @Override
    @DSAspectAnnotation
    public String doAspect() {
        return util.doAction(this, "doAspect");
    }

    @Override
    public String doNoAspect() {
        return util.doAction(this, "doNoAspect");
    }

}
