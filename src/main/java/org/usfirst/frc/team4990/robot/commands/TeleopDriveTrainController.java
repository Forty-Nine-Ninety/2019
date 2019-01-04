package org.usfirst.frc.team4990.robot.commands;

import java.util.Date;

import org.usfirst.frc.team4990.robot.OI;
import org.usfirst.frc.team4990.robot.RobotMap;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
/**
 * Class for controlling drivetrains.
 * @author Class of '21 (created in 2018 season)
 */
public class TeleopDriveTrainController extends Command{
	
	public enum DriveMode { STRAIGHT, ARC, TURN, NONE }
	
	public DriveMode driveMode;	
	
	public enum StickShapingMode { NextThrottle, SquaredThrottle, DifferentialDrive }
	
	public static StickShapingMode stickShapingMode = StickShapingMode.SquaredThrottle;	

	public static double currentThrottleMultiplier = 1.0;

	public static double currentTurnSteepnessMultiplier = 1.0;

	private double throttle, turnSteepness;

	//for older StickShapingMode (NextThrottle)
	 private double lastThrottle, lastTurnSteepness;
	 private Date currentUpdate, lastUpdate;
	 private double accelerationTime = 250;

	/**
	 * Constructor for TeleopDriveTrainController
	 * @author Class of '21 (created in 2018 season)
	 */
	public TeleopDriveTrainController() {
		requires(RobotMap.driveTrain);
	}
	
	/**
	 * Updates class variable values and sets motor speeds
	 * @author Class of '21 (created in 2018 season)
	 */
	public void execute() {

		switch (stickShapingMode) {
			case NextThrottle:
				currentUpdate = new Date();
			
				throttle = getNextThrottle(
					OI.throttleAnalogButton.getRawAxis() * currentThrottleMultiplier, 
					this.lastThrottle, 
					this.lastUpdate, 
					currentUpdate, 
					this.accelerationTime);
			
				turnSteepness = getNextThrottle(
					OI.turnSteepnessAnalogButton.getRawAxis() * currentThrottleMultiplier,
					this.lastTurnSteepness,
					this.lastUpdate,
					currentUpdate,
					this.accelerationTime);
				break;
			
			case SquaredThrottle:
				throttle = getSquaredThrottle(OI.throttleAnalogButton.getRawAxis() * currentThrottleMultiplier);
				turnSteepness = getSquaredThrottle(OI.turnSteepnessAnalogButton.getRawAxis());
				break;
			
			case DifferentialDrive:
				break;

		}
		
		if (throttle != 0 && turnSteepness != 0) { //arc turn
			driveMode = DriveMode.ARC;
			if (stickShapingMode != StickShapingMode.DifferentialDrive) {
				setArcTrajectory(throttle, -turnSteepness);
			} else {
				//differentialDrive arc turning
			}
			
		} else if (throttle != 0 && turnSteepness == 0) { //go forward
			if (driveMode != DriveMode.STRAIGHT) { //changed modes
				RobotMap.ahrs.reset();
			}
			driveMode = DriveMode.STRAIGHT;
			RobotMap.driveTrain.setSpeed(throttle, throttle);
			
		} else if (throttle == 0 && turnSteepness != 0) { //spin in place
			/* the right motor's velocity has the opposite sign of the the left motor's
			 * since the right motor will spin in the opposite direction from the left
			 */
			driveMode = DriveMode.TURN;
			RobotMap.driveTrain.setSpeed(turnSteepness * currentTurnSteepnessMultiplier, 
			-turnSteepness * currentTurnSteepnessMultiplier);
			
		} else {
			driveMode = DriveMode.NONE;
			RobotMap.driveTrain.setSpeed(0, 0);
		}
	}
	
	/**
	 * Squares the number provided and keeps sign (+ or -)
	 * @param throttleInput number to square and keeps sign
	 * @return squared number provided with same sign
	 */
	public double getSquaredThrottle(double throttleInput) {
		return throttleInput * throttleInput * Math.signum(throttleInput);
	}

	public double getNextThrottle(double throttleInput, double lastThrottle, Date lastUpdate, Date currentUpdate, double accelerationTime) {
		double newThrottle = throttleInput;
		
		if (accelerationTime != 0) {
			double acceleration = (throttleInput - lastThrottle) / accelerationTime;
			double deltaTime = currentUpdate.getTime() - lastUpdate.getTime();
			
			double deltaThrottle = deltaTime * acceleration;
			
			newThrottle = lastThrottle + deltaThrottle;
		}
		
		return Math.abs(newThrottle) < /*Constants.zeroThrottleThreshold*/0.01 ? 0.0 : newThrottle;
	}
	
	/**
	 * Sets motor for arc turns
	 * @author Class of '21 (created in 2018 season)
	 * @param throttle Value for left drivetrain
	 * @param turnSteepness How much the right drivetrain should turn compared to the left drivetrain
	 */
	public void setArcTrajectory(double throttle, double turnSteepness) {
		//without if statement; turns to the right
		
		double leftWheelSpeed = throttle;
		double rightWheelSpeed = calculateInsideWheelSpeed(throttle, turnSteepness);
		
		/* the robot should turn to the left, so left wheel is on the inside
		 * of the turn, and the right wheel is on the outside of the turn
		 */
		
		//goes forward and also runs this if reverse turning disabled
		boolean slowLeft;
		if (turnSteepness < 0) {
			if (throttle < 0) {
				slowLeft = true;
			} else {
				slowLeft = false;
			}
		}
		else {
			if (throttle < 0) {
				slowLeft = false;
			} else {
				slowLeft = true;
			}
		}
		if (slowLeft) {
			leftWheelSpeed = calculateInsideWheelSpeed(throttle,-turnSteepness);
			rightWheelSpeed = throttle;
		}
		
		//System.out.println(leftWheelSpeed + "; " + rightWheelSpeed);
		
		RobotMap.driveTrain.setSpeed(leftWheelSpeed, rightWheelSpeed);
	}
	
	/**
	 * Calculates "inside" wheel motor speed based on the "outside" wheel speed and turnSteepness
	 * @author Class of '21 (created in 2018 season) & Old Coders
	 * @param outsideWheelSpeed Speed of other wheel
	 * @param turnSteepness How much the inside wheels should turn compared to the outside wheels
	 * @return Calculated inside wheel speed
	 */
	private double calculateInsideWheelSpeed(double outsideWheelSpeed, double turnSteepness) {
		/*
		 * Basically, the larger the turnSteepness the smaller the power should be
		 * We also add a turnSensitivity constant to make the robot turn slower
		 */
		double newSteepness = turnSteepness * 0.9;
		double turnValue = (turnSteepness > 0) ? 1 - newSteepness : 1 + newSteepness;
		return outsideWheelSpeed * turnValue;
	}

	@Override
	protected boolean isFinished() {
		return !DriverStation.getInstance().isOperatorControl();
	}
	
	@Override
	protected void end() {
		RobotMap.driveTrain.setSpeed(0,0);
	}

}
