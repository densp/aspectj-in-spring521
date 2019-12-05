/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 18:05
 */
public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    String doAction(Object source, String method) {
        String s = String.format("%s -> %s worked", source.getClass().getSimpleName(), method);
        logger.info(s);
        return s;
    }

}
