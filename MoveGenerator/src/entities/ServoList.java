package entities;

/** ServoList object provides ServoID a list of ServoIDs
 *
 * @author Rubén Vilches
 * @version 1.0 - Mar, 2014.
 */
import java.util.ArrayList;
import java.util.List;

public class ServoList {

	private List<ServoID> servos;

	public ServoList() {
		servos = new ArrayList<ServoID>();
	}

	public List<ServoID> getServos() {
		return servos;
	}

	public ServoID getServo(int servo_ID) {
		for (ServoID servo : servos) {
			if (servo.getID() == servo_ID)
				return servo;
		}
		return null;
	}

	public void addServo(ServoID servo) {
		servos.add(servo);
	}

	public int getPosition(int servo_ID) {
		for (ServoID servo : servos) {
			if (servo.getID() == servo_ID)
				return servo.getPosition();
		}
		return -1;
	}

	public void setPosition(int servo_ID, int servo_position) {
		for (Servo servo : servos) {
			if (servo.getID() == servo_ID) {
				servo.setPosition(servo_position);
				return;
			}
		}
	}

	public void addPosition(int servo_ID, int servo_position) {
		servos.add(new ServoID(servo_ID, servo_position));
	}

	public int getServos_Num() {
		return servos.size();
	}

}
