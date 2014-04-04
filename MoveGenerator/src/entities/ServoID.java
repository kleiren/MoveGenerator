package entities;
/** ServoID object
*
* @author Rubén Vilches
* @version 1.0 - Mar 2014.
*/
public class ServoID extends Servo {

	private int pin;
	private String name;
	private int trim;

	public ServoID(int ID) {
		super(ID);
	}

	public ServoID(int ID, int position) {
		super(ID, position);
	}

	public int getPing() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTrim() {
		return trim;
	}

	public void setTrim(int trim) {
		this.trim = trim;
	}

	// ---------- GUI Variables ---------
	private int guiPositon_x;
	private int guiPositon_y;

	// ---------- GUI Functions ---------

	public int getGuiPositon_x() {
		return guiPositon_x;
	}

	public void setGuiPositon_x(int guiPositon_x) {
		this.guiPositon_x = guiPositon_x;
	}

	public int getGuiPositon_y() {
		return guiPositon_y;
	}

	public void setGuiPositon_y(int guiPositon_y) {
		this.guiPositon_y = guiPositon_y;
	}

}
