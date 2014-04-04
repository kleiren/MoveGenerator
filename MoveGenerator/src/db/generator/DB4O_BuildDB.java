package db.generator;

import entities.ServoID;
import entities.ServoList;
import manager.Db4oConnector;

/**
 * DataBase Generator. Fills the database with a list of servos.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class DB4O_BuildDB {

	static Db4oConnector db = new Db4oConnector();

	public static void main(String[] args) {

		ServoList servoList = generateServoList();
		db.connect();

		save(servoList);

		load();
		db.close();
	}

	public static void save(ServoList sl) {
		db.setServoList(sl);
	}

	public static void load() {
		ServoList servoList = db.getServoList();

		for (ServoID servo : servoList.getServos()) {
			System.out.println("ID:" + servo.getID() + "  NAME:" + servo.getName() + "  POS:" + servo.getPosition()
					+ "  X:" + servo.getGuiPositon_x());
		}
	}

	private void generateDegaultServoList() {
		ServoList servoList = new ServoList();
		ServoID servo;

		int servoNum = 10;
		for (int i = 0; i < servoNum; i++) {
			servo = new ServoID(0, 90);
			servo.setName("Servo" + i);
			servo.setPin(i + 20);
			servo.setTrim(0);
			int x = (i + 1 % 5) * 40;
			servo.setGuiPositon_x(x);
			int y = ((i / 5) + 1) * 40;
			servo.setGuiPositon_y(y);

			servoList.addServo(servo);
		}
	}

	private static ServoList generateServoList() {
		ServoList servoList = new ServoList();
		ServoID servo;

		servo = new ServoID(0, 90);
		servo.setName("Neck (yaw)");
		servo.setPin(32);
		servo.setTrim(0);
		servo.setGuiPositon_x(-47);
		servo.setGuiPositon_y(-185);
		servoList.addServo(servo);

		servo = new ServoID(1, 90);
		servo.setName("R Shoulder (pitch)");
		servo.setPin(25);
		servo.setTrim(0);
		servo.setGuiPositon_x(-201);
		servo.setGuiPositon_y(-214);
		servoList.addServo(servo);

		servo = new ServoID(2, 90);
		servo.setName("R Shoulder (roll)");
		servo.setPin(24);
		servo.setTrim(0);
		servo.setGuiPositon_x(-236);
		servo.setGuiPositon_y(-156);
		servoList.addServo(servo);

		servo = new ServoID(3, 90);
		servo.setName("L Shoulder (pitch)");
		servo.setPin(45);
		servo.setTrim(0);
		servo.setGuiPositon_x(82);
		servo.setGuiPositon_y(-214);
		servoList.addServo(servo);

		servo = new ServoID(4, 90);
		servo.setName("L Shoulder (roll)");
		servo.setPin(44);
		servo.setTrim(0);
		servo.setGuiPositon_x(127);
		servo.setGuiPositon_y(-156);
		servoList.addServo(servo);

		servo = new ServoID(5, 90);
		servo.setName("R Elbow (pitch)");
		servo.setPin(23);
		servo.setTrim(0);
		servo.setGuiPositon_x(-253);
		servo.setGuiPositon_y(-101);
		servoList.addServo(servo);

		servo = new ServoID(6, 90);
		servo.setName("L Elbow (pitch)");
		servo.setPin(43);
		servo.setTrim(0);
		servo.setGuiPositon_x(150);
		servo.setGuiPositon_y(-101);
		servoList.addServo(servo);

		servo = new ServoID(7, 90);
		servo.setName("Hip (yaw)");
		servo.setPin(31);
		servo.setTrim(0);
		servo.setGuiPositon_x(-47);
		servo.setGuiPositon_y(-116);
		servoList.addServo(servo);

		servo = new ServoID(8, 90);
		servo.setName("R Leg (yaw)");
		servo.setPin(30);
		servo.setTrim(0);
		servo.setGuiPositon_x(-97);
		servo.setGuiPositon_y(-59);
		servoList.addServo(servo);

		servo = new ServoID(9, 90);
		servo.setName("L Leg (yaw)");
		servo.setPin(33);
		servo.setTrim(0);
		servo.setGuiPositon_x(0);
		servo.setGuiPositon_y(-59);
		servoList.addServo(servo);

		servo = new ServoID(10, 90);
		servo.setName("R Leg (roll)");
		servo.setPin(12);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(-47);
		servoList.addServo(servo);

		servo = new ServoID(11, 90);
		servo.setName("R Leg (pitch)");
		servo.setPin(13);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(5);
		servoList.addServo(servo);

		servo = new ServoID(12, 90);
		servo.setName("L Leg (roll)");
		servo.setPin(6);
		servo.setTrim(0);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(-47);
		servoList.addServo(servo);

		servo = new ServoID(13, 90);
		servo.setName("L Leg (pitch)");
		servo.setPin(7);
		servo.setTrim(0);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(5);
		servoList.addServo(servo);

		servo = new ServoID(14, 90);
		servo.setName("R Knee1 (pitch)");
		servo.setPin(10);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(58);
		servoList.addServo(servo);

		servo = new ServoID(15, 90);
		servo.setName("R Knee2 (pitch)");
		servo.setPin(11);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(111);
		servoList.addServo(servo);

		servo = new ServoID(16, 90);
		servo.setName("L Knee1 (pitch)");
		servo.setPin(4);
		servo.setTrim(0);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(58);
		servoList.addServo(servo);

		servo = new ServoID(17, 90);
		servo.setName("L Knee2 (pitch)");
		servo.setPin(5);
		servo.setTrim(0);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(111);
		servoList.addServo(servo);

		servo = new ServoID(18, 90);
		servo.setName("R Ankle (pitch)");
		servo.setPin(8);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		servo = new ServoID(19, 90);
		servo.setName("R Ankle (roll)");
		servo.setPin(9);
		servo.setTrim(0);
		servo.setGuiPositon_x(-313);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		servo = new ServoID(20, 90);
		servo.setName("L Ankle (pitch)");
		servo.setPin(2);
		servo.setTrim(0);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		servo = new ServoID(21, 90);
		servo.setName("L Ankle (roll)");
		servo.setPin(3);
		servo.setTrim(0);
		servo.setGuiPositon_x(220);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		return servoList;
	}

	private static ServoList generateServoList_trim() {
		ServoList servoList = new ServoList();
		ServoID servo;

		servo = new ServoID(0, 90);
		servo.setName("Neck (yaw)");
		servo.setPin(32);
		servo.setTrim(0);
		servo.setGuiPositon_x(-47);
		servo.setGuiPositon_y(-185);
		servoList.addServo(servo);

		servo = new ServoID(1, 90);
		servo.setName("R Shoulder (pitch)");
		servo.setPin(25);
		servo.setTrim(4);
		servo.setGuiPositon_x(-201);
		servo.setGuiPositon_y(-214);
		servoList.addServo(servo);

		servo = new ServoID(2, 90);
		servo.setName("R Shoulder (roll)");
		servo.setPin(24);
		servo.setTrim(7);
		servo.setGuiPositon_x(-236);
		servo.setGuiPositon_y(-156);
		servoList.addServo(servo);

		servo = new ServoID(3, 90);
		servo.setName("L Shoulder (pitch)");
		servo.setPin(45);
		servo.setTrim(-6);
		servo.setGuiPositon_x(82);
		servo.setGuiPositon_y(-214);
		servoList.addServo(servo);

		servo = new ServoID(4, 90);
		servo.setName("L Shoulder (roll)");
		servo.setPin(44);
		servo.setTrim(0);
		servo.setGuiPositon_x(127);
		servo.setGuiPositon_y(-156);
		servoList.addServo(servo);

		servo = new ServoID(5, 90);
		servo.setName("R Elbow (pitch)");
		servo.setPin(23);
		servo.setTrim(54);
		servo.setGuiPositon_x(-253);
		servo.setGuiPositon_y(-101);
		servoList.addServo(servo);

		servo = new ServoID(6, 90);
		servo.setName("L Elbow (pitch)");
		servo.setPin(43);
		servo.setTrim(-44);
		servo.setGuiPositon_x(150);
		servo.setGuiPositon_y(-101);
		servoList.addServo(servo);

		servo = new ServoID(7, 90);
		servo.setName("Hip (yaw)");
		servo.setPin(31);
		servo.setTrim(-5);
		servo.setGuiPositon_x(-47);
		servo.setGuiPositon_y(-116);
		servoList.addServo(servo);

		servo = new ServoID(8, 90);
		servo.setName("R Leg (yaw)");
		servo.setPin(30);
		servo.setTrim(0);
		servo.setGuiPositon_x(-97);
		servo.setGuiPositon_y(-59);
		servoList.addServo(servo);

		servo = new ServoID(9, 90);
		servo.setName("L Leg (yaw)");
		servo.setPin(33);
		servo.setTrim(0);
		servo.setGuiPositon_x(0);
		servo.setGuiPositon_y(-59);
		servoList.addServo(servo);

		servo = new ServoID(10, 90);
		servo.setName("R Leg (roll)");
		servo.setPin(12);
		servo.setTrim(14);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(-47);
		servoList.addServo(servo);

		servo = new ServoID(11, 90);
		servo.setName("R Leg (pitch)");
		servo.setPin(13);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(5);
		servoList.addServo(servo);

		servo = new ServoID(12, 90);
		servo.setName("L Leg (roll)");
		servo.setPin(6);
		servo.setTrim(-13);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(-47);
		servoList.addServo(servo);

		servo = new ServoID(13, 90);
		servo.setName("L Leg (pitch)");
		servo.setPin(7);
		servo.setTrim(14);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(5);
		servoList.addServo(servo);

		servo = new ServoID(14, 90);
		servo.setName("R Knee1 (pitch)");
		servo.setPin(10);
		servo.setTrim(-5);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(58);
		servoList.addServo(servo);

		servo = new ServoID(15, 90);
		servo.setName("R Knee2 (pitch)");
		servo.setPin(11);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(111);
		servoList.addServo(servo);

		servo = new ServoID(16, 90);
		servo.setName("L Knee1 (pitch)");
		servo.setPin(4);
		servo.setTrim(-3);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(58);
		servoList.addServo(servo);

		servo = new ServoID(17, 90);
		servo.setName("L Knee2 (pitch)");
		servo.setPin(5);
		servo.setTrim(-4);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(111);
		servoList.addServo(servo);

		servo = new ServoID(18, 90);
		servo.setName("R Ankle (pitch)");
		servo.setPin(8);
		servo.setTrim(0);
		servo.setGuiPositon_x(-210);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		servo = new ServoID(19, 90);
		servo.setName("R Ankle (roll)");
		servo.setPin(9);
		servo.setTrim(-11);
		servo.setGuiPositon_x(-313);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		servo = new ServoID(20, 90);
		servo.setName("L Ankle (pitch)");
		servo.setPin(2);
		servo.setTrim(0);
		servo.setGuiPositon_x(115);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		servo = new ServoID(21, 90);
		servo.setName("L Ankle (roll)");
		servo.setPin(3);
		servo.setTrim(-25);
		servo.setGuiPositon_x(220);
		servo.setGuiPositon_y(162);
		servoList.addServo(servo);

		return servoList;
	}
}
