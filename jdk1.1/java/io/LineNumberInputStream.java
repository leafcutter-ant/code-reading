/*
 * @(#)LineNumberInputStream.java	1.12 97/01/24
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
 * This class is an input stream filter that provides the added 
 * functionality of keeping track of the current line number. 
 * <p>
 * A line is a sequence of bytes ending with a carriage return 
 * character (<code>'&#92;r'</code>), a newline character 
 * (<code>'&#92;n'</code>), or a carriage return character followed 
 * immediately by a linefeed character. In all three cases, the line 
 * terminating character(s) are returned as a single newline character.
 * <p>
 * The line number begins at <code>0</code>, and is incremented by 
 * <code>1</code> when a <code>read</code> returns a newline character.
 *
 * @author     Arthur van Hoff
 * @version    1.12, 01/24/97
 * @see        java.io.LineNumberReader
 * @since      JDK1.0
 * @deprecated This class incorrectly assumes that bytes adequately represent
 *             characters.  As of JDK&nbsp;1.1, the preferred way to operate on
 *             character streams is via the new character-stream classes, which
 *             include a class for counting line numbers.
 */
public
class LineNumberInputStream extends FilterInputStream {
    int pushBack = -1;
    int lineNumber;
    int markLineNumber;

    /**
     * Constructs a newline number input stream that reads its input 
     * from the specified input stream. 
     *
     * @param      in   the underlying input stream.
     * @since      JDK1.0
     */
    public LineNumberInputStream(InputStream in) {
	super(in);
    }

    /**
     * Reads the next byte of data from this input stream. The value 
     * byte is returned as an <code>int</code> in the range 
     * <code>0</code> to <code>255</code>. If no byte is available 
     * because the end of the stream has been reached, the value 
     * <code>-1</code> is returned. This method blocks until input data 
     * is available, the end of the stream is detected, or an exception 
     * is thrown. 
     * <p>
     * The <code>read</code> method of 
     * <code>LineNumberInputStream</code> calls the <code>read</code> 
     * method of the underlying input stream. It checks for carriage 
     * returns and newline characters in the input, and modifies the 
     * current line number as appropriate. A carriage-return character or 
     * a carriage return followed by a newline character are both 
     * converted into a single newline character. 
     *
     * @return     the next byte of data, or <code>-1</code> if the end of this
     *             stream is reached.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.LineNumberInputStream#getLineNumber()
     * @since      JDK1.0
     */
    public int read() throws IOException {
	int c = pushBack;

	if (c != -1) {
	    pushBack = -1;
	} else {
	    c = in.read();
	}

	switch (c) {
	  case '\r':
	    pushBack = in.read();
	    if (pushBack == '\n') {
		pushBack = -1;
	    }
	  case '\n':
	    lineNumber++;
	    return '\n';
	}
	return c;
    }

    /**
     * Reads up to <code>len</code> bytes of data from this input stream 
     * into an array of bytes. This method blocks until some input is available.
     * <p>
     * The <code>read</code> method of 
     * <code>LineNumberInputStream</code> repeatedly calls the 
     * <code>read</code> method of zero arguments to fill in the byte array.
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset of the data.
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             this stream has been reached.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.LineNumberInputStream#read()
     * @since      JDK1.0
     */
    public int read(byte b[], int off, int len) throws IOException {
	if (len <= 0) {
	    return 0;
	}

	int c = read();
	if (c == -1) {
	    return -1;
	}
	b[off] = (byte)c;

	int i = 1;
	try {
	    for (; i < len ; i++) {
		c = read();
		if (c == -1) {
		    break;
		}
		if (b != null) {
		    b[off + i] = (byte)c;
		}
	    }
	} catch (IOException ee) {
	}
	return i;
    }

    /**
     * Sets the line number to the specified argument. 
     *
     * @param      lineNumber   the new line number.
     * @since      JDK1.0
     */
    public void setLineNumber(int lineNumber) {
	this.lineNumber = lineNumber;
    }

    /**
     * Returns the current line number.
     *
     * @return     the current line number.
     * @since      JDK1.0
     */
    public int getLineNumber() {
	return lineNumber;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from the 
     * input stream. The <code>skip</code> method may, for a variety of 
     * reasons, end up skipping over some smaller number of bytes, 
     * possibly <code>0</code>. The actual number of bytes skipped is returned.
     * <p>
     * The <code>skip</code> method of 
     * <code>LineNumberInputStream</code> creates a byte array of length 
     * <code>n</code> and then reads into it until <code>n</code> bytes 
     * have been read or the end of the stream has been reached. 
     *
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if an I/O error occurs.
     * @since      JDK1.0
     */
    public long skip(long n) throws IOException {
	return read(new byte[(int)n]);
    }

    /**
     * Returns the number of bytes that can be read from this input 
     * stream without blocking. 
     * <p>
     * Note that if the underlying input stream is able to supply 
     * <i>k</i> input characters without blocking, the 
     * <code>LineNumberInputStream</code> can guarantee only to provide 
     * <i>k</i>/2 characters without blocking, because the 
     * <i>k</i> characters from the underlyhing input stream might 
     * consist of <i>k</i>/2 pairs of <code>'&#92;r'</code> and 
     * <code>'&#92;n'</code>, which are converted to just 
     * <i>k</i>/2 <code>'&#92;n'</code> characters. 
     *
     * @return     the number of bytes that can be read from this input stream
     *             without blocking.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @since      JDK1.0
     */
    public int available() throws IOException {
	return (pushBack == -1) ? super.available() : super.available() + 1;
    }

    /**
     * Marks the current position in this input stream. A subsequent 
     * call to the <code>reset</code> method repositions this stream at 
     * the last marked position so that subsequent reads re-read the same bytes.
     * <p>
     * The <code>mark</code> method of 
     * <code>LineNumberInputStream</code> remembers the current line 
     * number in a private variable, and then calls the <code>mark</code> 
     * method of the underlying input stream. 
     *
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.FilterInputStream#in
     * @see     java.io.LineNumberInputStream#reset()
     * @since   JDK1.0
     */
    public void mark(int readlimit) {
	markLineNumber = lineNumber;
	in.mark(readlimit);
    }

    /**
     * Repositions this stream to the position at the time the 
     * <code>mark</code> method was last called on this input stream. 
     * <p>
     * The <code>reset</code> method of 
     * <code>LineNumberInputStream</code> resets the line number to be 
     * the line number at the time the <code>mark</code> method was 
     * called, and then calls the <code>reset</code> method of the 
     * underlying input stream. 
     * <p>
     * Stream marks are intended to be used in
     * situations where you need to read ahead a little to see what's in
     * the stream. Often this is most easily done by invoking some
     * general parser. If the stream is of the type handled by the
     * parser, it just chugs along happily. If the stream is not of
     * that type, the parser should toss an exception when it fails,
     * which, if it happens within readlimit bytes, allows the outer
     * code to reset the stream and try another parser.
     *
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.LineNumberInputStream#mark(int)
     * @since      JDK1.0
     */
    public void reset() throws IOException {
	lineNumber = markLineNumber;
	in.reset();
    }
}
