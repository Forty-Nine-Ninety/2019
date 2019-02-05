package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.TeleopDriveTrainController;

public class DriveTrain extends Subsystem implements PIDSource {

	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	public DifferentialDrive differentialDrive = new DifferentialDrive(RobotMap.leftMotorGroup, RobotMap.rightMotorGroup);

	private double leftSpeedAdjust = 1.0;
	private double rightSpeedAdjust = 0.99;

	/**
	 * Includes 4 driving motors and 2 encoders; all specified as static objects in RobotMap.
	 * 
	 * @author Class of '21 (created in 2018 season)
	 */
	public DriveTrain() {
		configOpenloopRamp();
		differentialDrive.setExpiration(0.3); //sets motor safety to 0.3 seconds. This is a band-aid for the larger issue of the main thread taking more than 20ms to execute.
	}

	/**
	 * Configures the open-loop ramp rate of throttle output.
	 * @param secondsFronNeutralToFull Minimum desired time to go from neutral to full throttle. A value of '0' will disable the ramp.
	 * @see CTRE's Talon.configOpenloopRamp(double, double);
	 */

	public void configOpenloopRamp(double secondsFronNeutralToFull) {
		RobotMap.leftFrontDriveTalon.configOpenloopRamp(secondsFronNeutralToFull, 0);
		RobotMap.leftRearDriveTalon.configOpenloopRamp(secondsFronNeutralToFull, 0);
		RobotMap.rightFrontDriveTalon.configOpenloopRamp(secondsFronNeutralToFull, 0);
		RobotMap.rightRearDriveTalon.configOpenloopRamp(secondsFronNeutralToFull, 0);
	} 

	/**
	 * Configures the open-loop ramp rate of throttle output to the default value. As of 1/25/19, it's 0.3.
	 */

	public void configOpenloopRamp() {
		configOpenloopRamp(Dashboard.getConst("DriveTrain/rampDownTime", 0.3));
	} 

	public void clearStickyFaults() {
		RobotMap.leftFrontDriveTalon.clearStickyFaults(0);
		RobotMap.leftRearDriveTalon.clearStickyFaults(0);
		RobotMap.rightFrontDriveTalon.clearStickyFaults(0);
		RobotMap.rightRearDriveTalon.clearStickyFaults(0);
	}

	/**
	 * Sets speed of all drive train motors.
	 * 
	 * @param speed
	 *            speed to set, min -1, max 1 (stop is 0)
	 */

	public void setSpeed(double speed) {
		this.setSpeed(speed, speed);
	}

	/**
	 * Sets speed of all drive train motors.
	 * 
	 * @param leftSpeed
	 *            speed to set, min -1, max 1 (stop is 0)
	 * @param rightSpeed
	 *            speed to set, min -1, max 1 (stop is 0)
	 */

	public void setSpeed(double leftSpeed, double rightSpeed) {
		differentialDrive.tankDrive(leftSpeed * leftSpeedAdjust, rightSpeed * rightSpeedAdjust, false);
	}

	public void arcadeDrive(double xSpeed, double zRotation, Boolean squareInputs) {
		differentialDrive.arcadeDrive(xSpeed, zRotation, squareInputs);
	}

	public void curvatureDrive(double xSpeed, double zRotation, Boolean squareInputs) {
		differentialDrive.curvatureDrive(xSpeed, zRotation, squareInputs);
	}

	@Override
	public void periodic() {

	}

	/**
	 * Resets left and right encoder distances.
	 */

	public void resetDistanceTraveled() {
		RobotMap.leftFrontDriveTalon.resetEncoder();
		RobotMap.rightFrontDriveTalon.resetEncoder();
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new TeleopDriveTrainController());
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
	}

	/**
	 * Returns raw average left/right encoder value, in unknown units. Use pidGet()
	 * to return distance in feet.
	 */

	public double getEncoderDistance() {
		return (RobotMap.leftFrontDriveTalon.getPosition() * RobotMap.rightFrontDriveTalon.getPosition()) / 2;
	}
	
	/**
	 * Returns raw average left/right encoder value, in unknown units. Use pidGet()
	 * to return distance in feet.
	 */

	public double getEncoderRate() {
		return (RobotMap.leftFrontDriveTalon.getRate() * RobotMap.rightFrontDriveTalon.getRate()) / 2;
	}

	/**
	 * Returns right encoder value, in feet.
	 */
	public double pidGet() {
		if (pidSourceType == PIDSourceType.kDisplacement) {
			return getEncoderDistance();
		} else {
			return getEncoderRate();
		}
	}

}
