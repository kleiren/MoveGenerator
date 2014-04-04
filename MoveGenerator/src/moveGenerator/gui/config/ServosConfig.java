package moveGenerator.gui.config;

import java.util.ArrayList;
import java.util.List;

import manager.Config;
import manager.Core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import entities.Servo;

/**
 * Part of Configuration shell.<br>
 * Group to encapsulate all Servo's configurations.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class ServosConfig extends Group {

	private Text number_textBox;
	private List<Servo> servoList = new ArrayList<Servo>();

	public ServosConfig(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		this.setText("Servos");
		this.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(SWT.FILL, SWT.WRAP, true, false);
		gd.horizontalSpan = 9;
		this.setLayoutData(gd);

		Listener justNumbers = new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		};

		// #Servo number
		Label number_label = new Label(this, SWT.NORMAL);
		number_label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true));
		number_label.setText("Servo number:");

		number_textBox = new Text(this, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		gd.grabExcessHorizontalSpace = true;
		String text = Integer.toString(Core.getInstance().getServoList().getServos_Num());
		gd.widthHint = text.split("").length * 8;
		number_textBox.setLayoutData(gd);
		number_textBox.setTextLimit(2);
		number_textBox.setText(text);
		number_textBox.addListener(SWT.Verify, justNumbers);

		// #Servo information

	}

	public List<Servo> getServoList() {
		return servoList;
	}

	@Override
	public void checkSubclass() {

	}
}
