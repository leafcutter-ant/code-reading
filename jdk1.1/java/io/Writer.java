/*
 * @(#)Writer.java	1.8 97/01/27
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

package java.io;


/**
 * Abstract class for writing to character streams.  The only methods that a
 * subclass must implement are write(char[], int, int), flush(), and close().
 * Most subclasses, however, will override some of the methods defined here in
 * order to provide higher efficiency, additional functionality, or both.
 *
 * @see Writer
 * @see   BufferedWriter
 * @see   CharArrayWriter
 * @see   FilterWriter
 * @see   OutputStreamWriter
 * @see     FileWriter
 * @see   PipedWriter
 * @see   PrintWriter
 * @see   StringWriter
 * @see Reader
 *
 * @version 	1.8, 97/01/27
 * @author	Mark Reinhold
 * @since	JDK1.1
 */

public abstract class Writer {

    /**
     * The object used to synchronize operations on this stream.  For
     * efficiency, a character-stream object may use an object other than
     * itself to protect critical sections.  A subclass should therefore use
     * the object in this field rather than <tt>this</tt> or a synchronized
     * method.
     */
    protected Object lock;

    /**
     * Create a new character-stream writer whose critical sections will
     * synchronize on the writer itself.
     */
    protected Writer() {
	this.lock = this;
    }

    /**
     * Create a new character-stream writer whose critical sections will
     * synchronize on the given object.
     */
    protected Writer(Object lock) {
	this.lock = lock;
    }

    /**
     * Write a single character.  The character to be written is contained in
     * the 16 low-order bits of the given integer value; the 16 high-order bits
     * are ignored.
     *
     * <p> Subclasses that intend to support efficient single-character output
     * should override this method.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(int c) throws IOException {
	synchronized (lock) {
	    char cb[] = new char[1];
	    cb[0] = (char) c;
	    write(cb, 0, 1);
	}
    }

    /**
     * Write an array of characters.
     *
     * @param  cbuf  Array of characters to be written
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[]) throws IOException {
	write(cbuf, 0, cbuf.length);
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param  cbuf  Array of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     */
    abstract public void write(char cbuf[], int off, int len) throws IOException;

    /**
     * Write a string.
     *
     * @param  str  String to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(String str) throws IOException {
	write(str, 0, str.length());
    }

    /**
     * Write a portion of a string.
     *
     * @param  str  A String
     * @param  off  Offset from which to start writing characters
     * @param  len  Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(String str, int off, int len) throws IOException {
	synchronized (lock) {
	    char cbuf[] = new char[len];
	    str.getChars(off, len, cbuf, 0);
	    write(cbuf, 0, len);
	}
    }

    /**
     * Flush the stream.  If the stream has saved any characters from the
     * various write() methods in a buffer, write them immediately to their
     * intended destination.  Then, if that destination is another character or
     * byte stream, flush it.  Thus one flush() invocation will flush all the
     * buffers in a chain of Writers and OutputStreams.
     *
     * @exception  IOException  If an I/O error occurs
     */
    abstract public void flush() throws IOException;

    /**
     * Close the stream, flushing it first.  Once a stream has been closed,
     * further write() or flush() invocations will cause an IOException to be
     * thrown.  Closing a previously-closed stream, however, has no effect.
     *
     * @exception  IOException  If an I/O error occurs
     */
    abstract public void close() throws IOException;

}
