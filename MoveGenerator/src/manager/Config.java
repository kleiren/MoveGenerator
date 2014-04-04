package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * Config class load properties from properties file.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class Config {

	private static Config instance = null;
	Properties properties;

	private Config() {

		// ## Create and load default properties

		// #LOAD FILE FROM TELATIVE PROJECT PATH
		this.properties = new Properties();
		try {
			// #FILE ON THE PACKAGE ROOT FOLDER (two are equivalent)
			FileInputStream is = new FileInputStream("MoveGenerator.properties");
			properties.load(is);
			is.close();
			// #FILE IN THE SAME PACKAGE OF CLASS
			// properties.load(this.class.getClassLoader().getResourceAsStream("config.properties"));
			
		} catch (IOException ex) {
			try {
				properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	private void update(String key, String value) {

		properties.setProperty(key, value);
		try {

			/* set some properties here */
			Properties tmp = new Properties() {
				private static final long serialVersionUID = 1L;

				@Override
				public Set<Object> keySet() {
					return Collections.unmodifiableSet(new TreeSet<Object>(super.keySet()));
				}

				@Override
				public synchronized Enumeration<Object> keys() {
					return Collections.enumeration(new TreeSet<Object>(super.keySet()));
				}

			};
			tmp.putAll(properties);

			FileOutputStream out = new FileOutputStream("MoveGenerator.properties");
			tmp.store(out, null);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// # LOAD PROPERTIES

	// #GUI
	public InputStream getGUI_ICON() {
		String src = properties.getProperty("GUI.icon");
		return getClass().getClassLoader().getResourceAsStream(src);
	}

	public String getGUI_Name() {
		return properties.getProperty("GUI.name");
	}

	public InputStream getGUI_ServosControl_Background() {
		String src = properties.getProperty("GUI.servosControl.background");
		return getClass().getClassLoader().getResourceAsStream(src);
	}

	// #ARDUINO
	public String getArduino_COM() {
		return properties.getProperty("arduino.COM");
	}

	public void setArduino_COM(String arduino_COM) {
		update("arduino.COM", arduino_COM);
	}

	// #TOTA Time
	public int getTotalTime_MaxTime() {
		return Integer.parseInt(properties.getProperty("totalTime.maxTime"));
	}

	public int getTotalTime_MinTime() {
		return Integer.parseInt(properties.getProperty("totalTime.minTime"));
	}

	public int getTotalTime_SlowIncrement() {
		return Integer.parseInt(properties.getProperty("totalTime.slowIncrement"));
	}

	public int getTotalTime_FastIncrement() {
		return Integer.parseInt(properties.getProperty("totalTime.fastIncrement"));
	}

	public int getTotalTime_Button_TimeDelay() {
		return Integer.parseInt(properties.getProperty("totalTime.button.timeDelay"));
	}

	// #MOVE Time
	public int getMoveTime_MaxTime() {
		return Integer.parseInt(properties.getProperty("moveTime.maxTime"));
	}

	public int getMoveTime_MinTime() {
		return Integer.parseInt(properties.getProperty("moveTime.minTime"));
	}

	public int getMoveTime_SlowIncrement() {
		return Integer.parseInt(properties.getProperty("moveTime.slowIncrement"));
	}

	public int getMoveTime_FastIncrement() {
		return Integer.parseInt(properties.getProperty("moveTime.fastIncrement"));
	}

	public int getMoveTime_Button_TimeDelay() {
		return Integer.parseInt(properties.getProperty("moveTime.button.timeDelay"));
	}

	public int getMove_IntervalTime() {
		return Integer.parseInt(properties.getProperty("move.intervaTime"));
	}

	public int getServo_MaxPosition() {
		return Integer.parseInt(properties.getProperty("servo.maxPosition"));
	}

	public int getServo_MinPostition() {
		return Integer.parseInt(properties.getProperty("servo.minPosition"));
	}

	public int getServo_SlowIncrement() {
		return Integer.parseInt(properties.getProperty("servo.slowIncrement"));
	}

	public int getServo_FastIncrement() {
		return Integer.parseInt(properties.getProperty("servo.fastIncrement"));
	}

	public int getServo_Button_TimeDelay() {
		return Integer.parseInt(properties.getProperty("servo.button.timeDelay"));
	}

}
