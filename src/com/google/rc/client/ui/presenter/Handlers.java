package com.google.rc.client.ui.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class Handlers {

	private final EventBus bus; //alternatively HasEvents
	
	private final ClickH clickH;
	private final BoolValueH boolValueH;
	
	public Handlers(EventBus bus) {
		this.bus = bus;
		//this.clickH = new ClickH(bus);
		this.clickH = new ClickH();
		this.boolValueH = new BoolValueH();
		
		//bus.addHandler(type, handler)
	}
	
	
	
	private class ClickH implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			bus.fireEvent(event);
		}
	}
	
	private class BoolValueH implements ValueChangeHandler<Boolean> {
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			bus.fireEvent(event);
		}
	}
	
	/**
	 * Add primary handler for the widget depending on type of widget
	 * @param w
	 */
	public void add(Widget w) {
		
		if (w instanceof Button) {
			Button b = (Button) w;
			
			HandlerRegistration hr = b.addClickHandler(clickH);
		} else if (w instanceof CheckBox) {
			CheckBox c = (CheckBox) w;
			c.addValueChangeHandler(boolValueH);
		}
		
		
	}
	
	
	
	
}
