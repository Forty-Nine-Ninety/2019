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

	public boolean controlDisabled;

	public double leftSpeedAdjust = 1;//(RobotMap.robotSelector.get()) ? 0.88 : 1.0;
	public double rightSpeedAdjust = 1;//(RobotMap.robotSelector.get()) ? 1.0 : 0.85;

	/**
	 * Includes 4 driving motors and 2 encoders; all specified as static objects in RobotMap.
	 * 
	 * @author Class of '21 (created in 2018 season)
	 */
	public DriveTrain() {
		configOpenloopRamp();
		configDifferentialDrive();
		configTalonPID();
		controlDisabled = false;
	}

	private void configTalonPID() {
		RobotMap.leftFrontDriveTalon.configFactoryDefault();
		RobotMap.rightFrontDriveTalon.configFactoryDefault();

		RobotMap.leftFrontDriveTalon.setInverted(true);
		RobotMap.rightFrontDriveTalon.setInverted(true);
		RobotMap.leftRearDriveTalon.setInverted(true);
		RobotMap.rightRearDriveTalon.setInverted(true);
		RobotMap.leftFrontDriveTalon.setSensorPhase(false);
		RobotMap.rightFrontDriveTalon.setSensorPhase(false);

		RobotMap.rightFrontDriveTalon.configNeutralDeadband(0.001, RobotMap.TALON_TIMEOUT_MS);
		RobotMap.leftFrontDriveTalon.configNeutralDeadband(0.001, RobotMap.TALON_TIMEOUT_MS);

		RobotMap.leftFrontDriveTalon.configPeakOutputForward(+1.0, RobotMap.TALON_TIMEOUT_MS);
		RobotMap.leftFrontDriveTalon.configPeakOutputReverse(-1.0, RobotMap.TALON_TIMEOUT_MS);
		RobotMap.rightFrontDriveTalon.configPeakOutputForward(+1.0, RobotMap.TALON_TIMEOUT_MS);
		RobotMap.rightFrontDriveTalon.configPeakOutputReverse(-1.0, RobotMap.TALON_TIMEOUT_MS);

		RobotMap.rightFrontDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TALON_TIMEOUT_MS);
		RobotMap.leftFrontDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TALON_TIMEOUT_MS);

		//RobotMap.rightFrontDriveTalon.configClosedloopRamp(0.3, RobotMap.TALON_TIMEOUT_MS);
		//RobotMap.leftFrontDriveTalon.configClosedloopRamp(0.3, RobotMap.TALON_TIMEOUT_MS);

		RobotMap.rightFrontDriveTalon.selectProfileSlot(0, 0);
		RobotMap.rightFrontDriveTalon.config_kP(0, RobotMap.driveStraight_kP, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.rightFrontDriveTalon.config_kI(0, RobotMap.driveStraight_kI, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.rightFrontDriveTalon.config_kD(0, RobotMap.driveStraight_kD, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.rightFrontDriveTalon.config_kF(0, RobotMap.driveStraight_kF, RobotMap.TALON_TIMEOUT_MS);
		RobotMap.rightFrontDriveTalon.config_IntegralZone(0, RobotMap.driveStraight_IZone, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.rightFrontDriveTalon.configClosedLoopPeakOutput(0, RobotMap.driveStraight_PeakOutput, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.rightFrontDriveTalon.configAllowableClosedloopError(0, RobotMap.driveStraight_AllowableError, RobotMap.TALON_TIMEOUT_MS); 

		RobotMap.leftFrontDriveTalon.selectProfileSlot(0, 0);
		RobotMap.leftFrontDriveTalon.config_kP(0, RobotMap.driveStraight_kP, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.leftFrontDriveTalon.config_kI(0, RobotMap.driveStraight_kI, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.leftFrontDriveTalon.config_kD(0, RobotMap.driveStraight_kD, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.leftFrontDriveTalon.config_kF(0, RobotMap.driveStraight_kF, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.leftFrontDriveTalon.config_IntegralZone(0, RobotMap.driveStraight_IZone, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.leftFrontDriveTalon.configClosedLoopPeakOutput(0, RobotMap.driveStraight_PeakOutput, RobotMap.TALON_TIMEOUT_MS); 
		RobotMap.leftFrontDriveTalon.configAllowableClosedloopError(0, RobotMap.driveStraight_AllowableError, RobotMap.TALON_TIMEOUT_MS); 

		RobotMap.leftRearDriveTalon.follow(RobotMap.leftFrontDriveTalon);
		RobotMap.rightRearDriveTalon.follow(RobotMap.rightFrontDriveTalon);

		RobotMap.leftMotorGroup.coeff = (RobotMap.robotSelector.get()) ? RobotMap.driveStraight_practice_leftCoeff : RobotMap.driveStraight_comp_leftCoeff; //2600.0; //max = 3300
		RobotMap.rightMotorGroup.coeff = (RobotMap.robotSelector.get()) ? RobotMap.driveStraight_practice_rightCoeff : RobotMap.driveStraight_comp_rightCoeff; //2800.0; //max = 3100
	}

	private void configDifferentialDrive() {
		differentialDrive.setExpiration(RobotMap.differentialDriveExpiration); //sets motor safety to 0.3 seconds. This is a band-aid for the larger issue of the main thread taking more than 20ms to execute.
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
		configOpenloopRamp(RobotMap.rampDownTime);
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
