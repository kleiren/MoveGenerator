package moveGenerator.gui.config;

import manager.Config;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Configuration shell, window where settings will be set.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class ConfigurationShell {

	private Shell parent;
	private Shell configShell;

	private ArduinoConfig arduinoConfig;
	private ServosConfig servosConfig;

	private GridData gd;

	public ConfigurationShell(Shell parent, int style) {
		super();
		this.parent = parent;

		initialize();
	}

	public void initialize() {
		configShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		int size_x = 400;
		int size_y = 150;
		configShell.setSize(size_x, size_y);
		int x = (parent.getBounds().x + parent.getBounds().width - size_x) / 2;
		int y = (parent.getBounds().y + parent.getBounds().height - size_y) / 2;

		configShell.setBounds(x, y, size_x, size_y);
		configShell.setText("Configuration");

		configShell.setLayout(new GridLayout(9, true));
		configShell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Arduino Configuration Group
		arduinoConfig = new ArduinoConfig(configShell, SWT.NONE);

		// Servos Configuration Group
		//servosConfig = new ServosConfig(configShell, SWT.NONE);

		// BUTTONS
		new Label(configShell, SWT.NONE);
		new Label(configShell, SWT.NONE);

		Button button_Save = new Button(configShell, SWT.PUSH);
		button_Save.setText("Save");
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		gd.widthHint = 100;
		button_Save.setLayoutData(gd);
		button_Save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button is pressed
				saveChanges();
				configShell.close();
			}
		});
		new Label(configShell, SWT.NONE);

		Button button_Cancel = new Button(configShell, SWT.NONE);
		button_Cancel.setText("Cancel");
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.horizontalSpan = 2;
		gd.widthHint = 41;
		button_Cancel.setLayoutData(gd);
		button_Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button is pressed
				configShell.close();
			}
		});
		new Label(configShell, SWT.NONE);
		new Label(configShell, SWT.NONE);

		configShell.open();
	}

	private void saveChanges() {
		Config.getInstance().setArduino_COM(arduinoConfig.getCOM());
	}
}
