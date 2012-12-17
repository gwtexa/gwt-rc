package com.google.rc.client.ui.view;



/**
 * Mediator between viewer and viewer model. Provides the binding between widget and model.
 */
public abstract class ContentProvider {
	
	/**
	 * Returns the elements to display in the viewer 
     * when its input is set to the given element. 
     * These elements can be presented as rows in a table, items in a list, etc.
     * The result is not modified by the viewer. 
	 */
	abstract public Object[] getElements(Object modelObject);
	
    /**
     * Notifies this content provider that the given viewer's input has been switched to a different element.
     * <p>
     * A typical use for this method is registering the content provider as a listener
     * to changes on the new input (using model-specific means), and deregistering the viewer 
     * from the old input. In response to these change notifications, the content provider
     * should update the viewer (see the add, remove, update and refresh methods on the viewers).
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    	
    }
}
