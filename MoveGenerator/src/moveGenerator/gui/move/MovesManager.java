package moveGenerator.gui.move;

import manager.Core;
import moveGenerator.events.GuiChangesListener;
import moveGenerator.events.MyEvent;
import moveGenerator.events.MyEventStation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Moves manager. Composite of all elements related with movements.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MovesManager extends Composite implements GuiChangesListener {

	private Composite parent;
	private Table tableMoves;

	private int table_Index;

	public MovesManager(Composite parent, int style) {
		super(parent, style);
		this.parent = parent;

		MyEventStation.getInstance().addGuiChangesListener(this);
		table_Index = 0;

		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 10;
		this.setLayout(gridLayout);

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.heightHint = 210;
		this.setLayoutData(gridData);

		Font font = new Font(parent.getDisplay(), "default", 13, 13);

		Label title = new Label(this, SWT.NORMAL);
		title.setText("Movements Manager");
		title.setFont(font);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.horizontalSpan = 10;
		title.setLayoutData(gridData);

		new MoveControls(this, SWT.WRAP);

		tableMoves = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableMoves.setLinesVisible(true);
		tableMoves.setHeaderVisible(true);
		tableMoves.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableMoves.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableMoves.getSelectionIndex() != table_Index) {
					table_Index = tableMoves.getSelectionIndex();
					Core.getInstance().setMove_index(table_Index);
					MyEventStation.getInstance().updateView(table_Index, MyEvent.MOVE_CHANGED);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		fillTableMoves();

		new MovesControls(this, SWT.WRAP);

	}

	// # FILL TABLE MOVES : rellena la tabla de movimientos
	public void fillTableMoves() {

		// # Limpiamos la tabla
		tableMoves.clearAll();
		// # Contador a 0, desde donde se añadiran más filas
		tableMoves.setItemCount(0);

		String[] titles = { "Time", "Position Vector" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(tableMoves, SWT.NONE);
			column.setText(titles[i]);
		}

		for (int i = 0; i < Core.getInstance().getMoveList().getSize(); i++) {
			TableItem item = new TableItem(tableMoves, SWT.NONE);
			item.setText(0, String.valueOf(Core.getInstance().getMoveList().getMove(i).getMoveTime()));
			item.setText(1, Core.getInstance().getGeneratedString(i));
		}

		for (int i = 0; i < titles.length; i++) {
			tableMoves.getColumn(i).pack();
		}

		tableMoves.setSelection(0);
	}

	@Override
	public void updateView(MyEvent e) {

		if (e.getType() == MyEvent.POSITION) {
			String st = Core.getInstance().getGeneratedString(getSelectionIndex());
			tableMoves.getItem(getSelectionIndex()).setText(1, st);
		}

		else if (e.getType() == MyEvent.MOVE_TIME) {
			Core.getInstance().getMoveList().getMove(getSelectionIndex()).setMoveTime(e.getValue());
			String st = Integer.toString(e.getValue());
			tableMoves.getItem(getSelectionIndex()).setText(0, st);
		}

		else if (e.getType() == MyEvent.LOAD_MOVELIST || e.getType() == MyEvent.TOTAL_TIME) {
			fillTableMoves();
		}

		else if (e.getType() == MyEvent.ADD_MOVE) {
			table_Index = e.getValue();
			fillTableMoves();
			tableMoves.setSelection(table_Index);
		}

		else if (e.getType() == MyEvent.DELETE_MOVE) {
			table_Index = e.getValue();
			fillTableMoves();
			tableMoves.setSelection(table_Index);
		}
	}

	private int getSelectionIndex() {
		int index = table_Index;
		if (index == -1)
			index = 0;
		return index;
	}

}
