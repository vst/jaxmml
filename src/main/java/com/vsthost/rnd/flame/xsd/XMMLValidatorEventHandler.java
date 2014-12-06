package com.vsthost.rnd.flame.xsd;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import java.util.regex.Pattern;

/**
 * Handles the validation events.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class XMMLValidatorEventHandler implements ValidationEventHandler {
    /**
     * Handles the validation event.
     *
     * @param event The Validation Event.
     * @return boolean indicating if we should stop or not.
     */
    public boolean handleEvent(ValidationEvent event) {
        // Is it a problem of a missing message name? It can very well be in another file.
        // Show as a warning and skip:
        Pattern inputRefPattern = Pattern.compile("Key 'inputMessageRef' with value '[a-zA-Z0-9_-]+' not found for identity constraint of element 'xmodel'.");
        Pattern outputRefPattern = Pattern.compile("Key 'outputMessageRef' with value '[a-zA-Z0-9_-]+' not found for identity constraint of element 'xmodel'.");
        if (inputRefPattern.matcher(event.getMessage()).matches() || outputRefPattern.matcher(event.getMessage()).matches()) {
            System.err.println("WARNING (but skip, it may be defined in other XMML): " + event.getMessage());
            return true;
        }

        // Sever error. Print message and tell to stop validation:
        System.err.println("\nEVENT");
        System.err.println("SEVERITY: " + event.getSeverity());
        System.err.println("MESSAGE: " + event.getMessage());
        System.err.println("LINKED EXCEPTION: " + event.getLinkedException());
        System.err.println("LOCATOR");
        System.err.println(" LINE NUMBER: " + event.getLocator().getLineNumber());
        System.err.println(" COLUMN NUMBER: " + event.getLocator().getColumnNumber());
        System.err.println(" OFFSET: " + event.getLocator().getOffset());
        System.err.println(" OBJECT: " + event.getLocator().getObject());
        System.err.println(" NODE: " + event.getLocator().getNode());
        System.err.println(" URL: " + event.getLocator().getURL());
        return false;
    }
}
