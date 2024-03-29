/*
 * @(#)BeanInfo.java	1.18 97/01/01  
 * 
 * Copyright (c) 1996 Sun Microsystems, Inc. All Rights Reserved.
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
 * CopyrightVersion bdk_beta
 * 
 */

package java.beans;

/**
 * A bean implementor who wishes to provide explicit information about
 * their bean may provide a BeanInfo class that implements this BeanInfo
 * interface and provides explicit information about the methods,
 * properties, events, etc, of their  bean.
 * <p>
 * A bean implementor doesn't need to provide a complete set of
 * explicit information.  You can pick and choose which information
 * you want to provide and the rest will be obtained by automatic
 * analysis using low-level reflection of the bean classes' methods
 * and applying standard design patterns.
 * <p>
 * You get the opportunity to provide lots and lots of different
 * information as part of the various XyZDescriptor classes.  But
 * don't panic, you only really need to provide the minimal core
 * information required by the various constructors.
 * <P>
 * See also the SimpleBeanInfo class which provides a convenient
 * "noop" base class for BeanInfo classes, which you can override
 * for those specific places where you want to return explicit info.
 * <P>
 * To learn about all the behaviour of a bean see the Introspector class.
 */

public interface BeanInfo {

    /**
     * @return  A BeanDescriptor providing overall information about
     * the bean, such as its displayName, its customizer, etc.  May
     * return null if the information should be obtained by automatic
     * analysis.
     */
    BeanDescriptor getBeanDescriptor();
    
    /**
     * @return  An array of EventSetDescriptors describing the kinds of 
     * events fired by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    EventSetDescriptor[] getEventSetDescriptors();

    /**
     * A bean may have a "default" event that is the event that will
     * mostly commonly be used by human's when using the bean. 
     * @return Index of default event in the EventSetDescriptor array
     *		returned by getEventSetDescriptors.
     * <P>	Returns -1 if there is no default event.
     */
    int getDefaultEventIndex();

    /**
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean.  May return null if the
     * information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will
     * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
     * A client of getPropertyDescriptors can use "instanceof" to check
     * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    PropertyDescriptor[] getPropertyDescriptors();

    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are 
     * customizing the bean.
     * @return  Index of default property in the PropertyDescriptor array
     * 		returned by getPropertyDescriptors.
     * <P>	Returns -1 if there is no default property.
     */
    int getDefaultPropertyIndex();

    /**
     * @return An array of MethodDescriptors describing the externally
     * visible methods supported by this bean.  May return null if
     * the information should be obtained by automatic analysis.
     */
    MethodDescriptor[] getMethodDescriptors();

    /**
     * This method allows a BeanInfo object to return an arbitrary collection
     * of other BeanInfo objects that provide additional information on the
     * current bean.
     * <P>
     * If there are conflicts or overlaps between the information provided
     * by different BeanInfo objects, then the current BeanInfo takes precedence
     * over the getAdditionalBeanInfo objects, and later elements in the array
     * take precedence over earlier ones.
     *
     * @return an array of BeanInfo objects.  May return null.
     */
    BeanInfo[] getAdditionalBeanInfo();

    /**
     * This method returns an image object that can be used to
     * represent the bean in toolboxes, toolbars, etc.   Icon images
     * will typically be GIFs, but may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from
     * this method.
     * <p>
     * There are four possible flavors of icons (16x16 color,
     * 32x32 color, 16x16 mono, 32x32 mono).  If a bean choses to only
     * support a single icon we recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background
     * so they can be rendered onto an existing background.
     *
     * @param  iconKind  The kind of icon requested.  This should be
     *    one of the constant values ICON_COLOR_16x16, ICON_COLOR_32x32, 
     *    ICON_MONO_16x16, or ICON_MONO_32x32.
     * @return  An image object representing the requested icon.  May
     *    return null if no suitable icon is available.
     */
    java.awt.Image getIcon(int iconKind);
     
    /**
     * Constant to indicate a 16 x 16 color icon.
     */
    final static int ICON_COLOR_16x16 = 1;

    /**
     * Constant to indicate a 32 x 32 color icon.
     */
    final static int ICON_COLOR_32x32 = 2;

    /**
     * Constant to indicate a 16 x 16 monochrome icon.
     */
    final static int ICON_MONO_16x16 = 3;

    /**
     * Constant to indicate a 32 x 32 monochrome icon.
     */
    final static int ICON_MONO_32x32 = 4;
}
