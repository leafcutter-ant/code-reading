/*
 * @(#)ContainerEvent.java	1.3 97/01/27 Amy Fowler
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
 * CopyrightVersion 1.1_beta
 * 
 */

package java.awt.event;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Component;

/**
 * The class for container-level events.
 * These events are provided for notification purposes ONLY;
 * The AWT will automatically handle container add and remove
 * operations internally.
 *
 * @see ContainerListener
 *
 * @version 1.3 01/27/97
 * @author Tim Prinzing
 * @author Amy Fowler
 */
public class ContainerEvent extends ComponentEvent {

    /**
     * Marks the first integer id for the range of container event ids.
     */
    public static final int CONTAINER_FIRST		= 300;

    /**
     * Marks the last integer id for the range of container event ids.
     */
    public static final int CONTAINER_LAST		= 301;

   /**
     * The component moved event type.
     */
    public static final int COMPONENT_ADDED	= CONTAINER_FIRST;

    /**
     * The component resized event type.
     */
    public static final int COMPONENT_REMOVED = 1 + CONTAINER_FIRST;

    Component child;

    /*
     * JDK 1.1 serialVersionUID 
     */
    private static final long serialVersionUID = -4114942250539772041L;

    /**
     * Constructs a ContainerEvent object with the specified source
     * container, type, and child which is being added or removed. 
     * @param source the container where the event originated
     * @id the event type
     * @child the child component
     */
    public ContainerEvent(Component source, int id, Component child) {
        super(source, id);
        this.child = child;
    }

    /**
     * Returns the container where this event originated.
     */
    public Container getContainer() {
        return (Container)source; // cast should always be OK, type was checked in constructor
    }

    /**
     * Returns the child component that was added or removed in
     * this event.
     */
    public Component getChild() {
        return child;
    }

    public String paramString() {
        String typeStr;
        switch(id) {
          case COMPONENT_ADDED:
              typeStr = "COMPONENT_ADDED";
              break;
          case COMPONENT_REMOVED:
              typeStr = "COMPONENT_REMOVED";
              break;
          default:
              typeStr = "unknown type";
        }
        return typeStr + ",child="+child.getName();
    }
}
