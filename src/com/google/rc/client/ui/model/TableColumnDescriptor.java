package com.google.rc.client.ui.model;

import com.google.gwt.cell.client.Cell;



/**
 * TableColumnDescriptor defines the type of data, formatting, presentation rules (e.g. color depending on cell value)
 * Type of data is not known at compile time so we don't use generics as it is normally used in GWT.
 * 
 * TableColumnDescriptor has no information about order column and the column is identified by columnId
 * for interaction with TableContentProvider 
 * 
 * Descriptors are actually part of the model describing UI
 * 
 */
public class TableColumnDescriptor {

	/**
	 * If not set it defaults to columnName
	 */
	private String columnId;
	
	private String columnName;
	
	/**
	 * We can't derive cellType (like DataCell) from dataType because for one dataType (like String) 
	 * we may have many representations (TextCell, EditTextCell) 
	 */
	private Class<? extends Cell<?>> cellType; //Should extend Cell
	private Class<?> dataType;
	private LabelFormatter labelFormatter;
	
	public TableColumnDescriptor(String columnName, Class<? extends Cell<?>> cellType, Class<?> dataType) {
		super();
		this.columnId = columnName;
		this.columnName = columnName;
		this.cellType = cellType;
		this.dataType = dataType;
	}

	public String getColumnId() {
		return columnId;
	}

	public Class<? extends Cell<?>> getCellType() {
		return cellType;
	}

	public Class<?> getDataType() {
		return dataType;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	
	public LabelFormatter getLabelFormatter() {
		return labelFormatter;
	}
	
	public void setLabelFormatter(LabelFormatter labelFormatter) {
		this.labelFormatter = labelFormatter;
	}
}
