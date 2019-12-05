/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521.beans;

import com.ds.aspectjspring521.DSAspectAnnotation;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 17:54
 */
public class NotABean {

    private Util util;

    public NotABean(Util util) {
        this.util = util;
    }

    @DSAspectAnnotation
    public String doAspect() {
        return util.doAction(this, "doAspect");
    }

    public String doNoAspect() {
        return util.doAction(this, "doNoAspect");
    }
    
}
