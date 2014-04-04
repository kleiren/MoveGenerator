package manager;

import entities.Move;
import entities.MoveList;
import entities.Servo;
import entities.ServoID;
import entities.ServoList;
import moveGenerator.events.GuiChangesListener;
import moveGenerator.events.MyEvent;
import moveGenerator.events.MyEventStation;

/**
 * Core is the engine of all the system.
 * <p>
 * This class is constructed as a singleton, so it is used like short time
 * memory. It instance FileManager, RunManager and Db4oConnector also implements
 * an event handler.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class Core implements GuiChangesListener {

	private static Core instance = null;

	private Core() {
		moveList = new MoveList();

		move_index = 0;
		arduinoConnexion = false;
		loop = false;

		fileManager = new FileManager();
		run = new RunManager();

		// # Add Core to GuiChangesListener
		MyEventStation.getInstance().addGuiChangesListener(this);
	}

	public static Core getInstance() {
		if (instance == null) {
			instance = new Core();
		}
		return instance;
	}

	// ------- Initialize ------------
	public void init() {
		// Initialize servoList
		initServoList();
		// create newMoveList
		newMoveList();
	}

	private void initServoList_Dummy() {
		ServoList sL = new ServoList();

		for (int i = 0; i < 22; i++) {
			ServoID s = new ServoID(i, 90);
			// Servo s = new Servo(i, 90);
			s.setName("test");
			s.setPin(i);
			sL.addServo(s);
		}

		servoList = sL;
	}

	private void initServoList() {
		// Load from DB
		Db4oConnector dbCon = new Db4oConnector();
		dbCon.connect();
		servoList = dbCon.getServoList();
		dbCon.close();
	}

	// ------- Memory Variables ----------

	MoveList moveList;
	ServoList servoList;

	public MoveList getMoveList() {
		return moveList;
	}

	public void setMoveList(MoveList moveList) {
		this.moveList = moveList;
	}

	public ServoList getServoList() {
		return servoList;
	}

	public void setServoList(ServoList servoList) {
		this.servoList = servoList;
	}

	public void newMoveList() {
		// initialize moveList
		moveList = new MoveList();
		// create move
		Move move = new Move();
		move.setMoveTime(150);

		for (Servo servo : servoList.getServos()) {
			move.addPosition(servo.getID(), servo.getPosition());
		}

		moveList.addMove(move);
	}

	// ------- Control Variables -----------

	private int move_index;
	private boolean arduinoConnexion;
	private boolean loop;

	public int getMove_index() {
		return move_index;
	}

	public void setMove_index(int move_index) {
		this.move_index = move_index;
	}

	public boolean isArduinoConnexion() {
		return arduinoConnexion;
	}

	public void setArduinoConnexion(boolean arduinoConnexion) {
		this.arduinoConnexion = arduinoConnexion;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	// ---------- Manager Variables ----------

	private FileManager fileManager;
	private RunManager run;

	public boolean loadMoves(String path) {
		try {
			moveList = fileManager.LoadMovesFile(path);
			move_index = 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean saveMoves(String path) {
		try {
			fileManager.SaveMovesFile(path, moveList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getGeneratedString(int move_index) {
		return fileManager.getGeneratedString(moveList.getMove(move_index));
	}

	public int connection(boolean con) {
		return run.connection(con);
	}

	public void runMoves() {
		run.executeMoveList();
	}

	// ---------- Events ----------

	@Override
	public void updateView(MyEvent e) {
		if (arduinoConnexion) {
			if (e.getType() == MyEvent.MOVE_CHANGED || e.getType() == MyEvent.LOAD_MOVELIST
					|| e.getType() == MyEvent.DELETE_MOVE) {
				move_index = e.getValue();
				run.moveServos(e.getValue());
			}

			else if (e.getType() == MyEvent.POSITION) {
				run.moveServo(move_index, e.getValue());
			}
		}
	}

}
