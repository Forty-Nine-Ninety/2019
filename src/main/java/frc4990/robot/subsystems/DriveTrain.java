package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc4990.robot.RobotMap;
import frc4990.robot.SmartDashboardController;
import frc4990.robot.commands.TeleopDriveTrainController;

public class DriveTrain extends Subsystem implements PIDSource {

	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	public DifferentialDrive differentialDrive = new DifferentialDrive(RobotMap.leftMotorGroup, RobotMap.rightMotorGroup);

	double rampDownTime = SmartDashboardController.getConst("DriveTrain/rampDownTime", 0.3);

	private double leftSpeedAdjust = 1.0;
	private double rightSpeedAdjust = 0.99;

	/**
	 * Includes 4 driving motors and 2 encoders; all specified as static objects in RobotMap.
	 * 
	 * @author Class of '21 (created in 2018 season)
	 */
	public DriveTrain() {
		RobotMap.leftFrontDriveTalon.configOpenloopRamp(rampDownTime, 0);
		RobotMap.leftRearDriveTalon.configOpenloopRamp(rampDownTime, 0);
		RobotMap.rightFrontDriveTalon.configOpenloopRamp(rampDownTime, 0);
		RobotMap.rightRearDriveTalon.configOpenloopRamp(rampDownTime, 0);
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
		RobotMap.leftEncoder.reset();
		RobotMap.rightEncoder.reset();
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
		return (RobotMap.leftEncoder.getDistance() * RobotMap.rightEncoder.getDistance()) / 2;
	}
	
	/**
	 * Returns raw average left/right encoder value, in unknown units. Use pidGet()
	 * to return distance in feet.
	 */

	public double getEncoderRate() {
		return (RobotMap.leftEncoder.getRate() * RobotMap.rightEncoder.getRate()) / 2;
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
