/*
 * @(#)AWTException.java	1.8 97/06/17
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
package java.awt;


/**
 * Signals that an Absract Window Toolkit exception has occurred.
 *
 * @version 	1.8 06/17/97
 * @author 	Arthur van Hoff
 */
public class AWTException extends Exception {
 
    /*
     * JDK 1.1 serialVersionUID 
     */
     private static final long serialVersionUID = -1900414231151323879L;

    /**
     * Constructs an instance of <code>AWTException</code> with the 
     * specified detail message. A detail message is an 
     * instance of <code>String</code> that describes this particular
     * exception. 
     * @param   msg     the detail message
     * @since   JDK1.0
     */
    public AWTException(String msg) {
	super(msg);
    }
}
