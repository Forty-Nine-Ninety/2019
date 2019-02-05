package frc4990.robot.components;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * based on https://gist.github.com/jcorcoran/5743806
 * 
 * @author Benjamin
 */
	
	public class JoystickButtonGroup extends Button {
		Button[] buttons;

		/**
		 * Create a group of buttons. Must have at least 2 buttons.
		 */
		public JoystickButtonGroup(Button... buttons) {
			if (buttons.length < 2) {
				this.close();
			} else {
				this.buttons = (Button[]) buttons;
			}
		}
		
		/**
		 * Create a group of buttons. Must have at least 2 buttons.
		 */
		public JoystickButtonGroup(JoystickAnalogButton... buttons) {
			if (buttons.length < 2) {
				this.close();
			} else {
				this.buttons = buttons;
			}
		}

		/**
		 * Returns boolean value of analog button.
		 * @return boolean value of button
		 */

		public boolean get() {
			int i = 0; 
			while(i < buttons.length) {
				if (!buttons[i].get()) {
					return false;
				}
				i++;
			}
			return true;
		}
	}