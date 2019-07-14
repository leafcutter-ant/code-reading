/*
 * @(#)UnicastRemoteObject.java	1.10 96/12/16
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

/**
 * The UnicastRemoteObject class defines a non-replicated remote
 * object whose references are valid only while the server process is
 * alive.  The UnicastRemoteObject class provides support for
 * point-to-point active object references (invocations, parameters,
 * and results) using TCP streams.  <p>
 *
 * Objects that require remote behavior should extend RemoteObject,
 * typically via UnicastRemoteObject. If UnicastRemoteObject is not
 * extended, the implementation class must then assume the
 * responsibility for the correct semantics of the hashCode, equals,
 * and toString methods inherited from the Object class, so that they
 * behave appropriately for remote objects.
 */
public class UnicastRemoteObject extends RemoteServer {


    /**
     * Create and export a new UnicastRemoteObject object using an
     * anonymous port.
     */
    protected UnicastRemoteObject() throws RemoteException
    {
	exportObject((Remote)this);
    }

    /**
     * Re-export the remote object when it is deserialized.
     */
    private void readObject(java.io.ObjectInputStream in) 
	throws java.io.IOException, java.lang.ClassNotFoundException
    {
	exportObject((Remote)this);
    }
    
    /**
     * Returns a clone of the remote object that is distinct from
     * the original.
     *
     * @exception CloneNotSupportedException if clone failed due to
     * a RemoteException.
     * @return the new remote object
     */
    public Object clone() throws CloneNotSupportedException
    {
	try {
	    UnicastRemoteObject remote = (UnicastRemoteObject)super.clone();
	    exportObject(remote);
	    return remote;
	} catch (RemoteException e) {
	    throw new ServerCloneException("Clone failed", e);
	}
    }

    /** 
     * Export the remote object to make it available to receive incoming calls.
     * @param obj the remote object to be exported
     * @exception RemoteException if export fails
     */
    public static RemoteStub exportObject(Remote obj)
	throws RemoteException
    {
	/* Server ref must be created and assigned before remote object 
	 * can be exported.
	 */
	try {
	    Class refClass = Class.forName(RemoteRef.packagePrefix +
					   ".UnicastServerRef");
	    Object refObj = refClass.newInstance();
	    if (refObj instanceof ServerRef) {
		ServerRef serverRef = (ServerRef)refObj;
		if (obj instanceof UnicastRemoteObject)
		    ((UnicastRemoteObject)obj).ref = serverRef;
		return serverRef.exportObject(obj, null);
	    } else {
		throw new ExportException("Reference is not a java.rmi.server.ServerRef");
	    }
	} catch (Exception e) {
	    throw new ExportException("Unable to create remote reference", e);
	}
    }
}
