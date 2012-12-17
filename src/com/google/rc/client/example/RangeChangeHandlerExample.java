package com.google.rc.client.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;

/**
 * Example of using a {@link RangeChangeEvent.Handler} to push data into a
 * {@link CellList} when the range changes.
 */
public class RangeChangeHandlerExample implements EntryPoint {

  @Override
  public void onModuleLoad() {
    // Create a CellList.
    final CellList cellList = new CellList(new TextCell());

    // Add a range change handler.
    cellList.addRangeChangeHandler(new RangeChangeEvent.Handler() {
      @Override
      public void onRangeChange(RangeChangeEvent event) {
        Range range = event.getNewRange();
        int start = range.getStart();
        int length = range.getLength();

        // Create the data to push into the view. At this point, you could send
        // an asynchronous RPC request to a server.
        List data = new ArrayList();
        for (int i = start; i < start + length; i++) {
          data.add("Item " + i);
        }

        // Push the data into the list.
        cellList.setRowData(start, data);
      }
    });

    // Force the cellList to fire an initial range change event.
    cellList.setVisibleRangeAndClearData(new Range(0, 25), true);

    // Create paging controls.
    SimplePager pager = new SimplePager();
    pager.setDisplay(cellList);

    // Add the widgets to the root panel.
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.add(pager);
    vPanel.add(cellList);
    RootPanel.get().add(vPanel);
  }
}