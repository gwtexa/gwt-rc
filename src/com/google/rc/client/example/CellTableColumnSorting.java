package com.google.rc.client.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CellTableColumnSorting implements EntryPoint {

  // A simple data type that represents a contact.
  private static class Contact {
    private final String address;
    private final String name;

    public Contact(String name, String address) {
      this.name = name;
      this.address = address;
    }
  }

  // The list of data to display.
  private static List<Contact> CONTACTS = Arrays.asList(new Contact("John",
      "123 Fourth Road"), new Contact("Mary", "222 Lancer Lane"), new Contact(
      "Zander", "94 Road Street"));

  public void onModuleLoad() {

    // Create a CellTable.
    final CellTable<Contact> table = new CellTable<Contact>();

    // Create name column.
    TextColumn<Contact> nameColumn = new TextColumn<Contact>() {
      @Override
      public String getValue(Contact contact) {
        return contact.name;
      }
    };

    // Make the name column sortable.
    nameColumn.setSortable(true);

    // Create address column.
    TextColumn<Contact> addressColumn = new TextColumn<Contact>() {
      @Override
      public String getValue(Contact contact) {
        return contact.address;
      }
    };

    // Add the columns.
    table.addColumn(nameColumn, "Name");
    table.addColumn(addressColumn, "Address");

    // Set the total row count. You might send an RPC request to determine the
    // total row count.
    table.setRowCount(CONTACTS.size(), true);

    // Set the range to display. In this case, our visible range is smaller than
    // the data set.
    table.setVisibleRange(0, 3);

    // Create a data provider.
    AsyncDataProvider<Contact> dataProvider = new AsyncDataProvider<Contact>() {
      @Override
      protected void onRangeChanged(HasData<Contact> display) {
        final Range range = display.getVisibleRange();

        // Get the ColumnSortInfo from the table.
        final ColumnSortList sortList = table.getColumnSortList();

        // This timer is here to illustrate the asynchronous nature of this data
        // provider. In practice, you would use an asynchronous RPC call to
        // request data in the specified range.
        new Timer() {
          @Override
          public void run() {
            int start = range.getStart();
            int end = start + range.getLength();
            // This sorting code is here so the example works. In practice, you
            // would sort on the server.
            Collections.sort(CONTACTS, new Comparator<Contact>() {
              public int compare(Contact o1, Contact o2) {
                if (o1 == o2) {
                  return 0;
                }

                // Compare the name columns.
                int diff = -1;
                if (o1 != null) {
                  diff = (o2 != null) ? o1.name.compareTo(o2.name) : 1;
                }
                return sortList.get(0).isAscending() ? diff : -diff;
              }
            });
            List<Contact> dataInRange = CONTACTS.subList(start, end);

            // Push the data back into the list.
            table.setRowData(start, dataInRange);
          }
        }.schedule(2000);
      }
    };

    // Connect the list to the data provider.
    dataProvider.addDataDisplay(table);

    // Add a ColumnSortEvent.AsyncHandler to connect sorting to the
    // AsyncDataPRrovider.
    AsyncHandler columnSortHandler = new AsyncHandler(table);
    table.addColumnSortHandler(columnSortHandler);

    // We know that the data is sorted alphabetically by default.
    table.getColumnSortList().push(nameColumn);

    // Add it to the root panel.
    RootPanel.get().add(table);
  }
}