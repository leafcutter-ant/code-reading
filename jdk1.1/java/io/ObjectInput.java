/*
 * @(#)ObjectInput.java	1.8 97/01/22
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

package java.io;

/**
 * ObjectInput extends the DataInput interface to include the writing of
 * objects. DataInput includes methods for the input of primitive types, 
 * ObjectInput extends that interface to include objects, arrays, and Strings.
 *
 * @author  unascribed
 * @version 1.8, 01/22/97
 * @see java.io.InputStream
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @since   JDK1.1
 */
public interface ObjectInput extends DataInput {
    /**
     * Read and return an object. The class that implements this interface
     * defines where the object is "read" from.
     *
     * @exception java.lang.ClassNotFoundException If the class of a serialized 
     *      object cannot be found.
     * @exception IOException If any of the usual Input/Output
     * related exceptions occur.
     * @since     JDK1.1
     */
    public Object readObject()
	throws ClassNotFoundException, IOException;

    /**
     * Reads a byte of data. This method will block if no input is 
     * available.
     * @return 	the byte read, or -1 if the end of the
     *		stream is reached.
     * @exception IOException If an I/O error has occurred.
     * @since     JDK1.1
     */
    public int read() throws IOException;

    /**
     * Reads into an array of bytes.  This method will
     * block until some input is available.
     * @param b	the buffer into which the data is read
     * @return  the actual number of bytes read, -1 is
     * 		returned when the end of the stream is reached.
     * @exception IOException If an I/O error has occurred.
     * @since     JDK1.1
     */
    public int read(byte b[]) throws IOException;

    /**
     * Reads into an array of bytes.  This method will
     * block until some input is available.
     * @param b	the buffer into which the data is read
     * @param off the start offset of the data
     * @param len the maximum number of bytes read
     * @return  the actual number of bytes read, -1 is
     * 		returned when the end of the stream is reached.
     * @exception IOException If an I/O error has occurred.
     * @since     JDK1.1
     */
    public int read(byte b[], int off, int len) throws IOException;

    /**
     * Skips n bytes of input.
     * @param n the number of bytes to be skipped
     * @return	the actual number of bytes skipped.
     * @exception IOException If an I/O error has occurred.
     * @since     JDK1.1
     */
    public long skip(long n) throws IOException;

    /**
     * Returns the number of bytes that can be read
     * without blocking.
     * @return the number of available bytes.
     * @since     JDK1.1
     */
    public int available() throws IOException;

    /**
     * Closes the input stream. Must be called
     * to release any resources associated with
     * the stream.
     * @exception IOException If an I/O error has occurred.
     * @since     JDK1.1
     */
    public void close() throws IOException;
}
