package frc4990.robot.components;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * see https://gist.github.com/jcorcoran/5743806
 * 
 * @author James
 */
	
	public class JoystickAnalogButton extends Button {
		
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