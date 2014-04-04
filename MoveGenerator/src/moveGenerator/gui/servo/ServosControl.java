package moveGenerator.gui.servo;

import java.util.ArrayList;
import java.util.List;

import manager.Config;
import manager.Core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import entities.ServoID;

/**
 * Integrate all the controls related with Servos.<br>
 * Load backgroud image and many ServoGroup as have been defined.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class ServosControl extends Composite {

	private Composite thisComposite;
	private List<ServoGroup> sgList = new ArrayList<ServoGroup>();

	public ServosControl(Composite parent, int style) {
		super(parent, style);
		this.thisComposite = this;
		initialize();
	}

	public void initialize() {

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		int middle_x = thisComposite.getBounds().width / 2;
		int middle_y = thisComposite.getBounds().height / 2;

		for (ServoID servo : Core.getInstance().getServoList().getServos()) {

			final ServoGroup sg = new ServoGroup(this, SWT.SHADOW_ETCHED_IN, servo);
			sg.setText(servo.getName());
			sg.setLocation(servo.getGuiPositon_x() + middle_x, servo.getGuiPositon_y() + middle_y);
			sg.pack();
			sgList.add(sg);
		}

		// PUT BACKGROUND IMAGE

		final Image image = new Image(getDisplay(), Config.getInstance().getGUI_ServosControl_Background());
		GC gc = new GC(image);
		gc.dispose();
		thisComposite.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				GC gc = event.gc;
				Rectangle rect = image.getBounds();

				gc.drawImage(image, (thisComposite.getBounds().width - rect.width) / 2,
						(thisComposite.getBounds().height - rect.height) / 2);

			}
		});

		this.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				int middle_x = thisComposite.getBounds().width / 2;
				int middle_y = thisComposite.getBounds().height / 2;

				int i = 0;
				for (ServoID servo : Core.getInstance().getServoList().getServos()) {
					sgList.get(i).setLocation(servo.getGuiPositon_x() + middle_x, servo.getGuiPositon_y() + middle_y);
					sgList.get(i).pack();
					i++;
				}
			}
		});
	}
}