package moveGenerator.events;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Coordinates the generated events and notifies them to all concerned.<br>
 * This class is a singleton because the interest should be added as such.
 * Furthermore they must implementing this class.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MyEventStation {
	private static MyEventStation instance = null;

	private MyEventStation() {

	}

	public static MyEventStation getInstance() {
		if (instance == null) {
			instance = new MyEventStation();
		}
		return instance;
	}

	private transient Vector<GuiChangesListener> change_Listeners;

	/** Register a listener for MyEvent */
	synchronized public void addGuiChangesListener(GuiChangesListener l) {
		if (change_Listeners == null)
			change_Listeners = new Vector<GuiChangesListener>();
		change_Listeners.addElement(l);
	}

	/** Remove a listener for MyEvent */
	synchronized public void removeGuiChangesListener(GuiChangesListener l) {
		if (change_Listeners == null)
			change_Listeners = new Vector<GuiChangesListener>();
		change_Listeners.removeElement(l);
	}

	/** Fire a SunEvent to all registered listeners */
	@SuppressWarnings("unchecked")
	public void updateView(int value, int type) {
		// if we have no listeners, do nothing...
		if (change_Listeners != null && !change_Listeners.isEmpty()) {
			// create the event object to send
			MyEvent event = new MyEvent(value, type);

			// make a copy of the listener list in case
			// anyone adds/removes listeners
			Vector<GuiChangesListener> targets;
			synchronized (this) {
				targets = (Vector<GuiChangesListener>) change_Listeners.clone();
			}

			// walk through the listener list and
			// call the MyEvent method in each
			Enumeration<GuiChangesListener> e = targets.elements();
			while (e.hasMoreElements()) {
				GuiChangesListener l = (GuiChangesListener) e.nextElement();
				l.updateView(event);
			}
		}
	}
}
