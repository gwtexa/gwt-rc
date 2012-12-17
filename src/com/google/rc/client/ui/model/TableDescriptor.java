package com.google.rc.client.ui.model;

import java.util.List;


/**
 * We need more flexible table description in runtime so we can't use generics
 * TableDescriptor determines the order of columns (by storing ordered list of TableColumnDescriptors)
 * 
 * Descriptors are actually part of the model describing UI
 *
 */
public class TableDescriptor {

	private List<TableColumnDescriptor> columns;

	public List<TableColumnDescriptor> getColumns() {
		return columns;
	}

	public void setColumns(List<TableColumnDescriptor> columns) {
		this.columns = columns;
	}
	
	//define moving columns, paging, use label and content provider for viewers, whether table is lazy, header, footer
	
	
	
}
