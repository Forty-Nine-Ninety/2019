/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4990.robot;

import org.usfirst.frc.team4990.robot.commands.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * @author Class of '21 (created in 2018 season)
 */
public class OI{
	
	public static JoystickAnalogButton throttleAnalogButton = RobotMap.driveGamepad.leftJoystickY;
	public static JoystickAnalogButton turnSteepnessAnalogButton = RobotMap.driveGamepad.rightJoystickX;

	public static Button driveSpeedToggle = RobotMap.driveGamepad.x;
	public static Button turnSpeedToggle = RobotMap.driveGamepad.b;
	public static Button driveControllerCheck = RobotMap.driveGamepad.start;
	public static Button opControllerCheck = RobotMap.opGamepad.start;
	
	/* CREATING BUTTONS
	One type of button is a joystick button which is any button on a
	joystick.
	You create one by telling it which joystick it's on and which button
	number it is.
	Joystick stick = new Joystick(port);
	Button button = new JoystickButton(stick, buttonNumber);

	There are a few additional built in buttons you can use. Additionally,
	by subclassing Button you can create custom triggers and bind those to
	commands the same as any other Button.

	//TRIGGERING COMMANDS WITH BUTTONS
	Once you have a button, it's trivial to bind it to a button in one of
	three ways:

	Start the command when the button is pressed and let it run the command
	until it is finished as determined by it's isFinished method.
	button.whenPressed(new ExampleCommand());

	Run the command while the button is being held down and interrupt it once
	the button is released.
	button.whileHeld(new ExampleCommand());

	Start the command when the button is released and let it run the command
	until it is finished as determined by it's isFinished method.
	button.whenReleased(new ExampleCommand());
	*/
	
	/*
		Drive Train: (drive controller)
		    Joysticks 1 and 2: forward/backward and turn left/right
		    X button: toggle slow mode
		
		Check which controller is which: (both)
		    START key (RIGHT Middle): prints in console which controller it is being pressed on
	 */
	
	public OI() {

		//drivetrain
		driveSpeedToggle.toggleWhenPressed(new DriveSpeedToggle());
		//default command is (standard) joystick drive
		turnSpeedToggle.toggleWhenPressed(new TurnSpeedToggle());
		
		//controller check
		driveControllerCheck.toggleWhenPressed(new ControllerCheck(RobotMap.driveGamepad));
		opControllerCheck.toggleWhenPressed(new ControllerCheck(RobotMap.opGamepad));
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
		public double getRawAxis() {
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
				this.free();
			} else {
				this.buttons = (Button[]) buttons;
			}
		}
		
		/**
		 * Create a group of buttons. Must have at least 2 buttons.
		 */
		public JoystickButtonGroup(JoystickAnalogButton... buttons) {
			if (buttons.length < 2) {
				this.free();
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
	
	
}
