package moveGenerator.gui.servo;

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

import entities.Servo;
import entities.ServoID;

/**
 * Group of components to set Servo position <br>
 * Buttons: add / remove<br>
 * Textbox with Servo position <br>
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class ServoGroup extends Group implements GuiChangesListener {

	private ServoID servo;

	private Button button_decrease;
	private Text textBox;
	private Button button_increase;

	private static final int timerDelay = Config.getInstance().getServo_Button_TimeDelay();
	private static final int SLOW_INCREMENT = Config.getInstance().getServo_SlowIncrement();
	private static final int FAST_INCREMENT = Config.getInstance().getServo_FastIncrement();

	private Timer timer_increase, timer_decrease;

	public ServoGroup(Composite parent, int style, ServoID servo) {
		super(parent, style);
		this.servo = servo;

		MyEventStation.getInstance().addGuiChangesListener(this);

		initialize();
	}

	private Servo servo() {
		int index = Core.getInstance().getMove_index();
		Servo s = Core.getInstance().getMoveList().getMove(index).getServo(servo.getID());
		return s;
	}

	public void initialize() {

		this.setLayout(new GridLayout(3, false));

		button_decrease = new Button(this, SWT.PUSH);
		button_decrease.setText("-");
		button_decrease.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// # Execute when button is pressed
				servo().decreasePosition(SLOW_INCREMENT);
				MyEventStation.getInstance().updateView(servo.getID(), MyEvent.POSITION);
			}
		});
		button_decrease.addMouseListener(new MouseListener() {
			// # mouse down action
			public void mouseDown(MouseEvent e) {
				timer_decrease.start();
			}

			// # mouse up action
			public void mouseUp(MouseEvent e) {
				timer_decrease.stop();
			}

			// # doubleClick action
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		timer_decrease = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						servo().decreasePosition(FAST_INCREMENT);
						MyEventStation.getInstance().updateView(servo.getID(), MyEvent.POSITION);
					}
				});

			}
		});
		textBox = new Text(this, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.widthHint = 18;
		textBox.setLayoutData(gd);
		textBox.setTextLimit(3);
		textBox.setText(Integer.toString(servo.getPosition()));
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
			// # Do when focus Lost
			@Override
			public void focusLost(FocusEvent e) {
				servo().setPosition(Integer.parseInt(textBox.getText()));
				MyEventStation.getInstance().updateView(servo.getID(), MyEvent.POSITION);
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		textBox.addKeyListener(new KeyListener() {
			// # Do when keypress
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					// System.out.println(textBox.getText());
					servo().setPosition(Integer.parseInt(textBox.getText()));
					MyEventStation.getInstance().updateView(servo.getID(), MyEvent.POSITION);
				}
			}

			// # Do when keyreleases
			public void keyReleased(KeyEvent e) {

			}
		});

		button_increase = new Button(this, SWT.PUSH);
		button_increase.setText("+");
		button_increase.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// # Execute when button is pressed
				servo().increasePosition(SLOW_INCREMENT);
				MyEventStation.getInstance().updateView(servo.getID(), MyEvent.POSITION);
			}
		});

		button_increase.addMouseListener(new MouseListener() {
			// # mouse down action
			public void mouseDown(MouseEvent e) {
				timer_increase.start();
			}

			// # mouse up action
			public void mouseUp(MouseEvent e) {
				timer_increase.stop();
			}

			// # doubleClick action
			public void mouseDoubleClick(MouseEvent e) {

			}
		});

		timer_increase = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						// textBox.setText(String.valueOf(servo.decreasePosition(4)));
						servo().increasePosition(FAST_INCREMENT);
						MyEventStation.getInstance().updateView(servo.getID(), MyEvent.POSITION);
					}
				});

			}
		});

	}

	@Override
	public void checkSubclass() {

	}

	@Override
	public void updateView(MyEvent e) {

		if (e.getType() == MyEvent.MOVE_CHANGED || e.getType() == MyEvent.LOAD_MOVELIST
				|| e.getType() == MyEvent.DELETE_MOVE) {
			Servo s = Core.getInstance().getMoveList().getMove(e.getValue()).getServo(servo.getID());
			int pos = servo().setPosition(s.getPosition());
			textBox.setText(Integer.toString(pos));
		}

		else if (e.getType() == MyEvent.POSITION && e.getValue() == servo.getID()) {
			textBox.setText(Integer.toString(servo().getPosition()));
		}

	}

}