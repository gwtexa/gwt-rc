package com.google.rc.client.ui.view;



/**
 * Provides data to display in a single row.
 * Data is requested per columnId
 */
public interface TableRowContentProvider {
	public Object get(String columnId);
}
