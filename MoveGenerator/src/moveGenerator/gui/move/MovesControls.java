package moveGenerator.gui.move;

import manager.Core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Integrate all the controls related with multiple moves.<br>
 * Group of components to set Total Move time <br>
 * Loop check box.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MovesControls extends Composite {

	private MoveTotalTimeGroup moveTotalTimeGroup;

	public MovesControls(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	public void initialize() {

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		this.setLayout(gridLayout);
		this.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

		moveTotalTimeGroup = new MoveTotalTimeGroup(this, SWT.NONE);
		moveTotalTimeGroup.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		final Button button_loop = new Button(this, SWT.CHECK);
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd.heightHint = 50;
		button_loop.setLayoutData(gd);
		button_loop.setText("Loop Mode");
		button_loop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button DELETE is pressed

				if (Core.getInstance().isLoop())
					Core.getInstance().setLoop(false);
				else
					Core.getInstance().setLoop(true);
			}
		});

	}
}