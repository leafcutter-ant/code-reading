/*
 * @(#)RemoteCall.java	1.6 97/02/11
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

package java.rmi.server;
import java.rmi.*;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;
import java.io.IOException;

/**
 * RemoteCall is an abstraction used solely by the implementation
 * (stubs and skeletons of remote objects) to carry out a call to a
 * remote object.
 */
public interface RemoteCall {

    /**
     * Return the output stream the stub/skeleton should put arguments/results
     * into.
     *
     * @exception java.io.IOException if an I/O error occurs.
     */
    ObjectOutput getOutputStream()  throws IOException;
    
    /**
     * Release the output stream; in some transports this would release
     * the stream.
     *
     * @exception java.io.IOException if an I/O error occurs.
     */
    void releaseOutputStream()  throws IOException;

    /**
     * Get the InputStream that the stub/skeleton should get
     * results/arguments from.
     *
     * @exception java.io.IOException if an I/O error occurs.
     */
    ObjectInput getInputStream()  throws IOException;

    
    /**
     * Release the input stream. This would allow some transports to release
     * the channel early.
     *
     * @exception java.io.IOException if an I/O error occurs.
     */
    void releaseInputStream() throws IOException;

    /**
     * Returns an output stream (may put out header information
     * relating to the success of the call). Should only succeed
     * once per remote call.
     *
     * @param success If true, indicates normal return, else indicates
     * exceptional return.
     * @exception java.io.IOException              if an I/O error occurs.
     * @exception java.io.StreamCorruptedException If already been called.
     */
    ObjectOutput getResultStream(boolean success) throws IOException,
	StreamCorruptedException;
    
    /**
     * Do whatever it takes to execute the call.
     *
     * @exception java.lang.Exception if a general exception occurs.
     */
    void executeCall() throws Exception;

    /**
     * Allow cleanup after the remote call has completed.
     *
     * @exception java.io.IOException if an I/O error occurs.
     */
    void done() throws IOException;
}
