package gui;

import java.util.EventListener;
import java.util.EventObject;

public interface GuiEventListener extends EventListener {
	public void handleGuiEvent(EventObject e);
}
