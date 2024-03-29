/*
 * @(#)AclEntry.java	1.8 97/01/30
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

package java.security.acl;

import java.util.Enumeration;
import java.security.Principal;

/**
 * This is the interface used for representing one entry in an Access 
 * Control List (ACL).<p>
 * 
 * An ACL can be thought of as a data structure with multiple ACL entry
 * objects. Each ACL entry object contains a set of permissions associated 
 * with a particular principal. (A principal represents an entity such as 
 * an individual user or a group). Additionally, each ACL entry is specified
 * as being either positive or negative. If positive, the permissions are 
 * to be granted to the associated principal. If negative, the permissions 
 * are to be denied. Each principal can have at most one positive ACL entry 
 * and one negative entry; that is, multiple positive or negative ACL 
 * entries are not allowed for any principal.
 *
 * Note: ACL entries are by default positive. An entry becomes a 
 * negative entry only if the <a href = 
 * "#setNegativePermissions">setNegativePermissions</a>
 * method is called on it.
 * 
 * @see java.security.acl.Acl
 *
 * @author 	Satish Dharmaraj
 */
public interface AclEntry extends Cloneable {

    /**
     * Specifies the principal for which permissions are granted or denied 
     * by this ACL entry. If a principal was already set for this ACL entry,
     * false is returned, otherwise true is returned.
     * 
     * @param user the principal to be set for this entry.
     * 
     * @return true if the principal is set, false if there was 
     * already a principal set for this entry.
     */
    public boolean setPrincipal(Principal user);

    /**
     * Returns the principal for which permissions are granted or denied by 
     * this ACL entry. Returns null if there is no principal set for this 
     * entry yet. 
     * 
     * @return the principal associated with this entry.
     */
    public Principal getPrincipal();

    /**
     * Sets this ACL entry to be a negative one. That is, the associated 
     * principal (e.g., a user or a group) will be denied the permission set 
     * specified in the entry.
     * 
     * Note: ACL entries are by default positive. An entry becomes a 
     * negative entry only if this <code>setNegativePermissions</code> 
     * method is called on it.
     */
    public void setNegativePermissions();

    /**
     * Returns true if this is a negative ACL entry (one denying the 
     * associated principal the set of permissions in the entry), false 
     * otherwise.
     * 
     * @return true if this is a negative ACL entry, false if it's not.
     */
    public boolean isNegative();

    /**
     * Adds the specified permission to this ACL entry. Note: An entry can 
     * have multiple permissions. 
     * 
     * @param permission the permission to be associated with 
     * the principal in this entry.
     * 
     * @return true if the permission was added, false if the 
     * permission was already part of this entry's permission set.
     */
    public boolean addPermission(Permission permission);

    /**
     * Removes the specified permission from this ACL entry.
     *  
     * @param permission the permission to be removed from this entry.
     * 
     * @return true if the permission is removed, false if the 
     * permission was not part of this entry's permission set.
     */
    public boolean removePermission(Permission permission);

    /**
     * Checks if the specified permission is part of the 
     * permission set in this entry.
     * 
     * @param permission the permission to be checked for.
     * 
     * @return true if the permission is part of the        
     * permission set in this entry, false otherwise. 
     */
    public boolean checkPermission(Permission permission);

    /**
     * Returns an enumeration of the permissions in this ACL entry.
     * 
     * @return an enumeration of the permissions in this ACL entry.
     */
    public Enumeration permissions();

    /**
     * Returns a string representation of the contents of this ACL entry.
     * 
     * @return a string representation of the contents.
     */
    public String toString();

    /**
     * Clones this ACL entry.
     * 
     * @return a clone of this ACL entry.
     */
    public Object clone();
}


