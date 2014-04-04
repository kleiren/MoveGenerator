package moveGenerator.gui.config;

import manager.Config;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Part of Configuration shell.<br>
 * Group to encapsulate all Arduino's configurations.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class ArduinoConfig extends Group {

	private Text COM_textBox;

	public ArduinoConfig(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		this.setText("Arduino");
		this.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(SWT.FILL, SWT.WRAP, true, false);
		gd.horizontalSpan = 9;
		this.setLayoutData(gd);

		Label arduino_COM_label = new Label(this, SWT.NORMAL);
		arduino_COM_label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true));
		arduino_COM_label.setText("COM port:");

		COM_textBox = new Text(this, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		gd.grabExcessHorizontalSpace = true;
		String text = Config.getInstance().getArduino_COM();
		gd.widthHint = text.split("").length * 8;
		COM_textBox.setLayoutData(gd);
		COM_textBox.setTextLimit(200);
		// #Load default text
		COM_textBox.setText(text);

	}

	protected String getCOM() {
		return COM_textBox.getText();
	}

	@Override
	public void checkSubclass() {

	}
}
