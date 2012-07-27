package gui;

import javax.swing.event.EventListenerList;

public class GuiEventSource {
	private EventListenerList _listeners = new EventListenerList();
	
	public synchronized void addEventListener(GuiEventListener listener)	{
		_listeners.add(GuiEventListener.class, listener);
	}
	public synchronized void removeEventListener(GuiEventListener listener)	{
		_listeners.remove(GuiEventListener.class, listener);
	}

	// call this method whenever you want to notify
	//the event listeners of the particular event
	private synchronized void fireEvent(GuiEvent evt)	{
		Object[] listeners = _listeners.getListenerList();

		// Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==GuiEventListener.class) {
                ((GuiEventListener)listeners[i+1]).handleGuiEvent(evt);
            }
        }
	}

	public void stateChanged(GuiState state)
	{
		fireEvent(new GuiEvent(state));
	}
}
