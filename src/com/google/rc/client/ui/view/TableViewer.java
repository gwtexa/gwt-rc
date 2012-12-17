package com.google.rc.client.ui.view;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.rc.client.ui.model.TableDescriptor;

/**
 * Table viewer should not store the full model object (like array of elems)
 *  
 *
 */
public class TableViewer extends Viewer {

	private TableDescriptor tableDescr;
	
	public TableViewer(TableDescriptor tableDescr) {
		this.tableDescr = tableDescr;
		//TODO Create CellTable according to the descriptor
		CellTable<TableRowContentProvider> table = TableFactory.createCellTable(tableDescr);
	}
	
}
