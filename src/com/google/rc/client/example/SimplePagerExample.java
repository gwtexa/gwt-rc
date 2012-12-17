package com.google.rc.client.example;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

/**
 * Example of {@link SimplePager}.
 */
public class SimplePagerExample implements EntryPoint {

  public void onModuleLoad() {
    // Create a CellList.
    CellList<String> cellList = new CellList<String>(new TextCell());

    // Add a cellList to a data provider.
    ListDataProvider<String> dataProvider = new ListDataProvider<String>();
    List<String> data = dataProvider.getList();
    for (int i = 0; i < 200; i++) {
      data.add("Item " + i);
    }
    dataProvider.addDataDisplay(cellList);

    // Create a SimplePager.
    SimplePager pager = new SimplePager();

    // Set the cellList as the display.
    pager.setDisplay(cellList);

    // Add the pager and list to the page.
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.add(pager);
    vPanel.add(cellList);
    RootPanel.get().add(vPanel);
  }
}