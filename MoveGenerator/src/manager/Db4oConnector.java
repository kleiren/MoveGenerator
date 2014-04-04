package manager;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;

import entities.ServoList;

/**
 * Db4oConnector establishes connection with data base.
 * <p>
 * Allows: set, get and update a ServoList object. <br>
 * Using db4o, object oriented DB.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class Db4oConnector {

	private static ObjectContainer oc = null;
	private String filePath;
	private EmbeddedConfiguration config;

	public Db4oConnector() {

		filePath = "servos.db4o";
	}

	public void connect() {
		try {
			config = Db4oEmbedded.newConfiguration();
			oc = Db4oEmbedded.openFile(config, filePath);

		} catch (Exception e) {
			System.out.println("Error connecting DB");
		}
	}

	public void close() {
		if (oc != null)
			oc.close();
	}

	// --------- SERVOLIST FUNTIONS---------------

	public void setServoList(ServoList servoList) {
		oc.store(servoList);
		oc.commit();
	}

	private ObjectSet<ServoList> getServoL() {
		Query query = oc.query();
		query.constrain(ServoList.class);
		return query.execute();
	}

	public ServoList getServoList() {
		ServoList servos = new ServoList();
		ObjectSet<ServoList> result = getServoL();
		while (result.hasNext())
			servos = (ServoList) result.next();
		return servos;
	}

	private void deleteServoList() {
		ObjectSet<ServoList> result = getServoL();

		while (result.hasNext()) {
			ServoList servoL = (ServoList) result.next();
			oc.delete(servoL);
		}
		oc.commit();
		oc.close();
	}

	public void updateServo(ServoList servoList) {
		deleteServoList();
		setServoList(servoList);
	}

}
