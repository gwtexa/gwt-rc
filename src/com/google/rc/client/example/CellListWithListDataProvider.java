package com.google.rc.client.example;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CellListWithListDataProvider implements EntryPoint {
  // The list of data to display.
  private static final List<String> DAYS = Arrays.asList("Sunday", "Monday",
      "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

  public void onModuleLoad() {
    // Create a cell to render each value in the list.
    TextCell textCell = new TextCell();

    // Create a CellList that uses the cell.
    CellList<String> cellList = new CellList<String>(textCell);

    // Set the range to display. In this case, our visible range is smaller than
    // the data set.
    cellList.setVisibleRange(1, 3);

    // Create a data provider.
    ListDataProvider<String> dataProvider = new ListDataProvider<String>();
    
    // Connect the list to the data provider.
    dataProvider.addDataDisplay(cellList);
    
    // Add the data to the data provider, which automatically pushes it to the
    // widget. Our data provider will have seven values, but it will only push
    // the four that are in range to the list.
    List<String> list = dataProvider.getList();
    for (String day : DAYS) {
      list.add(day);
    }

    // Add it to the root panel.
    RootPanel.get().add(cellList);
  }
}