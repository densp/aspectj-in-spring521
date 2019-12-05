/*
 * Copyright (c) 2000-2019 Short Consulting AG. All Rights Reserved.
 */
package com.ds.aspectjspring521;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: Denis Spiridonov
 * Date: 04.12.2019
 * Time: 17:45
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface DSAspectAnnotation {
}
