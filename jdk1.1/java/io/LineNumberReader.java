/*
 * @(#)LineNumberReader.java	1.5 97/01/27
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
 * A buffered character-input stream that keeps track of line numbers.  A line
 * is considered to be terminated by any one of a line feed ('\n'), a carriage
 * return ('\r'), or a carriage return followed immediately by a linefeed.
 *
 * @version 	1.5, 97/01/27
 * @author	Mark Reinhold
 * @since	JDK1.1
 */

public class LineNumberReader extends BufferedReader {

    /** The current line number */
    private int lineNumber = 0;

    /** The line number of the mark, if any */
    private int markedLineNumber;

    /** If the next character is a line feed, skip it */
    private boolean skipLF;

    /**
     * Create a new line-numbering reader, using the default input-buffer
     * size.
     */
    public LineNumberReader(Reader in) {
	super(in);
    }

    /**
     * Create a new line-numbering reader, reading characters into a buffer of
     * the given size.
     */
    public LineNumberReader(Reader in, int sz) {
	super(in, sz);
    }

    /**
     * Set the current line number.
     */
    public void setLineNumber(int lineNumber) {
	this.lineNumber = lineNumber;
    }

    /**
     * Get the current line number.
     */
    public int getLineNumber() {
	return lineNumber;
    }

    /**
     * Read a single character.  Line terminators are compressed into single
     * newline ('\n') characters.
     *
     * @return     The character read, or -1 if the end of the stream has been
     *             reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
	synchronized (lock) {
	    int c = super.read();
	    if (skipLF) {
		if (c == '\n')
		    c = super.read();
		skipLF = false;
	    }
	    switch (c) {
	    case '\r':
		skipLF = true;
	    case '\n':		/* Fall through */
		lineNumber++;
		return '\n';
	    }
	    return c;
	}
    }

    /**
     * Read characters into a portion of an array.
     *
     * @param      cbuf  Destination buffer
     * @param      off   Offset at which to start storing characters
     * @param      len   Maximum number of characters to read
     *
     * @return     The number of bytes read, or -1 if the end of the stream has
     *             already been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char cbuf[], int off, int len) throws IOException {
	synchronized (lock) {
	    int n = super.read(cbuf, off, len);

	    for (int i = off; i < off + len; i++) {
		int c = cbuf[i];
		if (skipLF) {
		    skipLF = false;
		    if (c == '\n')
			continue;
		}
		switch (c) {
		case '\r':
		    skipLF = true;
		case '\n':	/* Fall through */
		    lineNumber++;
		    break;
		}
	    }

	    return n;
	}
    }

    /**
     * Read a line of text.  A line is considered to be terminated by any one
     * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
     * followed immediately by a linefeed.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public String readLine() throws IOException {
	synchronized (lock) {
	    String l = super.readLine();
	    if (l != null)
		lineNumber++;
	    skipLF = false;
	    return l;
	}
    }

    /** Maximum skip-buffer size */
    private static final int maxSkipBufferSize = 8192;

    /** Skip buffer, null until allocated */
    private char skipBuffer[] = null;

    /**
     * Skip characters.
     *
     * @param  n  The number of characters to skip
     *
     * @return    The number of characters actually skipped
     *
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
	int nn = (int) Math.min(n, maxSkipBufferSize);
	synchronized (lock) {
	    if ((skipBuffer == null) || (skipBuffer.length < nn))
		skipBuffer = new char[nn];
	    long r = n;
	    while (r > 0) {
		int nc = read(skipBuffer, 0, nn);
		if (nc == -1)
		    break;
		r -= nc;
	    }
	    return n - r;
	}
    }

    /**
     * Mark the present position in the stream.  Subsequent calls to reset()
     * will attempt to reposition the stream to this point, and will also reset
     * the line number appropriately.
     *
     * @param  readAheadLimit  Limit on the number of characters that may be
     *                         read while still preserving the mark.  After
     *                         reading this many characters, attempting to
     *                         reset the stream may fail.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
	synchronized (lock) {
	    super.mark(readAheadLimit);
	    markedLineNumber = lineNumber;
	}
    }

    /**
     * Reset the stream to the most recent mark.
     *
     * @exception  IOException  If the stream has not been marked,
     *                          or if the mark has been invalidated
     */
    public void reset() throws IOException {
	synchronized (lock) {
	    super.reset();
	    lineNumber = markedLineNumber;
	}
    }

}
