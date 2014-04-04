package moveGenerator.gui;

import manager.Config;
import manager.Core;
import moveGenerator.gui.menu.MenuToolbar;
import moveGenerator.gui.move.MovesManager;
import moveGenerator.gui.servo.ServosControl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Main shell, which must be executed, it will load all the visual components of
 * the interface.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MainShell {

	public void execute() {

		// # Propertites
		Display display = new Display();
		Display.setAppName(Config.getInstance().getGUI_Name());

		final Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MAX | SWT.MIN | SWT.RESIZE);
		shell.setSize(1100, 740);
		shell.setText(Config.getInstance().getGUI_Name());

		shell.setImage(new Image(display, Config.getInstance().getGUI_ICON()));
		shell.setBackground(new Color(null, 250, 250, 250));
		shell.setLayout(new GridLayout(1, false));

		// # ------ COMPONENTS ------

		// # Menu
		new MenuToolbar(shell, SWT.BAR);

		// # Middle Zone + Servo controls
		new ServosControl(shell, SWT.BORDER);

		// # Mobes Manager
		new MovesManager(shell, SWT.BORDER);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void main(String[] args) throws Exception {
		// # ------ CORE INITIALIZATION ------
		Core.getInstance().init();
		// MoveList.getInstance().loadFile("D:/example.txt");

		new MainShell().execute();

		// # ------ FINALIZE ------
		// # Close connections and purge.
		Core.getInstance().connection(false);
		System.gc();
	}

}