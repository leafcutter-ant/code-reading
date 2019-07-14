/*
 * @(#)IllegalMonitorStateException.java	1.4 97/01/20
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

package java.lang;

/**
 * Thrown to indicate that a thread has attempted to wait on an 
 * object's monitor or to notify other threads waiting on an object's
 * monitor without owning the specified monitor. 
 *
 * @author  unascribed
 * @version 1.4, 01/20/97
 * @see     java.lang.Object#notify()
 * @see     java.lang.Object#notifyAll()
 * @see     java.lang.Object#wait() 
 * @see     java.lang.Object#wait(long) 
 * @see     java.lang.Object#wait(long, int) 
 * @since   JDK1.0
 */
public
class IllegalMonitorStateException extends RuntimeException {
    /**
     * Constructs an <code>IllegalMonitorStateException</code> with no 
     * detail message. 
     *
     * @since   JDK1.0
     */
    public IllegalMonitorStateException() {
	super();
    }

    /**
     * Constructs an <code>IllegalMonitorStateException</code> with the 
     * specified detail message. 
     *
     * @param   s   the detail message.
     * @since   JDK1.0
     */
    public IllegalMonitorStateException(String s) {
	super(s);
    }
}
