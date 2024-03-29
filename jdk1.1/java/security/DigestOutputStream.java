/*
 * @(#)DigestOutputStream.java	1.19 97/01/30
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

package java.security;

import java.io.IOException;
import java.io.EOFException;
import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

/**
 * A transparent stream that updates the associated message digest using 
 * the bits going through the stream.
 *
 * <p>To complete the message digest computation, call one of the 
 * <code>digest</code> methods on the associated message 
 * digest after your calls to one of this digest ouput stream's <a href = 
 * "#write(int)">write</a> methods.
 *      
 * <p>It is possible to turn this stream on or off (see <a href =
 * "#on">on</a>). When it is on, a call to <code>write</code> results in
 * an update on the message digest.  But when it is off, the message 
 * digest is not updated. The default is for the stream to be on.
 *
 * @see MessageDigest
 * @see DigestInputStream
 *
 * @version 1.19 00/08/14
 * @author Benjamin Renaud */

public class DigestOutputStream extends FilterOutputStream {
  
    private boolean on = true;

    /**  
     * The message digest associated with this stream.  
     */  
    protected MessageDigest digest;

    /**
     * Creates a digest output stream, using the specified output stream
     * and message digest.
     *
     * @param stream the output stream.
     *
     * @param digest the message digest to associate with this stream.
     */
    public DigestOutputStream(OutputStream stream, MessageDigest digest) {
	super(stream);
	setMessageDigest(digest);
    }

    /**
     * Returns the message digest associated with this stream.
     *
     * @return the message digest associated with this stream.
     */
    public MessageDigest getMessageDigest() {
	return digest;
    }    

    /**
     * Associates the specified message digest with this stream.
     *
     * @param digest the message digest to be associated with this stream.  
     */
    public void setMessageDigest(MessageDigest digest) {
	this.digest = digest;
    }

    /**
     * Updates the message digest (if the digest function is on) using   
     * the specified byte, and in any case writes the byte      
     * to the output stream. That is, if the digest function is on
     * (see <a href = "#on">on</a>), this method calls
     * <code>update</code> on the message digest associated with this
     * stream, passing it the byte <code>b</code>. This method then
     * writes the byte to the output stream, blocking until the byte 
     * is actually written.
     *
     * @param b the byte to be used for updating and writing to the    
     * output stream.    
     *
     * @exception IOException if an I/O error occurs.
     * 
     * @see MessageDigest#update(byte) 
     */
    public void write(int b) throws IOException {
	if (on) {
	    digest.update((byte)b);
	}
	out.write(b);
    }

    /**
     * Updates the message digest (if the digest function is on) using
     * the specified subarray, and in any case writes the subarray to
     * the output stream. That is, if the digest function is on (see
     * <a href = "#on">on</a>), this method calls <code>update</code>
     * on the message digest associated with this stream, passing it
     * the subarray specifications. This method then writes the subarray 
     * bytes to the output stream, blocking until the bytes are actually
     * written.
     *
     * @param b the array containing the subarray to be used for updating 
     * and writing to the output stream.
     *
     * @param off the offset into <code>b</code> of the first byte to 
     * be updated and written.
     *
     * @param len the number of bytes of data to be updated and written 
     * from <code>b</code>, starting at offset <code>off</code>.
     *
     * @exception IOException if an I/O error occurs.
     * 
     * @see MessageDigest#update(byte[], int, int)
     */
    public void write(byte[] b, int off, int len) throws IOException {
	if (on) {
	    digest.update(b, off, len);
	}
	out.write(b, off, len);
    }

    /**
     * Turns the digest function on or off. The default is on.  When
     * it is on, a call to <a href = "#write">write</a> results in an
     * update on the message digest.  But when it is off, the message
     * digest is not updated.
     *    
     * @param on true to turn the digest function on, false to turn it
     * off. 
     */
    public void on(boolean on) {
	this.on = on;
    }
	
    /**
     * Prints a string representation of this digest output stream and
     * its associated message digest object.  
     */
     public String toString() {
	 return "[Digest Output Stream] " + digest.toString();
     }
}	


  

