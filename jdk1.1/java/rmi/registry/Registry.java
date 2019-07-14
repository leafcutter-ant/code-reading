/*
 * @(#)Registry.java	1.5 97/02/12
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
package java.rmi.registry;

import java.rmi.*;

/**
 * A "registry" exists on every node that allows RMI connections to
 * servers on that node.  The registry on a particular node contains a
 * transient database that maps names to remote objects.  When the
 * node boots, the registry database is empty.  The names stored in the
 * registry are pure and are not parsed.  A service storing itself in
 * the registry may want to prefix its name of the service by a package
 * name (although not required), to reduce name collisions in the
 * registry.
 *
 * The LocateRegistry class is used to obtain the registry for different hosts.
 *
 * @see LocateRegistry
 */
public interface Registry extends Remote {
    /** Well known port for registry */
    public static final int REGISTRY_PORT = 1099;
    
    /**
     * Returns the remote object associated with the specified name in the 
     * registry.
     *
     * @exception RemoteException If remote operation failed.
     * @exception NotBoundException if there is no object with this name in the
     *              registry.
     * @exception AccessException If this operation is not permitted.
     */
    public Remote lookup(String name)
	throws RemoteException, NotBoundException, AccessException;

    /**
     * Binds the name to the specified remote object.
     * @exception RemoteException If remote operation failed.
     * @exception AlreadyBoundException If name is already bound.
     * @exception AccessException If this operation is not permitted.
     */
    public void bind(String name, Remote obj)
	throws RemoteException, AlreadyBoundException, AccessException;
    
    /**
     * Unbind the name.
     * @exception RemoteException If remote operation failed.
     * @exception NotBoundException if there is no object with this name in the
     *              registry.
     * @exception AccessException If this operation is not permitted.
     */
    public void unbind(String name)
	throws RemoteException, NotBoundException, AccessException;


    /** 
     * Rebind the name to a new object, replacing any existing binding.
     * @exception RemoteException If remote operation failed.
     * @exception AccessException If this operation is not permitted.
     */
    public void rebind(String name, Remote obj)
	throws RemoteException, AccessException;

    /**
     * Returns an array of the names in the registry.
     * @exception RemoteException If remote operation failed.
     * @exception AccessException If this operation is not permitted.
     */
    public String[] list()
	throws RemoteException, AccessException;
}
