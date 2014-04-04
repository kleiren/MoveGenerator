package moveGenerator.events;

import java.util.EventListener;

/**
 * GuiChangesListener interface
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public interface GuiChangesListener extends EventListener {

	public void updateView(MyEvent e);
}
