/*
 * @(#)Number.java	1.20 97/01/20
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

package java.lang;

/**
 * The abstract class <code>Number</code> is the superclass of 
 * classes <code>Byte</code>, <code>Double</code>, <code>Float</code>,
 * <code>Integer</code>, <code>Long</code>, and <code>Short</code>.
 * <p>
 * Subclasses of <code>Number</code> must provide methods to convert 
 * the represented numeric value to <code>byte</code>, <code>double</code>,
 * <code>float</code>, <code>int</code>, <code>long</code>, and
 * <code>short</code>.
 *
 * @author	Lee Boynton
 * @author	Arthur van Hoff
 * @version 1.20, 01/20/97
 * @see     java.lang.Byte
 * @see     java.lang.Double
 * @see     java.lang.Float
 * @see     java.lang.Integer
 * @see     java.lang.Long
 * @see     java.lang.Short
 * @since   JDK1.0
 */
public abstract class Number implements java.io.Serializable {
    /**
     * Returns the value of the specified number as an <code>int</code>.
     * This may involve rounding.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>int</code>.
     * @since   JDK1.0
     */
    public abstract int intValue();

    /**
     * Returns the value of the specified number as a <code>long</code>.
     * This may involve rounding.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>long</code>.
     * @since   JDK1.0
     */
    public abstract long longValue();

    /**
     * Returns the value of the specified number as a <code>float</code>.
     * This may involve rounding.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>float</code>.
     * @since   JDK1.0
     */
    public abstract float floatValue();

    /**
     * Returns the value of the specified number as a <code>double</code>.
     * This may involve rounding.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>double</code>.
     * @since   JDK1.0
     */
    public abstract double doubleValue();

    /**
     * Returns the value of the specified number as a <code>byte</code>.
     * This may involve rounding or truncation.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>byte</code>.
     * @since   JDK1.1
     */
    public byte byteValue() {
	return (byte)intValue();
    }

    /**
     * Returns the value of the specified number as a <code>short</code>.
     * This may involve rounding or truncation.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>short</code>.
     * @since   JDK1.1
     */
    public short shortValue() {
	return (short)intValue();
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -8742448824652078965L;
}
