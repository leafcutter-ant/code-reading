/*
 * @(#)Skeleton.java	1.5 97/02/11
 * 
 * Copyright (c) 1995, 1996 Sun Microsystems, Inc. All Rights Reserved.
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
 * CopyrightVersion 1.1_beta
 */
package java.rmi.server;

import java.rmi.Remote;

/** Used solely by the implementation.<p>
 * Every skeleton class generated by the rmic stub compiler implements
 * this interface. A skeleton for a remote object is a server-side entity
 * that dispatches calls to the actual remote object implementation.
 */
public interface Skeleton {
    /**
     * Unmarshals arguments, calls the actual remote object implementation,
     * and marshals the return value or any exception.
     *
     * @exception java.lang.Exception if a general exception occurs.
     */
    void dispatch(Remote obj, RemoteCall theCall, int opnum, long hash)
	throws Exception;

    Operation[] getOperations();
}
