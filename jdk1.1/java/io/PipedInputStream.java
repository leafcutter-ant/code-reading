/*
 * @(#)PipedInputStream.java	1.18 97/02/20
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
 * A piped input stream is the receiving end of a communications 
 * pipe. Two threads can communicate by having one thread send data 
 * through a piped output stream and having the other thread read the 
 * data through a piped input stream.
 *
 * @author  James Gosling
 * @version 1.18, 02/20/97
 * @see     java.io.PipedOutputStream
 * @since   JDK1.0
 */
public
class PipedInputStream extends InputStream {
    boolean closed = true;
    boolean closedByReader = false;
    boolean connected = false;

	/* REMIND: identification of the read and write sides needs to be
	   more sophisticated.  Either using thread groups (but what about
	   pipes within a thread?) or using finalization (but it may be a
	   long time until the next GC). */
    Thread readSide;
    Thread writeSide;

    /**
     * @since   JDK1.1
     */
    protected static final int PIPE_SIZE = 1024;

    /**
     * The circular buffer into which incoming data is placed.
     * @since   JDK1.1
     */
    protected byte buffer[] = new byte[PIPE_SIZE];

    /*
     * fill and empty pointers.  in<0 implies the buffer is empty, in==out
     * implies the buffer is full
     */

    /**
     * @since   JDK1.1
     */
    protected int in = -1;

    /**
     * @since   JDK1.1
     */
    protected int out = 0;

    /**
     * Creates a piped input stream connected to the specified piped 
     * output stream. 
     *
     * @param      src   the stream to connect to.
     * @exception  IOException  if an I/O error occurs.
     * @since      JDK1.0
     */
    public PipedInputStream(PipedOutputStream src) throws IOException {
	connect(src);
    }

    /**
     * Creates a piped input stream that is not yet connected to a piped 
     * output stream. It must be connected to a piped output stream, 
     * either by the receiver or the sender, before being used. 
     *
     * @see     java.io.PipedInputStream#connect(java.io.PipedOutputStream)
     * @see     java.io.PipedOutputStream#connect(java.io.PipedInputStream)
     * @since   JDK1.0
     */
    public PipedInputStream() {
    }

    /**
     * Connects this piped input stream to a sender. 
     *
     * @param      src   The piped output stream to connect to.
     * @exception  IOException  if an I/O error occurs.
     * @since      JDK1.0
     */
    public void connect(PipedOutputStream src) throws IOException {
	if (connected) {
	    throw new IOException("Pipe already connected");
	}
	src.connect(this);
	connected = true;
    }
    
    /**
     * Receives a byte of data.  This method will block if no input is
     * available.
     * @param b the byte being received
     * @exception IOException If the pipe is broken.
     * @since     JDK1.1
     */
    protected synchronized void receive(int b) throws IOException {
	writeSide = Thread.currentThread();
	while (in == out) {
	    if ((readSide != null) && !readSide.isAlive()) {
		throw new IOException("Pipe broken");
	    }
	    /* full: kick any waiting readers */
	    notifyAll();	
	    try {
	        wait(1000);
	    } catch (InterruptedException ex) {
		throw new java.io.InterruptedIOException();
	    }
	}
	if (in < 0) {
	    in = 0;
	    out = 0;
	}
	buffer[in++] = (byte)(b & 0xFF);
	if (in >= buffer.length) {
	    in = 0;
	}
    }

    /**
     * Receives data into an array of bytes.  This method will
     * block until some input is available. 
     * @param b the buffer into which the data is received
     * @param off the start offset of the data
     * @param len the maximum number of bytes received
     * @return the actual number of bytes received, -1 is
     *          returned when the end of the stream is reached. 
     * @exception IOException If an I/O error has occurred. 
     */
    synchronized void receive(byte b[], int off, int len)  throws IOException {
	while (--len >= 0) {
	    receive(b[off++]);
	}
    }

    /**
     * Notifies all waiting threads that the last byte of data has been
     * received.
     */
    synchronized void receivedLast() {
	closed = true;
	notifyAll();
    }

    /**
     * Reads the next byte of data from this piped input stream. The 
     * value byte is returned as an <code>int</code> in the range 
     * <code>0</code> to <code>255</code>. If no byte is available 
     * because this end of the stream has been reached, the value 
     * <code>-1</code> is returned. This method blocks until input data 
     * is available, the end of the stream is detected, or an exception 
     * is thrown. 
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if the pipe is broken.
     * @since      JDK1.0
     */
    public synchronized int read()  throws IOException {
	if (closedByReader) {
	    throw new IOException("InputStream closed");
	}
	int trials = 2;
	while (in < 0) {
	    readSide = Thread.currentThread();
	    if (closed) { 
		/* closed by writer, return EOF */
		return -1;
	    }
	    if ((writeSide != null) && (!writeSide.isAlive()) && (--trials < 0)) {
		throw new IOException("Pipe broken");
	    }
            /* might be a writer waiting */
	    notifyAll();
	    try {
	        wait(1000);
	    } catch (InterruptedException ex) {
		throw new java.io.InterruptedIOException();
	    }
 	}
	int ret = buffer[out++] & 0xFF;
	if (out >= buffer.length) {
	    out = 0;
	}
	if (in == out) {
            /* now empty */
	    in = -1;		
	}
	return ret;
    }

    /**
     * Reads up to <code>len</code> bytes of data from this piped input 
     * stream into an array of bytes. This method blocks until at least one
     * byte of input is available. 
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset of the data.
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  if an I/O error occurs.
     * @since      JDK1.0
     */
    public synchronized int read(byte b[], int off, int len)  throws IOException {

	if (b == null) {
	    throw new NullPointerException();
	} else if (off < 0 || len < 0 || off + len > b.length) {
	    throw new ArrayIndexOutOfBoundsException();
	} else if (len == 0) {
	    return 0;
	}

        /* possibly wait on the first character */
	int c = read();		
	if (c < 0) {
	    return -1;
	}
	b[off] = (byte) c;
	int rlen = 1;
	while ((in >= 0) && (--len > 0)) {
	    b[off + rlen] = buffer[out++];
	    rlen++;
	    if (out >= buffer.length) {
		out = 0;
	    }
	    if (in == out) {
                /* now empty */
		in = -1;	
	    }
	}
	return rlen;
    }

    /**
     * Returns the number of bytes that can be read from this input 
     * stream without blocking. This method overrides the <code>available</code>
     * method of the parent class.
     *
     * @return     the number of bytes that can be read from this input stream
     *             without blocking.
     * @exception  IOException  if an I/O error occurs.
     * @since   JDK1.0.2
     */
  public synchronized int available() throws IOException {
    if(in < 0)
      return 0;
    else if(in == out)
      return buffer.length;
    else if (in > out)
      return in - out;
    else
      return in + buffer.length - out;
  }
    
    /**
     * Closes this piped input stream and releases any system resources 
     * associated with the stream. 
     *
     * @exception  IOException  if an I/O error occurs.
     * @since      JDK1.0
     */
    public void close()  throws IOException {
	in = -1;
	closedByReader = true;
    }
}
