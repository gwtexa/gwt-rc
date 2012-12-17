package com.google.rc.client.ui.view;

abstract public class TableContentProvider {

	private Object modelObject;
	
	public TableContentProvider(Object modelObject) {
		this.modelObject = modelObject;
	}
	
	public TableRowContentProvider[] getRows() {
		return getRows(0, getSize());
	}
	
	abstract public TableRowContentProvider[] getRows(int from, int to);
	
	abstract public int getSize();
	
}
