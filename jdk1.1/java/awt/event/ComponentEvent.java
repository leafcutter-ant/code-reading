/*
 * @(#)ComponentEvent.java	1.14 97/01/27 Carl Quinn
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

package java.awt.event;

import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.Component;
import java.awt.Rectangle;

/**
 * The root event class for all component-level events.
 * These events are provided for notification purposes ONLY;
 * The AWT will automatically handle component moves and resizes
 * internally so that GUI layout works properly regardless of
 * whether a program is receiving these events or not.
 *
 * @see ComponentListener
 *
 * @version 1.14 01/27/97
 * @author Carl Quinn
 */
public class ComponentEvent extends AWTEvent {

    /**
     * Marks the first integer id for the range of component event ids.
     */
    public static final int COMPONENT_FIRST		= 100;

    /**
     * Marks the last integer id for the range of component event ids.
     */
    public static final int COMPONENT_LAST		= 103;

   /**
     * The component moved event type.
     */
    public static final int COMPONENT_MOVED	= COMPONENT_FIRST;

    /**
     * The component resized event type.
     */
    public static final int COMPONENT_RESIZED	= 1 + COMPONENT_FIRST;

    /**
     * The component shown event type.
     */
    public static final int COMPONENT_SHOWN	= 2 + COMPONENT_FIRST;

    /**
     * The component hidden event type.
     */
    public static final int COMPONENT_HIDDEN	= 3 + COMPONENT_FIRST;

    /*
     * JDK 1.1 serialVersionUID 
     */
    private static final long serialVersionUID = 8101406823902992965L;

    /**
     * Constructs a ComponentEvent object with the specified source component 
     * and type.
     * @param source the component where the event originated
     * @id the event type
     */
    public ComponentEvent(Component source, int id) {
        super(source, id);
    }

    /**
     * Returns the component where this event originated.
     */
    public Component getComponent() {
//        return (source instanceof Component) ? (Component)source : null;
        return (Component)source; // cast should always be OK, type was checked in constructor
    }

    public String paramString() {
        String typeStr;
        Rectangle b = (source !=null
		       ? ((Component)source).getBounds()
		       : null);

        switch(id) {
          case COMPONENT_SHOWN:
              typeStr = "COMPONENT_SHOWN";
              break;
          case COMPONENT_HIDDEN:
              typeStr = "COMPONENT_HIDDEN";
              break;
          case COMPONENT_MOVED:
              typeStr = "COMPONENT_MOVED ("+ 
                         b.x+","+b.y+" "+b.width+"x"+b.height+")";
              break;
          case COMPONENT_RESIZED:
              typeStr = "COMPONENT_RESIZED ("+ 
                         b.x+","+b.y+" "+b.width+"x"+b.height+")";
              break;
          default:
              typeStr = "unknown type";
        }
        return typeStr;
    }
}
