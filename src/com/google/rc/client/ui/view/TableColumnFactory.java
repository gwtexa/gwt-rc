package com.google.rc.client.ui.view;

import java.util.Date;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;
import com.google.rc.client.ui.model.TableColumnDescriptor;

/**
 * Create GWT Column from TableContentProvider and TableColumnDescriptor
 * In this way we separate concerns and decouple
 */
public class TableColumnFactory {

//	public static <C, R> Column<TableContentProvider, C> createColumn(Class<? extends Cell<C>> c) throws IllegalAccessException, InstantiationException {
//		Cell r = c.newInstance();
//	    Column<TableContentProvider, C> col = new Column<TableContentProvider, C>(r) {
//	      @Override
//	      public C getValue(TableContentProvider contentProvider) {
//	        return contentProvider.get
//	      }
//	    };
//	    return col;
//	}
	
	public static Column<TableRowContentProvider, Object> create(final TableColumnDescriptor descr, TableRowContentProvider provider) throws IllegalAccessException, InstantiationException {
		Class<?> dataType = descr.getDataType();
		Class<? extends Cell<?>> cellType = descr.getCellType();
		Cell cellObj = cellType.newInstance();
		
	    Column<TableRowContentProvider, Object> col = new Column<TableRowContentProvider, Object>(cellObj) {
	      @Override
	      public Object getValue(TableRowContentProvider contentProvider) {
	        return contentProvider.get(descr.getColumnId());
	      }
	    };
	    return col;
	}
	
	
	
}
