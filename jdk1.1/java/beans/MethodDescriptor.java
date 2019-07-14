/*
 * @(#)MethodDescriptor.java	1.16 96/12/06  
 * 
 * Copyright (c) 1996 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 * 
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 * 
 * CopyrightVersion bdk_beta
 * 
 */

package java.beans;

import java.lang.reflect.*;

/**
 * A MethodDescriptor describes a particular method that a Java Bean
 * supports for external access from other components.
 */

public class MethodDescriptor extends FeatureDescriptor {

    /**
     * @param method    The low-level method information.
     */
    public MethodDescriptor(Method method) {
	this.method = method;
	setName(method.getName());
    }


    /**
     * @param method    The low-level method information.
     * @param parameterDescriptors  Descriptive information for each of the
     *		 		method's parameters.
     */
    public MethodDescriptor(Method method, 
		ParameterDescriptor parameterDescriptors[]) {
	this.method = method;
	this.parameterDescriptors = parameterDescriptors;
	setName(method.getName());
    }

    /**
     * @return The low-level description of the method
     */
    public Method getMethod() {
	return method;
    }


    /**
     * @return The locale-independent names of the parameters.  May return
     *		a null array if the parameter names aren't known.
     */
    public ParameterDescriptor[] getParameterDescriptors() {
	return parameterDescriptors;
    }

    /*
     * Package-private constructor
     * Merge two method descriptors.  Where they conflict, give the
     * second argument (y) priority over the first argument (x).
     * @param x  The first (lower priority) MethodDescriptor
     * @param y  The second (higher priority) MethodDescriptor
     */

    MethodDescriptor(MethodDescriptor x, MethodDescriptor y) {
	super(x,y);
	method = x.method;
	parameterDescriptors = x.parameterDescriptors;
	if (y.parameterDescriptors != null) {
	    parameterDescriptors = y.parameterDescriptors;
	}
    }

    private Method method;
    private ParameterDescriptor parameterDescriptors[];
}
