package moveGenerator.gui.move;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import manager.Config;
import manager.Core;
import moveGenerator.events.GuiChangesListener;
import moveGenerator.events.MyEvent;
import moveGenerator.events.MyEventStation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Group of components to set Total Move time <br>
 * Buttons: add / remove<br>
 * Textbox with Move time <br>
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MoveTimeGroup extends Group implements GuiChangesListener {

	private int time;

	private static final int MAX_TIME = Config.getInstance().getMoveTime_MaxTime();
	private static final int MIN_TIME = Config.getInstance().getMoveTime_MinTime();

	private static final int SLOW_INCREMENT = Config.getInstance().getMoveTime_SlowIncrement();
	private static final int FAST_INCREMENT = Config.getInstance().getMoveTime_FastIncrement();

	private int timerDelay = Config.getInstance().getMoveTime_Button_TimeDelay();

	private Button button_Remove;
	private Text textBox;
	private Button button_Add;

	private Timer timer_increase, timer_decrease;

	public MoveTimeGroup(Composite parent, int style) {
		super(parent, style);
		this.time = Core.getInstance().getMoveList().getMove(0).getMoveTime();

		MyEventStation.getInstance().addGuiChangesListener(this);
		initialize();
	}

	public void initialize() {

		this.setText("Move time");
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 3;
		this.setLayout(gridLayout2);

		button_Remove = new Button(this, SWT.PUSH);
		button_Remove.setText("-");
		button_Remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button is pressed
				timeDecrease(SLOW_INCREMENT);
			}
		});
		button_Remove.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				timer_decrease.start();
			}

			public void mouseUp(MouseEvent e) {
				timer_decrease.stop();
			}

			public void mouseDoubleClick(MouseEvent e) {
				// doubleClick action
			}
		});

		timer_decrease = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						timeDecrease(FAST_INCREMENT);
					}
				});

			}
		});

		textBox = new Text(this, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.widthHint = 30;
		textBox.setLayoutData(gd);
		textBox.setTextLimit(4);
		textBox.setText(Integer.toString(time));
		textBox.addListener(SWT.Verify, new Listener() {
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
		});
		textBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				setTime(Integer.parseInt(textBox.getText()));
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		textBox.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					// Execute when ENTER is pressed
					setTime(Integer.parseInt(textBox.getText()));
				}
			}

			public void keyReleased(KeyEvent e) {
				// Do when keyreleases
			}
		});

		button_Add = new Button(this, SWT.PUSH);
		button_Add.setText("+");
		button_Add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Execute when button is pressed
				timeIncrease(SLOW_INCREMENT);
			}
		});
		button_Add.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				timer_increase.start();
			}

			public void mouseUp(MouseEvent e) {
				timer_increase.stop();
			}

			public void mouseDoubleClick(MouseEvent e) {
				// doubleClick action
			}
		});
		timer_increase = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						timeIncrease(FAST_INCREMENT);
					}
				});

			}
		});

	}

	private void timeIncrease(int increment) {
		if ((time + increment) < MAX_TIME) {
			time = time + increment;
			textBox.setText(String.valueOf(time));
			MyEventStation.getInstance().updateView(time, MyEvent.MOVE_TIME);
		}
	}

	private void timeDecrease(int increment) {
		if ((time - increment) > MIN_TIME) {
			time = time - increment;
			textBox.setText(String.valueOf(time));
			MyEventStation.getInstance().updateView(time, MyEvent.MOVE_TIME);
		}
	}

	private void setTime(int newTime) {
		if (newTime > MIN_TIME && newTime < MAX_TIME && time != newTime) {
			time = newTime;
			textBox.setText(String.valueOf(time));
			MyEventStation.getInstance().updateView(time, MyEvent.MOVE_TIME);
		}
	}

	@Override
	public void updateView(MyEvent e) {
		if (e.getType() == MyEvent.MOVE_CHANGED || e.getType() == MyEvent.DELETE_MOVE
				|| e.getType() == MyEvent.LOAD_MOVELIST || e.getType() == MyEvent.TOTAL_TIME) {
			int move_index = e.getValue();
			time = Core.getInstance().getMoveList().getMove(move_index).getMoveTime();
			textBox.setText(String.valueOf(time));
		}

	}

	@Override
	public void checkSubclass() {

	}

}