/*
 * @(#)ContentHandler.java	1.7 97/02/10
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

import java.io.IOException;

/**
 * The abstract class <code>ContentHandler</code> is the superclass 
 * of all classes that read an <code>Object</code> from a 
 * <code>URLConnection</code>. 
 * <p>
 * An application does not generally call the 
 * <code>getContent</code> method in this class directly. Instead, an 
 * application calls the <code>getContent</code> method in class 
 * <code>URL</code> or in <code>URLConnection</code>.
 * The application's content handler factory (an instance of a class that 
 * implements the interface <code>ContentHandlerFactory</code> set 
 * up by a call to <code>setContentHandler</code>) is 
 * called with a <code>String</code> giving the MIME type of the 
 * object being received on the socket. The factory returns an 
 * instance of a subclass of <code>ContentHandler</code>, and its 
 * <code>getContent</code> method is called to create the object. 
 *
 * @author  James Gosling
 * @version 1.7, 02/10/97
 * @see     java.net.ContentHandler#getContent(java.net.URLConnection)
 * @see     java.net.ContentHandlerFactory
 * @see     java.net.URL#getContent()
 * @see     java.net.URLConnection
 * @see     java.net.URLConnection#getContent()
 * @see     java.net.URLConnection#setContentHandlerFactory(java.net.ContentHandlerFactory)
 * @since   JDK1.0
 */
abstract public class ContentHandler {
    /** 
     * Given a URL connect stream positioned at the beginning of the 
     * representation of an object, this method reads that stream and 
     * creates an object from it. 
     *
     * @param      urlc   a URL connection.
     * @return     the object read by the <code>ContentHandler</code>.
     * @exception  IOException  if an I/O error occurs while reading the object.
     * @since      JDK1.0
     */
    abstract public Object getContent(URLConnection urlc) throws IOException;
}
