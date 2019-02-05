/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc4990.robot.components.F310Gamepad;
import frc4990.robot.components.TalonWithMagneticEncoder;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.DriveTrain;
import frc4990.robot.subsystems.Pneumatic;



/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {
	
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	public static Compressor compressor = new Compressor(0);

	public static F310Gamepad driveGamepad = new F310Gamepad(0);
	public static F310Gamepad opGamepad = new F310Gamepad(1);

	public static TalonWithMagneticEncoder leftFrontDriveTalon;
	public static WPI_TalonSRX leftRearDriveTalon;
	public static TalonWithMagneticEncoder rightFrontDriveTalon;
	public static WPI_TalonSRX rightRearDriveTalon;

	public static SpeedControllerGroup leftMotorGroup;
	public static SpeedControllerGroup rightMotorGroup;

	public static Pneumatic frontSolenoid = new Pneumatic(0, 0);
	public static Pneumatic rearSolenoid = new Pneumatic(0, 1);
	public static DriveTrain driveTrain = new DriveTrain();
	public static Dashboard dashboard = new Dashboard();

	public static DigitalInput robotSelector = new DigitalInput(9); //true = practice bot, false = competition bot

	public RobotMap() {

		if (robotSelector.get()) { //practice bot

			leftFrontDriveTalon = new TalonWithMagneticEncoder(22);
			leftRearDriveTalon = new WPI_TalonSRX(9);
			rightFrontDriveTalon = new TalonWithMagneticEncoder(6);
			rightRearDriveTalon = new WPI_TalonSRX(21);

		} else { //competition bot

			leftFrontDriveTalon = new TalonWithMagneticEncoder(1);
			leftRearDriveTalon = new WPI_TalonSRX(2);
			rightFrontDriveTalon = new TalonWithMagneticEncoder(3);
			rightRearDriveTalon = new WPI_TalonSRX(4);

		}

		leftMotorGroup = new SpeedControllerGroup(leftFrontDriveTalon, leftRearDriveTalon);
		rightMotorGroup = new SpeedControllerGroup(rightFrontDriveTalon, rightRearDriveTalon);
	}
}
