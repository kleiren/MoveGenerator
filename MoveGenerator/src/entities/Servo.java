package entities;

import manager.Config;
/** Servo object able to increase and decrease positions
*
* @author Rubén Vilches
* @version 1.0 - Mar 2014.
*/
public class Servo {

	private static int MAX_POS = Config.getInstance().getServo_MaxPosition();
	private static int MIN_POS = Config.getInstance().getServo_MinPostition();

	private int ID;
	private int position;

	public Servo(int ID) {
		this.ID = ID;
		position = 0;
	}

	public Servo(int ID, int position) {
		this.ID = ID;
		this.position = position;
	}

	public int getID() {
		return ID;
	}

	public int getPosition() {
		return position;
	}

	public int setPosition(int position) {
		if (position >= MAX_POS)
			this.position = MAX_POS;
		else if (position <= MIN_POS)
			this.position = MIN_POS;
		else
			this.position = position;

		return this.position;
	}

	public int increasePosition(int increase_value) {
		if (position + increase_value <= MAX_POS)
			position = position + increase_value;

		return position;
	}

	public int decreasePosition(int increase_value) {
		if (position - increase_value >= MIN_POS)
			position = position - increase_value;

		return position;
	}

}
