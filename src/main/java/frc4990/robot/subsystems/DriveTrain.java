package frc4990.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.TeleopDriveTrainController;

public class DriveTrain extends Subsystem implements PIDSource {

	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	public DifferentialDrive differentialDrive = new DifferentialDrive(RobotMap.leftMotorGroup, RobotMap.rightMotorGroup);

	public double leftSpeedAdjust = 1;//(RobotMap.robotSelector.get()) ? 0.88 : 1.0;
	public double rightSpeedAdjust = 1;//(RobotMap.robotSelector.get()) ? 1.0 : 0.85;

	/**
	 * Includes 4 driving motors and 2 encoders; all specified as static objects in RobotMap.
	 * 
	 * @author Class of '21 (created in 2018 season)
	 */
	public DriveTrain() {
		//configOpenloopRamp();
		configDifferentialDrive();
		configTalonPID();
	}

	private void configTalonPID() {
		RobotMap.leftFrontDriveTalon.configFactoryDefault();
		RobotMap.rightFrontDriveTalon.configFactoryDefault();

		RobotMap.leftFrontDriveTalon.setInverted(false);
		RobotMap.rightFrontDriveTalon.setInverted(false);
		RobotMap.leftFrontDriveTalon.setSensorPhase(true);
		RobotMap.rightFrontDriveTalon.setSensorPhase(true);

		RobotMap.rightFrontDriveTalon.configNeutralDeadband(0.001, 5);
		RobotMap.leftFrontDriveTalon.configNeutralDeadband(0.001, 5);

		RobotMap.leftFrontDriveTalon.configPeakOutputForward(+1.0, 5);
		RobotMap.leftFrontDriveTalon.configPeakOutputReverse(-1.0, 5);
		RobotMap.rightFrontDriveTalon.configPeakOutputForward(+1.0, 5);
		RobotMap.rightFrontDriveTalon.configPeakOutputReverse(-1.0, 5);

		RobotMap.rightFrontDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 5);
		RobotMap.leftFrontDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 5);

		RobotMap.rightFrontDriveTalon.configClosedloopRamp(0.3, 5);
		RobotMap.leftFrontDriveTalon.configClosedloopRamp(0.3, 5);

		RobotMap.rightFrontDriveTalon.selectProfileSlot(0, 0);
		RobotMap.rightFrontDriveTalon.config_kP(0, 0, 5); //TODO: Add kP
		RobotMap.rightFrontDriveTalon.config_kI(0, 0, 5); //TODO: Add kI
		RobotMap.rightFrontDriveTalon.config_kD(0, 0, 5); //TODO: Add kD
		RobotMap.rightFrontDriveTalon.config_kF(0, 0.25, 5); //TODO: Add kF
		RobotMap.rightFrontDriveTalon.config_IntegralZone(0, 50, 5); //TODO: Add IZone
		RobotMap.rightFrontDriveTalon.configClosedLoopPeakOutput(0, 1, 5); //TODO: Add peak output
		RobotMap.rightFrontDriveTalon.configAllowableClosedloopError(0, 0, 5); //TODO: Add allowable error

		RobotMap.leftFrontDriveTalon.selectProfileSlot(0, 0);
		RobotMap.leftFrontDriveTalon.config_kP(0, 0, 5); //TODO: Add kP
		RobotMap.leftFrontDriveTalon.config_kI(0, 0, 5); //TODO: Add kI
		RobotMap.leftFrontDriveTalon.config_kD(0, 0, 5); //TODO: Add kD
		RobotMap.leftFrontDriveTalon.config_kF(0, 0.25, 5); //TODO: Add kF
		RobotMap.leftFrontDriveTalon.config_IntegralZone(0, 50, 5); //TODO: Add IZone
		RobotMap.leftFrontDriveTalon.configClosedLoopPeakOutput(0, 1, 5); //TODO: Add peak output
		RobotMap.leftFrontDriveTalon.configAllowableClosedloopError(0, 0, 5); //TODO: Add allowable error

		RobotMap.leftRearDriveTalon.follow(RobotMap.leftFrontDriveTalon);
		RobotMap.rightRearDriveTalon.follow(RobotMap.rightFrontDriveTalon);
	}

	private void configDifferentialDrive() {
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

	public static void setSpeed(double speed) {
		DriveTrain.setSpeed(speed, speed);
	}

	/**
	 * Sets speed of all drive train motors.
	 * 
	 * @param leftSpeed
	 *            speed to set, min -1, max 1 (stop is 0)
	 * @param rightSpeed
	 *            speed to set, min -1, max 1 (stop is 0)
	 */

	public static void setSpeed(double leftSpeed, double rightSpeed) {
		RobotMap.driveTrain.differentialDrive.tankDrive(leftSpeed * RobotMap.driveTrain.leftSpeedAdjust, rightSpeed * RobotMap.driveTrain.rightSpeedAdjust, false);
	}

	public static void arcadeDrive(double xSpeed, double zRotation, Boolean squareInputs) {
		RobotMap.driveTrain.differentialDrive.arcadeDrive(xSpeed, zRotation, squareInputs);
	}

	public static void curvatureDrive(double xSpeed, double zRotation, Boolean squareInputs) {
		RobotMap.driveTrain.differentialDrive.curvatureDrive(xSpeed, zRotation, squareInputs);
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

	public static double getEncoderDistance() {
		return (RobotMap.leftFrontDriveTalon.getPosition() + RobotMap.rightFrontDriveTalon.getPosition()) / 2;
	}
	
	/**
	 * Returns raw average left/right encoder value, in unknown units. Use pidGet()
	 * to return distance in feet.
	 */

	public static double getEncoderRate() {
		return (RobotMap.leftFrontDriveTalon.getRate() + RobotMap.rightFrontDriveTalon.getRate()) / 2;
	}

	/**
	 * Returns averaged encoder value, in feet.
	 */
	public double pidGet() {
		if (pidSourceType == PIDSourceType.kDisplacement) {
			return getEncoderDistance();
		} else {
			return getEncoderRate();
		}
	}

}
