package pl.marcinchwedczuk.javafx.validation.demo.controls;

import javafx.event.Event;
import javafx.event.EventType;

public class LayoutEvent extends Event {
    public static final EventType<LayoutEvent> LAYOUT_EVENT_TYPE = new EventType<>(Event.ANY, "LAYOUT");

    public LayoutEvent() {
        super(LAYOUT_EVENT_TYPE);
    }
}
