/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc4990.robot.subsystems.DriveTrain;
import frc4990.robot.subsystems.F310Gamepad;
import frc4990.robot.subsystems.HatchClaw;
import frc4990.robot.subsystems.Pneumatic;
import frc4990.robot.subsystems.TalonMotorController;
import frc4990.robot.subsystems.TalonWithMagneticEncoder;
import frc4990.robot.subsystems.Turret;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {

	public static PowerDistributionPanel pdp;

	public static F310Gamepad driveGamepad;
	public static F310Gamepad opGamepad;
	public static TalonWithMagneticEncoder leftFrontDriveTalon;
	public static TalonMotorController leftRearDriveTalon;
	public static TalonWithMagneticEncoder rightFrontDriveTalon;
	public static TalonMotorController rightRearDriveTalon;

	public static SpeedControllerGroup leftMotorGroup;
	public static SpeedControllerGroup rightMotorGroup;

	public static DriveTrain driveTrain;

	public static AHRS ahrs;

	public static Turret turret;
	public static TalonWithMagneticEncoder turretTalon;

	public static HatchClaw hatchClaw;
	public static Pneumatic hatchPneumatic;
	public static TalonMotorController hatchMotor;

	public static Pneumatic frontSolenoid;
	public static Pneumatic rearSolenoid;
	public static Compressor compressor;
	
	public static DigitalInput robotSelector;

	public static DigitalInput turretSensorLeft;
	public static DigitalInput turretSensorMiddle;
	public static DigitalInput turretSensorRight;

	public RobotMap() {

		robotSelector = new DigitalInput(9); //true = practice bot, false = competition bot

		if (robotSelector.get()) { //practice bot

			pdp = new PowerDistributionPanel();

			driveGamepad = new F310Gamepad(0);
			opGamepad = new F310Gamepad(1);

			leftFrontDriveTalon = new TalonWithMagneticEncoder(22);
			leftRearDriveTalon = new TalonMotorController(9);
			rightFrontDriveTalon = new TalonWithMagneticEncoder(6);
			rightRearDriveTalon = new TalonMotorController(21);

			leftMotorGroup = new SpeedControllerGroup(leftFrontDriveTalon, leftRearDriveTalon);
			rightMotorGroup = new SpeedControllerGroup(rightFrontDriveTalon, rightRearDriveTalon);

			driveTrain = new DriveTrain();

			ahrs = new AHRS(SPI.Port.kMXP);

			//navX-MXP RoboRIO extension and 9-axis gyro thingy
			//for simple gyro angles: use ahrs.getAngle() to get heading (returns number -n to n) and reset() to reset angle (and drift)

			frontSolenoid = new Pneumatic(0, 0);
			rearSolenoid = new Pneumatic(0, 1);
			compressor = new Compressor(0);

			turret = new Turret();
			turretTalon = new TalonWithMagneticEncoder(30);

			hatchPneumatic = new Pneumatic(0, 3);
			hatchClaw = new HatchClaw();
			hatchMotor = new TalonMotorController(31);

			turretSensorLeft = new DigitalInput(0);
			turretSensorMiddle = new DigitalInput(1);
			turretSensorRight = new DigitalInput(2);

		} else { //competition bot

			pdp = new PowerDistributionPanel();

			driveGamepad = new F310Gamepad(0);
			opGamepad = new F310Gamepad(1);

			leftFrontDriveTalon = new TalonWithMagneticEncoder(1);
			leftRearDriveTalon = new TalonMotorController(2);
			rightFrontDriveTalon = new TalonWithMagneticEncoder(3);
			rightRearDriveTalon = new TalonMotorController(4);

			leftMotorGroup = new SpeedControllerGroup(leftFrontDriveTalon, leftRearDriveTalon);
			rightMotorGroup = new SpeedControllerGroup(rightFrontDriveTalon, rightRearDriveTalon);
			
			driveTrain = new DriveTrain();

			ahrs = new AHRS(SPI.Port.kMXP);
			//navX-MXP RoboRIO extension and 9-axis gyro thingy
			//for simple gyro angles: use ahrs.getAngle() to get heading (returns number -n to n) and reset() to reset angle (and drift)

			frontSolenoid = new Pneumatic(0, 0);
			rearSolenoid = new Pneumatic(0, 1);
			compressor = new Compressor(0);
      
      		turret = new Turret();
			turretTalon = new TalonWithMagneticEncoder(30);

			hatchPneumatic = new Pneumatic(0, 3);
			hatchClaw = new HatchClaw();
			hatchMotor = new TalonMotorController(31);

			turretSensorLeft = new DigitalInput(0);
			turretSensorMiddle = new DigitalInput(1);
			turretSensorRight = new DigitalInput(2);
		}
	}
}
