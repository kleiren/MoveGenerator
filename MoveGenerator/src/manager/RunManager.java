package manager;

import java.util.ArrayList;
import java.util.List;

import entities.Move;
import entities.Servo;
import entities.ServoID;
import manager.arduino.ArduinoDriver;

/**
 * RunManager provides all the functions related with Arduino. Through
 * ArduinoDriver class movements are ordered.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class RunManager {

	// private static final int SERVO_NUM = Config.getInstance().getServo_NUM();
	private static final long INTERVALTIME = Config.getInstance().getMove_IntervalTime();

	private ArduinoDriver api;
	Move oldPositions;

	public RunManager() {
		api = new ArduinoDriver();

		oldPositions = new Move();
	}

	public int connection(boolean connected) {
		System.out.println("Connection = " + connected);
		// this.connected = connected;
		int res = -1;
		if (connected) {
			res = api.initialize();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		} else
			api.close();

		if (res > 0) {
			Core.getInstance().setArduinoConnexion(connected);
			init();
			moveServos(Core.getInstance().getMove_index());
		}

		return res;
	}

	private void init() {
		String pinConfig = "";
		oldPositions = new Move();
		for (ServoID servo : Core.getInstance().getServoList().getServos()) {
			oldPositions.addPosition(servo.getID(), -90);
			// prepare pinConfig
			pinConfig += servo.getPing() + ";";
		}

		api.sendConfig(pinConfig);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void moveServos(int move_index) {
		for (int i = 0; i < Core.getInstance().getMoveList().getMove(move_index).getServos_Num(); i++) {
			int servoID = Core.getInstance().getMoveList().getMove(move_index).getServos().get(i).getID();
			moveServo(move_index, servoID);
		}
	}

	public void send(Servo newPosition) {
		ServoID servoInfo = Core.getInstance().getServoList().getServo(newPosition.getID());
		api.send(servoInfo.getPing(), newPosition.getPosition() + servoInfo.getTrim());
	}

	public void moveServo(int move_index, int servo_ID) {
		Servo servo = Core.getInstance().getMoveList().getMove(move_index).getServo(servo_ID);
		Servo oldServo = oldPositions.getServo(servo_ID);

		if (oldServo.getPosition() != servo.getPosition()) {
			send(servo);
			oldServo.setPosition(servo.getPosition());
		}
	}

	public void executeMoveList() {
		System.out.println("Ejecutando");
		do {
			for (int i = 0; i < Core.getInstance().getMoveList().getSize(); i++) {
				moveNServos(Core.getInstance().getMoveList().getMove(i));
			}
		} while (Core.getInstance().isLoop());
	}

	private void moveNServos(Move newPosition) {
		Move temp_old_positions = new Move();
		temp_old_positions.copyOf(oldPositions);

		List<Float> increment = new ArrayList<Float>();

		// calcula el incremento de posicion en funcion del tiempo
		for (Servo servo : newPosition.getServos()) {
			increment.add(getGradeInrcrement(newPosition.getMoveTime(), oldPositions.getPosition(servo.getID()),
					servo.getPosition()));
		}

		// Calcular la duracion exacta del ciclo
		// tiempo de referencia final
		long final_time = System.currentTimeMillis() + newPosition.getMoveTime();

		int iteration = 1; // para saber en que iteracion nos encontramos
		while (System.currentTimeMillis() < final_time) {
			// referencia del tiempo fianl del subciclo
			long interval_time = System.currentTimeMillis() + INTERVALTIME;
			int pos = -1;
			for (int i = 0; i < newPosition.getServos_Num(); i++) {
				pos = (oldPositions.getServos().get(i).getPosition() + (int) (iteration * increment.get(i)));

				if (pos != temp_old_positions.getServos().get(i).getPosition()) {
					temp_old_positions.getServos().get(i).setPosition(pos);
					send(new Servo(newPosition.getServos().get(i).getID(), pos));
				}
			}
			iteration++;

			// Esperamos a que termine el subciclo
			while (System.currentTimeMillis() < interval_time) {
			}

		}

		oldPositions.copyOf(newPosition);
	}

	float getGradeInrcrement(int time, int posI, int posF) {

		// # Cantidad de incrementos (escala 20ms)
		// numeroIncrementos = time/20.0
		// # Cantidad de incrementos (escala INTERVALTIME ms)
		// numeroIncrementos = time/INTERVALTIME
		// # Variacion de posicion en cada incremento
		// varPosicion/numeroIncrementos
		float totalIncr = posF - posI;
		float var = time / INTERVALTIME;
		return totalIncr / var;
	}
}
