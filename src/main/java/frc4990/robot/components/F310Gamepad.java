package frc4990.robot.components;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
public class F310Gamepad extends Joystick {

	public static enum POV {
		north(0), northWest(45), west(90), 
		southWest(135), south(180), southEast(225), 
		east(270), northEast(315);

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
		start(8), back(9), 
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

	public enum Axis {
		leftTrigger(2), rightTrigger(3),
		leftJoystickX(0), leftJoystickY(1),
		rightJoystickX(4), rightJoystickY(5);

		private int value;
    
        Axis(int value) {
            this.value = value;
        }
    
        public int get() {
            return value;
        }
	}

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

	public JoystickAnalogButton getAxis(Axis axis) {
		switch (axis) {
			case leftJoystickY:  return new JoystickAnalogButton(this, 1, 0.0078126, true);
			case leftTrigger:    return new JoystickAnalogButton(this, 2, 0.95);
			case rightTrigger:   return new JoystickAnalogButton(this, 2, 0.95);
			case rightJoystickX: return new JoystickAnalogButton(this, 4, 0.0391);
			default: return new JoystickAnalogButton(this, axis.get());
	}}

	public Double getRawAxis(Axis axis) {
		return getAxis(axis).getRawAxis();
	}

	public Boolean getBooleanAxis(Axis axis) {
		return getAxis(axis).get();
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

	public POVButton getPOVButton(POV pov) {
		return new POVButton(this, pov.get());
	}
}
