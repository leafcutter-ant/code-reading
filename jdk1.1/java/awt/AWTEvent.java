/*
 * @(#)AWTEvent.java	1.21 98/01/09 Carl Quinn
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

package java.awt;

import java.util.EventObject;
import java.awt.event.*;

/**
 * The root event class for all AWT events.
 * This class and its subclasses supercede the original
 * java.awt.Event class.
 * Subclasses of this root AWTEvent class defined outside of the
 * java.awt.event package should define event ID values greater than
 * the value defined by RESERVED_ID_MAX.
 *
 * The event masks defined in this class are needed ONLY by
 * component subclasses which are using Component.enableEvents()
 * to select for event types not selected by registered listeners.
 * If a listener is registered on a component, the appropriate event
 * mask is already set internally by the component.
 * @see Component#enableEvents
 *
 * @see java.awt.event.ComponentEvent
 * @see java.awt.event.FocusEvent
 * @see java.awt.event.KeyEvent
 * @see java.awt.event.MouseEvent
 * @see java.awt.event.WindowEvent
 * @see java.awt.event.ActionEvent
 * @see java.awt.event.AdjustmentEvent
 * @see java.awt.event.ItemEvent
 * @see java.awt.event.TextEvent
 *
 * @version 1.21 01/09/98
 * @author Carl Quinn
 * @author Amy Fowler
 */
public abstract class AWTEvent extends EventObject {
    private transient int data;

    protected int id;

    // This field controls whether or not the event is sent back
    // down to the peer once the source has processed it -
    // false means it's sent to the peer, true means it's not.
    // Semantic events always have a 'true' value since they were
    // generated by the peer in response to a low-level event.
    protected boolean consumed = false;

    /**
     * The event mask for selecting component events.
     */
    public final static long COMPONENT_EVENT_MASK = 0x01;

    /**
     * The event mask for selecting container events.
     */
    public final static long CONTAINER_EVENT_MASK = 0x02;

    /**
     * The event mask for selecting focus events.
     */
    public final static long FOCUS_EVENT_MASK = 0x04;

    /**
     * The event mask for selecting key events.
     */
    public final static long KEY_EVENT_MASK = 0x08;

    /**
     * The event mask for selecting mouse events.
     */
    public final static long MOUSE_EVENT_MASK = 0x10;

    /**
     * The event mask for selecting mouse motion events.
     */
    public final static long MOUSE_MOTION_EVENT_MASK = 0x20;

    /**
     * The event mask for selecting window events.
     */
    public final static long WINDOW_EVENT_MASK = 0x40;

    /**
     * The event mask for selecting action events.
     */
    public final static long ACTION_EVENT_MASK = 0x80;

    /**
     * The event mask for selecting adjustment events.
     */
    public final static long ADJUSTMENT_EVENT_MASK = 0x100;

    /**
     * The event mask for selecting item events.
     */
    public final static long ITEM_EVENT_MASK = 0x200;

    /**
     * The event mask for selecting text events.
     */
    public final static long TEXT_EVENT_MASK = 0x400;

    /**
     * The maximum value for reserved AWT event IDs. Programs defining
     * their own event IDs should use IDs greater than this value.
     */
    public final static int RESERVED_ID_MAX = 1999;

    /*
     * JDK 1.1 serialVersionUID 
     */
    private static final long serialVersionUID = -1825314779160409405L;

    /**
     * Constructs an AWTEvent object from the parameters of a 1.0-style event.
     * @param event the old-style event
     */
    public AWTEvent(Event event) {
        this(event.target, event.id);
    }

    /**
     * Constructs an AWTEvent object with the specified source object and type.
     * @param source the object where the event originated
     * @id the event type
     */
    public AWTEvent(Object source, int id) {
        super(source);
	this.id = id;
        switch(id) {
          case ActionEvent.ACTION_PERFORMED:
          case ItemEvent.ITEM_STATE_CHANGED:
          case AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED:
          case TextEvent.TEXT_VALUE_CHANGED:
            consumed = true;
            break;
          default:
        }
    }

    /**
     * Returns the event type.
     */
    public int getID() {
        return id;
    }

    public String toString() {
        String srcName = null;
        if (source instanceof Component) {
            srcName = ((Component)source).getName();
        } else if (source instanceof MenuComponent) {
            srcName = ((MenuComponent)source).getName();
        }
	return getClass().getName() + "[" + paramString() + "] on " +
            (srcName != null? srcName : source);
    }

    public String paramString() {
        return "";
    }

    protected void consume() {
        switch(id) {
          case KeyEvent.KEY_PRESSED:
          case KeyEvent.KEY_RELEASED:
          case MouseEvent.MOUSE_PRESSED:
          case MouseEvent.MOUSE_RELEASED:
          case MouseEvent.MOUSE_MOVED:
          case MouseEvent.MOUSE_DRAGGED:
          case MouseEvent.MOUSE_ENTERED:
          case MouseEvent.MOUSE_EXITED:
              consumed = true;
              break;
          default:
              // event type cannot be consumed
        }
    }

    protected boolean isConsumed() {
        return consumed;
    }

    /* Converts a new event to an old one (used for compatibility).
     * If the new event cannot be converted (because no old equivelent
     * exists) then this returns null.
     *
     * Note: this method is here instead of in each individual new
     * event class in java.awt.event because we don't want to make
     * it public and it needs to be called from java.awt.
     */
    Event convertToOld() {
        Object src = getSource();
        int newid = id;

        switch(id) {
          case KeyEvent.KEY_PRESSED:
          case KeyEvent.KEY_RELEASED:
              KeyEvent ke = (KeyEvent)this;
              if (ke.isActionKey()) {
                  newid = (id == KeyEvent.KEY_PRESSED? 
                           Event.KEY_ACTION : Event.KEY_ACTION_RELEASE);
              }
              int keyCode = ke.getKeyCode();
              if (keyCode == KeyEvent.VK_SHIFT || 
                  keyCode == KeyEvent.VK_CONTROL || 
                  keyCode == KeyEvent.VK_ALT) {
                  return null;  // suppress modifier keys in old event model.
              }
              // no mask for button1 existed in old Event - strip it out
              return new Event(src, ke.getWhen(), newid, 0, 0,
                               Event.getOldEventKey(ke), 
                               (ke.getModifiers() & ~InputEvent.BUTTON1_MASK));

          case MouseEvent.MOUSE_PRESSED:
          case MouseEvent.MOUSE_RELEASED:
          case MouseEvent.MOUSE_MOVED:
          case MouseEvent.MOUSE_DRAGGED:
          case MouseEvent.MOUSE_ENTERED:
          case MouseEvent.MOUSE_EXITED:
              MouseEvent me = (MouseEvent)this;
              // no mask for button1 existed in old Event - strip it out
              Event olde = new Event(src, me.getWhen(), newid, 
                               me.getX(), me.getY(), 0, 
                               (me.getModifiers() & ~InputEvent.BUTTON1_MASK));
              olde.clickCount = me.getClickCount();
              return olde;

          case FocusEvent.FOCUS_GAINED:
              return new Event(src, Event.GOT_FOCUS, null);

          case FocusEvent.FOCUS_LOST:
              return new Event(src, Event.LOST_FOCUS, null);

          case WindowEvent.WINDOW_CLOSING:
          case WindowEvent.WINDOW_ICONIFIED:
          case WindowEvent.WINDOW_DEICONIFIED:
              return new Event(src, newid, null);

          case ComponentEvent.COMPONENT_MOVED:
              if (src instanceof Frame || src instanceof Dialog) {
                  Point p = ((Component)src).getLocation();
                  return new Event(src, 0, Event.WINDOW_MOVED, p.x, p.y, 0, 0);
              }
              break;

          case ActionEvent.ACTION_PERFORMED:
              ActionEvent ae = (ActionEvent)this;
              String cmd;
              if (src instanceof Button) { 
                  cmd = ((Button)src).getLabel();
              } else if (src instanceof MenuItem) {
                  cmd = ((MenuItem)src).getLabel();
              } else {
                  cmd = ae.getActionCommand();
              } 
              return new Event(src, 0, newid, 0, 0, 0, ae.getModifiers(), cmd);

          case ItemEvent.ITEM_STATE_CHANGED:
              ItemEvent ie = (ItemEvent)this;
              Object arg;
              if (src instanceof List) {
                  newid = (ie.getStateChange() == ItemEvent.SELECTED?
                           Event.LIST_SELECT : Event.LIST_DESELECT);
                  arg = ie.getItem();
              } else {
                  newid = Event.ACTION_EVENT;
                  
				//Netscape: CheckboxMenuItems use the name of the 
				//menu choice as the arg, NOT a Bool

				  if (src instanceof Choice || src instanceof CheckboxMenuItem) {
                      arg = ie.getItem();

                  } else { // Checkbox
                      arg = new Boolean(ie.getStateChange() == ItemEvent.SELECTED);
                  }
              }             
              return new Event(src, newid, arg);

          case AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED:
              AdjustmentEvent aje = (AdjustmentEvent)this;
              switch(aje.getAdjustmentType()) {
                case AdjustmentEvent.UNIT_INCREMENT:
                  newid = Event.SCROLL_LINE_DOWN;
                  break;
                case AdjustmentEvent.UNIT_DECREMENT:
                  newid = Event.SCROLL_LINE_UP;
                  break;
                case AdjustmentEvent.BLOCK_INCREMENT:
                  newid = Event.SCROLL_PAGE_DOWN;
                  break;
                case AdjustmentEvent.BLOCK_DECREMENT:
                  newid = Event.SCROLL_PAGE_UP;
                  break;
                case AdjustmentEvent.TRACK:
                  newid = Event.SCROLL_ABSOLUTE;
                  break;
                default:
                  return null;
              }
              return new Event(src, newid, new Integer(aje.getValue()));

          default:
        }
        return null;
    }                          

    /* Package-private method to change a KeyEvent's source to the new
     * focus owner.  This method needs to be here instead of in KeyEvent
     * because it should only be called from by the EventQueue.
     */
    void setSource(Object newSource) {
        if (!(this instanceof KeyEvent)) {
            throw new ClassCastException();
        }
        source = newSource;
    }
}
