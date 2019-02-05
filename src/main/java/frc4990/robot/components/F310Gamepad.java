package frc4990.robot.components;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
public class F310Gamepad extends Joystick {

	public enum POV {
		north(0), northWest(45), west(90), 
		southWest(135), south(180), southEast(-135), 
		east(-90), northEast(-45), none(0);

		private int value;
    
        POV(int value) {
            this.value = value;
        }
    
        public int get() {
            return value;
        }
	}

	public enum Buttons {
		a(1), b(2), x(3), y(4), 
		leftBumper(5), rightBumper(6),
		start(10), back(9), 
		rightJoystickButton(12), 
		leftJoystickButton(11);

		private int value;
    
        Buttons(int value) {
            this.value = value;
        }
    
        public int get() {
            return value;
        }
	}

	/*
	 * the following methods provide semantic sugar over the raw axis and button
	 * inputs from the F310 Gamepad, as discovered by using the FRC Driver Station
	 * software.
	 */

	public Button a = new JoystickButton(this, 1);
	public Button b = new JoystickButton(this, 2);
	public Button x = new JoystickButton(this, 3);
	public Button y = new JoystickButton(this, 4);
	public Button leftBumper = new JoystickButton(this, 5);
	public Button rightBumper = new JoystickButton(this, 6);
	public Button start = new JoystickButton(this, 10);
	public Button back = new JoystickButton(this, 9);
	public Button rightJoystickButton = new JoystickButton(this, 12);
	public Button leftJoystickButton = new JoystickButton(this, 11);

	/*
	* Use <JoystickAnalogButton>.get() to get boolean value.
	* Use <JoystickAnalogButton>.getRawAxis() to get double value.
	*/
	public JoystickAnalogButton leftTrigger = new JoystickAnalogButton(this, 2, 0.95);
	public JoystickAnalogButton rightTrigger = new JoystickAnalogButton(this, 3, 0.95);
	public JoystickAnalogButton leftJoystickX = new JoystickAnalogButton(this, 0);
	public JoystickAnalogButton leftJoystickY = new JoystickAnalogButton(this, 1, 0.0078126, true);
	public JoystickAnalogButton rightJoystickX = new JoystickAnalogButton(this, 4, 0.0391);
	public JoystickAnalogButton rightJoystickY = new JoystickAnalogButton(this, 5);

	/**
	 * Initializes new F310 Gamepad
	 * @param joystickNumber From drive station port number. (0 to 3?) BASED ON WHEN PLUGGED IN, not specific ports.
	 */
	public F310Gamepad(int joystickNumber) {
		super(joystickNumber);
	}

	public Boolean isButtonPressed(int button) {
		return this.getRawButton(button);
	}

	public Boolean isButtonPressed(Buttons button) {
		return this.getRawButton(button.get());
	}

	public JoystickButton getButton(Buttons button) {
		return new JoystickButton(this, button.get());
	}

	public Boolean isPOVPressed(int pov) {
		return this.getPOV(pov) == pov;
	}

	public Boolean isPOVPressed(POV pov) {
		return this.getPOV(pov.get()) == pov.get();
	}

	public POVButton getPOVButton(int pov) {
		return new POVButton(this, pov);
	}
}