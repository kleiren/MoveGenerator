package moveGenerator.events;

import java.util.EventObject;

/**
 * MyEvent object provides the necessary info when event occurs. <br>
 * instance the codes of the supported actions.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
@SuppressWarnings("serial")
public class MyEvent extends EventObject {

	public final static int POSITION = 0;
	public final static int MOVE_CHANGED = 1;
	public final static int MOVE_TIME = 2;
	public final static int TOTAL_TIME = 3;
	public final static int LOAD_MOVELIST = 4;
	public final static int ADD_MOVE = 5;
	public final static int DELETE_MOVE = 6;
	public final static int LOOP = 7;

	private int type;
	private int value;

	public MyEvent(int value, int type) {
		super(new Object());
		this.type = type;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public int getValue() {
		return (int) value;
	}

}
