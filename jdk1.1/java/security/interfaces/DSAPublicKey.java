/*
 * @(#)DSAPublicKey.java	1.5 97/01/15
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
 
package java.security.interfaces;

import java.math.BigInteger;

/**
 * The interface to a DSA public key. DSA (Digital Signature Algorithm) 
 * is defined in NIST's FIPS-186.
 *
 * @see java.security.Key
 * @see java.security.KeyParams
 * @see java.security.Signature
 * 
 * @version 1.5, 00/08/14
 * @author Benjamin Renaud 
 */
public interface DSAPublicKey extends DSAKey, java.security.PublicKey {

    /**
     * Returns the value of the public key, <code>y</code>.
     * 
     * @return the value of the public key, <code>y</code>. 
     */
    public BigInteger getY();
}


