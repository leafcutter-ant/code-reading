/*
 * @(#)StringSelection.java	1.4 97/02/12
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

package java.awt.datatransfer;

import java.io.*;

/**
 * A class which implements the capability required to transfer a
 * simple java String in plain text format.
 */
public class StringSelection implements Transferable, ClipboardOwner {

    final static int STRING = 0;
    final static int PLAIN_TEXT = 1;

    DataFlavor flavors[] = {DataFlavor.stringFlavor, DataFlavor.plainTextFlavor};

    private String data;
						   
    /**
     * Creates a transferable object capable of transferring the
     * specified string in plain text format.
     */
    public StringSelection(String data) {
        this.data = data;
    }

    /**
     * Returns the array of flavors in which it can provide the data.
     */
    public synchronized DataFlavor[] getTransferDataFlavors() {
	return flavors;
    }

    /**
     * Returns whether the requested flavor is supported by this object.
     * @param flavor the requested flavor for the data
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
	return (flavor.equals(flavors[STRING]) || flavor.equals(flavors[PLAIN_TEXT]));
    }

    /**
     * If the data was requested in the "java.lang.String" flavor, return the
     * String representing the selection.
     *
     * @param flavor the requested flavor for the data
     * @exception UnsupportedFlavorException if the requested data flavor is
     *              not supported in the "<code>java.lang.String</code>" flavor.
     */
    public synchronized Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException {
	if (flavor.equals(flavors[STRING])) {
	    return (Object)data;
	} else if (flavor.equals(flavors[PLAIN_TEXT])) {
	    return new StringReader(data);
	} else {
	    throw new UnsupportedFlavorException(flavor);
	}
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }
}
	
