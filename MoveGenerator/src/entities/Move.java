package entities;

import java.util.ArrayList;
import java.util.List;
/** Move object provides list of Servo and move time.
 *
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class Move {

	private List<Servo> servos;
	private int moveTime;

	public Move() {
		servos = new ArrayList<Servo>();
	}

	public List<Servo> getServos() {
		return servos;
	}

	public Servo getServo(int servo_ID) {
		for (Servo servo : servos) {
			if (servo.getID() == servo_ID)
				return servo;
		}
		return null;
	}

	public void addServo(Servo servo) {
		servos.add(servo);
	}

	public int getPosition(int servo_ID) {
		for (Servo servo : servos) {
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
		servos.add(new Servo(servo_ID, servo_position));
	}

	public int getServos_Num() {
		return servos.size();
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int time) {
		this.moveTime = time;
	}

	public void copyOf(Move move) {
		List<Servo> sL = move.getServos();
		servos = new ArrayList<Servo>();
		for (Servo servo : sL) {
			Servo s = new Servo(servo.getID(), servo.getPosition());
			servos.add(s);
		}
		moveTime = move.getMoveTime();
	}

}
