package frc4990.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.TeleopDriveTrainController;

public class DriveTrain extends Subsystem implements PIDSource {

	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	public DifferentialDrive differentialDrive = new DifferentialDrive(new SpeedControllerGroup(RobotMap.leftFrontDriveTalon, RobotMap.leftRearDriveTalon), new SpeedControllerGroup(RobotMap.rightFrontDriveTalon, RobotMap.rightRearDriveTalon));

	public double leftSpeedAdjust = (RobotMap.robotSelector.get()) ? 0.88 : 1.0;
	public double rightSpeedAdjust = (RobotMap.robotSelector.get()) ? 1.0 : 0.85;

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
		RobotMap.leftFrontDriveTalon.configSelectedFeedbackSensor(	FeedbackDevice.QuadEncoder,	0, 5);
		RobotMap.rightFrontDriveTalon.configRemoteFeedbackFilter(RobotMap.rightFrontDriveTalon.getDeviceID(), RemoteSensorSource.TalonSRX_SelectedSensor, 0, 5);
		RobotMap.rightFrontDriveTalon.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0, 5);
		RobotMap.rightFrontDriveTalon.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, 5);
		RobotMap.rightFrontDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, 1, 5);
		//TODO: change '51711' to measured value for this:
		/* Empirically measure what the difference between encoders per 360'
	 	* Drive the robot in clockwise rotations and measure the units per rotation.
	 	* Drive the robot in counter clockwise rotations and measure the units per rotation.
	 	* Take the average of the two.
		*/
		RobotMap.rightFrontDriveTalon.configSelectedFeedbackCoefficient(3600 / 51711, 1, 5);
		//TODO: Fix sensor phase issues
		RobotMap.leftFrontDriveTalon.setInverted(false);
		RobotMap.leftFrontDriveTalon.setSensorPhase(true);
		RobotMap.rightFrontDriveTalon.setInverted(true);
		RobotMap.rightFrontDriveTalon.setSensorPhase(true);

		RobotMap.rightFrontDriveTalon.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 5);
		RobotMap.rightFrontDriveTalon.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 5);
		RobotMap.leftFrontDriveTalon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 5);		//Used remotely by right Talon, speed up

		RobotMap.rightFrontDriveTalon.configNeutralDeadband(0.001, 5);
		RobotMap.leftFrontDriveTalon.configNeutralDeadband(0.001, 5);

		RobotMap.leftFrontDriveTalon.configPeakOutputForward(+1.0, 5);
		RobotMap.leftFrontDriveTalon.configPeakOutputReverse(-1.0, 5);
		RobotMap.rightFrontDriveTalon.configPeakOutputForward(+1.0, 5);
		RobotMap.rightFrontDriveTalon.configPeakOutputReverse(-1.0, 5);

		RobotMap.rightFrontDriveTalon.config_kP(1, 0, 5); //TODO: Add kP
		RobotMap.rightFrontDriveTalon.config_kI(1, 0, 5); //TODO: Add kI
		RobotMap.rightFrontDriveTalon.config_kD(1, 0, 5); //TODO: Add kD
		RobotMap.rightFrontDriveTalon.config_kF(1, 0.25, 5); //TODO: Add kF
		RobotMap.rightFrontDriveTalon.config_IntegralZone(1, 50, 5); //TODO: Add IZone
		RobotMap.rightFrontDriveTalon.configClosedLoopPeakOutput(1, 1, 5); //TODO: Add peak output
		RobotMap.rightFrontDriveTalon.configAllowableClosedloopError(1, 0, 5); //TODO: Add allowable error

		/* configAuxPIDPolarity(boolean invert, int timeoutMs)
		 * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
		 * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
		 */
		RobotMap.rightFrontDriveTalon.configAuxPIDPolarity(false, 5);
	}

	private void configDifferentialDrive() {
		differentialDrive.setExpiration(0.3); //sets motor safety to 0.3 seconds. This is a band-aid for the larger issue of the main thread taking more than 20ms to execute.
		differentialDrive.setRightSideInverted(false);
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
