/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.commands.*;
import frc4990.robot.commands.TeleopDriveTrainController.StickShapingMode;
import frc4990.robot.subsystems.Dashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author Class of '21 (created in 2018 season)
 */
public class OI{
	
	public static JoystickAnalogButton throttleAnalogButton = RobotMap.driveGamepad.leftJoystickY;
	public static JoystickAnalogButton turnSteepnessAnalogButton = RobotMap.driveGamepad.rightJoystickX;
	
	/* Controller Mapping:
		Drive Train: (drive controller)
		    Joysticks 1 and 2: forward/backward and turn left/right
			X button: toggle slow throttle
			B button: toggle slow turning
		
		Check which controller is which: (both)
		    START key (RIGHT Middle): prints in console which controller it is being pressed on
	 */
	
	public OI() {

		//drivetrain
		RobotMap.driveGamepad.x.toggleWhenPressed(new DriveSpeedToggle());
		RobotMap.driveGamepad.b.toggleWhenPressed(new TurnSpeedToggle());
		RobotMap.driveGamepad.y.whenPressed(new stickShapingToggle());

		
		//controller check
		RobotMap.driveGamepad.start.toggleWhenPressed(new InstantCommand("DriveControllerCheck", () -> System.out.println("START pressed on Drive Gamepad.")));
		RobotMap.opGamepad.start.toggleWhenPressed(new InstantCommand("OPControllerCheck", () -> System.out.println("START pressed on OP Gamepad.")));

		//Pneumatics
		//RobotMap.opGamepad.x.whenPressed(new TogglePneumatic(RobotMap.pneumatic1));
		//RobotMap.opGamepad.y.whenPressed(new TogglePneumatic(RobotMap.pneumatic2));
	}
	
	/**
	 * see https://gist.github.com/jcorcoran/5743806
	 * @author James
	 */
	
	public static class JoystickAnalogButton extends Button {
		
		GenericHID m_gamepad;
		int m_axisNumber;
		double m_threshold = 0;
		Boolean m_inverted = false;

		/**
		 * Create a button for triggering commands off a joystick's analog axis
		 * 
		 * @param gamepad
		 *            The GenericHID object that has the button (e.g. Joystick,
		 *            KinectStick, etc)
		 * @param axisNumber
		 *            The axis number
		 */
		public JoystickAnalogButton(GenericHID gamepad, int axisNumber) {
			m_gamepad = gamepad;
			m_axisNumber = axisNumber;
		}

		/**
		 * Create a button for triggering commands off a joystick's analog axis
		 * 
		 * @param joystick The GenericHID object that has the button (e.g. Joystick, KinectStick, etc)
		 * @param axisNumber The axis number
		 * @param threshold The threshold to trigger above (positive) or below (negative)
		 */
		public JoystickAnalogButton(GenericHID joystick, int axisNumber, double threshold) {
			this(joystick, axisNumber);
			m_threshold = Math.abs(threshold);
		}

		public JoystickAnalogButton(GenericHID joystick, int axisNumber, double threshold, Boolean inverted) {
			this(joystick, axisNumber, threshold);
			m_inverted = true;
		}
		/**
		 * Set the value above which triggers should occur (for positive thresholds)
		 *  or below which triggers should occur (for negative thresholds)
		 * The default threshold value is 0.5
		 *  
		 * @param threshold the threshold value (1 to -1)
		 */
		public void setThreshold(double threshold){
			m_threshold = Math.abs(threshold);
		}
		 
		/**
		 * Get the defined threshold value.
		 * @return the threshold value
		 */
		public double getThreshold(){
			return m_threshold;
		}

		/**
		 * @return the m_inverted
		 */
		public Boolean getInverted() {
			return m_inverted;
		}

		/**
		 * @param m_inverted the m_inverted to set
		 */
		public void setInverted(Boolean m_inverted) {
			this.m_inverted = m_inverted;
		}
		
		/**
		 * Returns boolean value of analog button.
		 * @return boolean value of button
		 */

		public boolean get() {
				return Math.abs(m_gamepad.getRawAxis(m_axisNumber)) > m_threshold;    //Return true if axis value is less than negative threshold
		}
		
		/**
		 * Returns double value of axis.
		 * @return double value of axis.
		 */
		public Double getRawAxis() {
			if (!m_inverted) { //not inverted
				return Math.abs(m_gamepad.getRawAxis(m_axisNumber)) > m_threshold ? 
				m_gamepad.getRawAxis(m_axisNumber) 
				: 0;
			} else { //inverted
				return Math.abs(m_gamepad.getRawAxis(m_axisNumber)) > m_threshold ?
				-m_gamepad.getRawAxis(m_axisNumber) 
				: 0;
			}
			
		}
	}
	
	/**
	 * based on https://gist.github.com/jcorcoran/5743806
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
	
	public class stickShapingToggle extends InstantCommand {

		public stickShapingToggle() {}
		
		public void initialize() {
			TeleopDriveTrainController.stickShapingMode = (TeleopDriveTrainController.stickShapingMode == StickShapingMode.DifferentialDrive) ? StickShapingMode.SquaredThrottle : StickShapingMode.DifferentialDrive;
			System.out.println("[StickShaping Method] Changed to:" + TeleopDriveTrainController.stickShapingMode.toString());
	
		}
		
	}

	public class DriveSpeedToggle extends InstantCommand {
		public void initialize() {
			TeleopDriveTrainController.currentThrottleMultiplier = TeleopDriveTrainController.currentThrottleMultiplier == 
				Dashboard.getConst("DriveDpiToggle/lowThrottleMultiplier", 0.5) ? 
				TeleopDriveTrainController.currentThrottleMultiplier = Dashboard.getConst("DriveDpiToggle/defaultThrottleMultiplier", 1.0) : 
				Dashboard.getConst("DriveDpiToggle/lowThrottleMultiplier", 0.5);
			System.out.println("Throttle Speed: " + TeleopDriveTrainController.currentThrottleMultiplier + "x");
		}
	}

	public class TurnSpeedToggle extends InstantCommand {
		public void initialize() {
			TeleopDriveTrainController.currentTurnSteepnessMultiplier = TeleopDriveTrainController.currentTurnSteepnessMultiplier == 
				Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier", 0.6) ? Dashboard.getConst("DriveDpiToggle/defaultTurnSpeedMultiplier", 1.0) : 
				Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier", 0.6);
			System.out.println("Turn Speed: " + TeleopDriveTrainController.currentTurnSteepnessMultiplier + "x");
		}
	}
}
