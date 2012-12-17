package com.google.rc.client.ui.view;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.rc.client.ui.model.TableColumnDescriptor;
import com.google.rc.client.ui.model.TableDescriptor;


public class TableFactory {

	public static CellTable<TableRowContentProvider> createCellTable(TableDescriptor tableDescr) {
		CellTable<TableRowContentProvider> table = new CellTable<TableRowContentProvider>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		for (TableColumnDescriptor colDescr: tableDescr.getColumns()) {
			//TableColumnFactory.createColumn(colDescr)
		}
		
		
		//table.setRowData(values)
		return table;
		
//	    DateCell dateCell = new DateCell();
//	    Column<Contact, Date> dateColumn = new Column<Contact, Date>(dateCell) {
//	      @Override
//	      public Date getValue(Contact object) {
//	        return object.birthday;
//	      }
//	    };
//	    table.addColumn(dateColumn, "Birthday");
//		
//		return table;
	}

}
