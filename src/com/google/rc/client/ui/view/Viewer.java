package com.google.rc.client.ui.view;

abstract public class Viewer {

	private Object input;
	
	/**
	 * Interacting widgets trigger UiEv which are immediate and target widget should set this flag to true
	 * while DmEv is on the way through DmEvBus, ContentProvider and possibly round trip to the server.
	 * After DmEv arrives content provider updates the viewer and this flag may set back to false.
	 */
	private boolean isStaleData;
	
	private ContentProvider contentProvider;

	/**
	 * Sets the model object for the viewer. Normally it should be array or list
	 * 
	 * @param modelObject
	 */
	public void setInput(Object input) {
		Object oldInput = getInput();
		contentProvider.inputChanged(this, oldInput, input);
		this.input = input;

//		inputChanged(this.input, oldInput);
	}

	public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	public Object getInput() {
		return input;
	}
}
