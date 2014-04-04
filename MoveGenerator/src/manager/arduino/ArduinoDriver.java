package manager.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import manager.Config;

/**
 * ArduinoDriver establishes connection with Arduino.
 * <p>
 * Provides the following communication protocol: <br>
 * 1. It sends a String = char[] <br>
 * 2. All lines end with \n <br>
 * 3. The first char is a control parameter. <br>
 * 4. Communication baud is set at 115200. <br>
 * 5. Implemented functions: <br>
 * 5.1. Send configuration. It sends a strip of pins where Arduino must attach a
 * Servo. <br>
 * <b>Format</b>: 0servoPin1;servoPin2;servoPin3;...<br>
 * <b>Example</b>: 015;20;25;30;35<br>
 * 5.2. Send new Servo position. It sends the new position (int) where Arduino
 * must move the Servo.<br>
 * <b>Format</b>: 2servoPin;servoPosition<br>
 * <b>Example</b>: 215;150<br>
 * 
 * @author Laurid Meyer
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class ArduinoDriver implements SerialPortEventListener {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	// private static final String PORT_NAMES[] = {
	// "/dev/tty.usbserial-A9007UX1", // MacOSX
	// "/dev/ttyUSB0", // Linux
	// "COM3", "COM6", "COM7" // Windows
	// };
	private static String PORT_NAMES[] = { Config.getInstance().getArduino_COM() };

	/** Buffered input stream from the port */
	private static InputStream input;
	/** The output stream to the port */
	private static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 115200;// 9600;

	private String inputBuffer = "";

	public ArduinoDriver() {
	}

	public int initialize() {
		CommPortIdentifier portId = null;
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		PORT_NAMES = new String[] { Config.getInstance().getArduino_COM() };

		// # iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return -1;
		} else {
			System.out.println("Found your Port");
		}

		try {
			// # open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// # set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// # open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// # add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {
			System.err.println(e.toString());
			return -2;
		}

		return 1;
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		try {
			if (serialPort != null) {
				serialPort.removeEventListener();
				serialPort.close();
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This Method can be called to print a String to the serial connection
	 */
	public synchronized void sendString(String msg) {
		try {
			msg += '\n';// # add a newline character
			output.write(msg.getBytes());// write it to the serial
			output.flush();// refresh the serial
			// System.out.print("<- " + msg);// output for debugging
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This Method is called when Serialdata is recieved
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				// # read all incoming characters
				for (int i = 0; i < available; i++) {
					// # store it into an int
					int receivedVal = input.read();
					// # if the character is not a new line "\n" and not a
					// carriage return
					if (receivedVal != 10 && receivedVal != 13) {
						// # store the new character into a buffer
						inputBuffer += (char) receivedVal;
					}
					// # if it's a new line character
					else if (receivedVal == 10) {
						// # output for debugging
						System.out.println("-> " + inputBuffer);
						inputBuffer = "";// # clear the buffer
					}
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	public void sendConfig(String pinConfig) {
		sendString("0" + pinConfig);
	}

	public void sendPanic() {
		sendString("1");
	}

	public void send(int servo_pin, int position) {
		// System.out.println(servo + ";" + position);
		sendString("2" + servo_pin + ";" + position);
	}

}