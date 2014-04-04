package moveGenerator.gui.move;

import manager.Core;
import moveGenerator.events.MyEvent;
import moveGenerator.events.MyEventStation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import entities.Move;

/**
 * Integrate all the controls related with a move.<br>
 * Buttons: Add Move and Remove Move.<br>
 * Group of components to set Move time.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MoveControls extends Composite {

	private MoveTimeGroup moveTimeGroup;

	public MoveControls(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	public void initialize() {

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		this.setLayout(gridLayout);
		this.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true));

		moveTimeGroup = new MoveTimeGroup(this, SWT.NONE);
		moveTimeGroup.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		Button button_newMove = new Button(this, SWT.CENTER);
		button_newMove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		button_newMove.setText("Add Move");
		button_newMove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button ADD is pressed

				int index = Core.getInstance().getMove_index();
				Move move = Core.getInstance().getMoveList().getMove(index);

				index = index + 1;
				Core.getInstance().setMove_index(index);

				Move newMove = new Move();
				newMove.copyOf(move);

				Core.getInstance().getMoveList().addMove(index, newMove);
				MyEventStation.getInstance().updateView(index, MyEvent.ADD_MOVE);
			}
		});

		Button button_deleteMove = new Button(this, SWT.CENTER);
		button_deleteMove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		button_deleteMove.setText("Delete Move");
		button_deleteMove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button DELETE is pressed

				int index = Core.getInstance().getMove_index();
				Core.getInstance().getMoveList().deleteMove(index);

				if (index > Core.getInstance().getMoveList().getSize() - 1)
					index = Core.getInstance().getMoveList().getSize() - 1;

				Core.getInstance().setMove_index(index);
				MyEventStation.getInstance().updateView(index, MyEvent.DELETE_MOVE);
			}
		});
	}
}