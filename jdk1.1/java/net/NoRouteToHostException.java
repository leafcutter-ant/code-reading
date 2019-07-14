/*
 * @(#)NoRouteToHostException.java	1.5 97/01/25
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
 * 
 */

package java.net;

/**
 * Signals that an error occurred while attempting to connect a
 * socket to a remote address and port.  Typically, the remote
 * host cannot be reached because of an intervening firewall, or
 * if an intermediate router is down.
 *
 * @since   JDK1.1
 */
public class NoRouteToHostException extends SocketException {
    /**
     * Constructs a new NoRouteToHostException with the specified detail 
     * message as to why the remote host cannot be reached.
     * A detail message is a String that gives a specific 
     * description of this error.
     * @param msg the detail message
     * @since   JDK1.1
     */
    public NoRouteToHostException(String msg) {
	super(msg);
    }

    /**
     * Construct a new NoRouteToHostException with no detailed message.
     * @since   JDK1.1
     */
    public NoRouteToHostException() {}
}
