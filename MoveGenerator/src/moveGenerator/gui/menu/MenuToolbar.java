package moveGenerator.gui.menu;

import manager.Core;
import moveGenerator.events.MyEvent;
import moveGenerator.events.MyEventStation;
import moveGenerator.gui.config.ConfigurationShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Menu toolbar. Implements access to the following functionalities:<br>
 * 1. Open/Save Move file. <br>
 * 2. Open configuration window (ConfigurationShell).<br>
 * 3. Connect to Arduino. <br>
 * 4. Run complete move.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MenuToolbar extends Menu {

	private Shell parent;

	private Shell connectingDialog;
	private Shell runningDialog;
	private Thread runMovement;
	private boolean paused;

	public MenuToolbar(Shell parent, int style) {
		super(parent, style);
		this.parent = parent;

		paused = false;
		initialize();
	}

	public void initialize() {
		createMenuFile();
		parent.setMenuBar(this);

	}

	private void createMenuFile() {

		MenuItem archive = new MenuItem(this, SWT.CASCADE);
		archive.setText("Archive");
		// Menu contenedor para desplegar el MenuItem Archivo
		Menu fileMenu = new Menu(parent, SWT.DROP_DOWN);
		// Asociamos el contenedor al MenuItem
		archive.setMenu(fileMenu);
		// Añadimos MenuItem individuales al contenedor
		MenuItem newItem = new MenuItem(fileMenu, SWT.PUSH);
		newItem.setText("New");
		new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText("Save");
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("Open");

		newItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {

				Core.getInstance().newMoveList();
				// # SENT NOTIFICATION
				MyEventStation.getInstance().updateView(0,
						MyEvent.LOAD_MOVELIST);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		openItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(parent, SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.txt" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);

				if (selected != null) {
					Core.getInstance().loadMoves(selected);
					// # SENT NOTIFICATION
					MyEventStation.getInstance().updateView(0,
							MyEvent.LOAD_MOVELIST);

					// # Create dialog
					final MessageBox dialog = new MessageBox(parent,
							SWT.ICON_INFORMATION | SWT.OK);
					dialog.setText("Open");
					dialog.setMessage("Filed Open");
					// # open dialog and await user selection
					dialog.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		saveItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(parent, SWT.SAVE);
				fd.setText("Save");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.txt" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);

				if (selected != null) {
					Core.getInstance().saveMoves(selected);

					// # Create dialog
					final MessageBox dialog = new MessageBox(parent,
							SWT.ICON_INFORMATION | SWT.OK);
					dialog.setText("Save");
					dialog.setMessage("File Saved");
					// # open dialog and await user selection
					dialog.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem config = new MenuItem(this, SWT.PUSH);
		config.setText("Configuration");
		config.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				new ConfigurationShell(parent, SWT.NONE);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem connection = new MenuItem(this, SWT.CASCADE);
		connection.setText("Connection");
		// Menu contenedor para desplegar el MenuItem Archivo
		Menu connectionMenu = new Menu(parent, SWT.DROP_DOWN);
		// Asociamos el contenedor al MenuItem
		connection.setMenu(connectionMenu);
		final MenuItem connect = new MenuItem(connectionMenu, SWT.RADIO);
		connect.setText("Connect");
		final MenuItem disconnect = new MenuItem(connectionMenu, SWT.RADIO);
		disconnect.setText("Disconnect");
		disconnect.setSelection(true);

		connect.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isSelected = ((MenuItem) e.getSource()).getSelection();
				int res = 0;
				if (isSelected) {

					showConnectingDialog();
					res = Core.getInstance().connection(true);
					connectingDialog.close();

					if (res == -1) {
						disconnect.setSelection(true);
						connect.setSelection(false);
						final MessageBox dialog = new MessageBox(parent,
								SWT.ERROR | SWT.OK);
						dialog.setText("ERROR");
						dialog.setMessage("Arduino not found.\nConnect the board to the computer and make sure to configure the correct port.");
						dialog.open();

					} else if (res == -2) {
						disconnect.setSelection(true);
						connect.setSelection(false);
						final MessageBox dialog = new MessageBox(parent,
								SWT.ERROR | SWT.OK);
						dialog.setText("ERROR");
						dialog.setMessage("Communication with Arduino failed.");
						dialog.open();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		disconnect.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isSelected = ((MenuItem) e.getSource()).getSelection();
				if (isSelected)
					Core.getInstance().connection(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem run = new MenuItem(this, SWT.PUSH);
		run.setText("Run");

		run.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (Core.getInstance().isArduinoConnexion()) {

					showRunningDialog();
					runMovement = new Thread(new Runnable() {
						@Override
						public void run() {
							Core.getInstance().runMoves();
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									runningDialog.close();
								}
							});
						}
					});
					runMovement.start();
				}

				else {
					// create dialog with ok and cancel button and info icon
					final MessageBox dialog = new MessageBox(parent, SWT.ERROR
							| SWT.OK);
					dialog.setText("Run");
					dialog.setMessage("Not connected");
					dialog.open();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private void showConnectingDialog() {
		connectingDialog = new Shell(parent, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		int size_x = 200;
		int size_y = 100;
		int x = (parent.getBounds().x + parent.getBounds().width - size_x) / 2;
		int y = (parent.getBounds().y + parent.getBounds().height - size_y) / 2;
		connectingDialog.setBounds(x, y, size_x, size_y);
		connectingDialog.setText("Arduino Connexion");

		connectingDialog.setLayout(new GridLayout(1, false));
		connectingDialog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));

		Label l = new Label(connectingDialog, SWT.NULL);
		l.setText("Connecting ...");
		l.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		connectingDialog.open();
	}

	private void showRunningDialog() {
		runningDialog = new Shell(parent, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		int size_x = 200;
		int size_y = 100;
		int x = (parent.getBounds().x + parent.getBounds().width - size_x) / 2;
		int y = (parent.getBounds().y + parent.getBounds().height - size_y) / 2;
		runningDialog.setBounds(x, y, size_x, size_y);
		runningDialog.setText("Executing full move");

		runningDialog.setLayout(new GridLayout(2, false));
		runningDialog
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label l = new Label(runningDialog, SWT.NORMAL);
		if (Core.getInstance().isLoop())
			l.setText("Running on Loop ...");
		else
			l.setText("Running ...");
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gridData.horizontalSpan = 2;
		l.setLayoutData(gridData);

		Button button_Stop = new Button(runningDialog, SWT.PUSH);
		button_Stop.setText("Stop");
		button_Stop.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));
		button_Stop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button is pressed
				runMovement.stop();
				runningDialog.close();
			}
		});

		final Button button_Pause = new Button(runningDialog, SWT.PUSH);
		button_Pause.setText("  Pause  ");
		button_Pause.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));
		button_Pause.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button is pressed
				if (!paused) {
					paused = true;
					runMovement.suspend();
					button_Pause.setText("Resume");

				} else {
					paused = false;
					runMovement.resume();
					button_Pause.setText("  Pause  ");
				}
			}
		});

		runningDialog.open();
	}

	@Override
	public void checkSubclass() {

	}
}
