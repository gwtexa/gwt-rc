package com.google.rc.client.example;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CellListWithAsyncDataProvider implements EntryPoint {
  // The list of data to display.
  private static final List<String> DAYS = Arrays.asList("Sunday", "Monday",
      "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

  public void onModuleLoad() {
    // Create a cell to render each value in the list.
    TextCell textCell = new TextCell();

    // Create a CellList that uses the cell.
    final CellList<String> cellList = new CellList<String>(textCell);

    // Set the total row count. You might send an RPC request to determine the
    // total row count.
    cellList.setRowCount(DAYS.size(), true);

    // Set the range to display. In this case, our visible range is smaller than
    // the data set.
    cellList.setVisibleRange(1, 3);

    // Create a data provider.
    AsyncDataProvider<String> dataProvider = new AsyncDataProvider<String>() {
      @Override
      protected void onRangeChanged(HasData<String> display) {
        final Range range = display.getVisibleRange();

        // This timer is here to illustrate the asynchronous nature of this data
        // provider. In practice, you would use an asynchronous RPC call to
        // request data in the specified range.
        new Timer() {
          @Override
          public void run() {
            int start = range.getStart();
            int end = start + range.getLength();
            List<String> dataInRange = DAYS.subList(start, end);

            // Push the data back into the list.
            cellList.setRowData(start, dataInRange);
          }
        }.schedule(2000);
      }
    };

    // Connect the list to the data provider.
    dataProvider.addDataDisplay(cellList);

    // Add it to the root panel.
    RootPanel.get().add(cellList);
  }
}