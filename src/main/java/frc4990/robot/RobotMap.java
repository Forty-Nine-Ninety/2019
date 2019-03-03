/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import frc4990.robot.components.F310Gamepad;
import frc4990.robot.components.TalonSRXGroup;
import frc4990.robot.components.TalonWithMagneticEncoder;
import frc4990.robot.components.TalonWithMagneticEncoder.SensorMode;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.DriveTrain;
import frc4990.robot.subsystems.HatchClaw;
import frc4990.robot.subsystems.Pneumatic;
import frc4990.robot.subsystems.Turret;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {

	//Driver Station Inputs
	public static F310Gamepad driveGamepad = new F310Gamepad(0);
	public static F310Gamepad opGamepad = new F310Gamepad(1);
	
	//Sensors
	public static PowerDistributionPanel pdp;
	public static AHRS ahrs;
	public static Compressor compressor;
	public static int pcmCANID;

	//DriveTrain
	public static TalonWithMagneticEncoder leftFrontDriveTalon;
	public static WPI_TalonSRX leftRearDriveTalon;
	public static TalonWithMagneticEncoder rightFrontDriveTalon;
	public static WPI_TalonSRX rightRearDriveTalon;
	public static TalonSRXGroup leftMotorGroup;
	public static TalonSRXGroup rightMotorGroup;

	//Turret
	public static TalonWithMagneticEncoder turretTalon;
	public static DigitalInput turretSensorLeft;
	public static DigitalInput turretSensorMiddle;
	public static DigitalInput turretSensorRight;

	//Climbing pneumatics
	public static Pneumatic frontSolenoid;
	public static Pneumatic rearSolenoid;

	//HatchClaw
	public static Pneumatic hatchPneumatic;
	public static WPI_TalonSRX hatchMotor;
	public static Counter hatchMotorCounter;

	//Subsystems
	public static DriveTrain driveTrain;
	public static HatchClaw hatchClaw;
	public static Turret turret;
	public static Dashboard dashboard;

	/**
	 * true = practice, false = comp
	 */

	public static DigitalInput robotSelector = new DigitalInput(9); //true = practice bot, false = competition bot

	public RobotMap() {
    
    //all port bindings or empty constuctors that stay the same for the pratice & real robots.

		ahrs = new AHRS(SPI.Port.kMXP);

		driveGamepad = new F310Gamepad(0);
		opGamepad = new F310Gamepad(1);

		if (robotSelector.get()) { //practice bot
			System.out.println("I am the *PRACTICE* bot.");
      //all port bindings that are only true for the practice robot. (PDP = 2, PCM = 12, Talons = 31 through 40)

			pcmCANID = 12;
			pdp = new PowerDistributionPanel(2);

			leftFrontDriveTalon = new TalonWithMagneticEncoder(31, SensorMode.Relative);
			leftRearDriveTalon = new WPI_TalonSRX(32);
			rightFrontDriveTalon = new TalonWithMagneticEncoder(33, SensorMode.Relative);
			rightRearDriveTalon = new WPI_TalonSRX(34);

			turretTalon = new TalonWithMagneticEncoder(35);
			turretSensorLeft = new DigitalInput(0);
			turretSensorMiddle = new DigitalInput(1);
			turretSensorRight = new DigitalInput(2);

			hatchMotor = new WPI_TalonSRX(36);
			hatchMotorCounter = new Counter(3);

		} else { //competition bot
			System.out.println("I am the *COMP* bot.");
       //all port bindings that are only true for the competition robot. (PDP = 1, PCM = 11, Talons = 21 through 30)
			
			pcmCANID = 11;
			pdp = new PowerDistributionPanel(1);

			leftFrontDriveTalon = new TalonWithMagneticEncoder(21);
			leftRearDriveTalon = new WPI_TalonSRX(22);
			rightFrontDriveTalon = new TalonWithMagneticEncoder(23);
			rightRearDriveTalon = new WPI_TalonSRX(24);

			turretTalon = new TalonWithMagneticEncoder(25);
			turretSensorLeft = new DigitalInput(0);
			turretSensorMiddle = new DigitalInput(1);
			turretSensorRight = new DigitalInput(2);

			hatchMotor = new WPI_TalonSRX(26);
			hatchMotorCounter = new Counter(3);
		}

		//all port bindings that are dependent on robot-specific port bindings.
    
		frontSolenoid = new Pneumatic(pcmCANID, 0);
		rearSolenoid = new Pneumatic(pcmCANID, 1);
		hatchPneumatic = new Pneumatic(pcmCANID, 2);
		compressor = new Compressor(pcmCANID);

		leftMotorGroup = new TalonSRXGroup(ControlMode.Velocity, leftFrontDriveTalon); 
		rightMotorGroup = new TalonSRXGroup(ControlMode.Velocity, rightFrontDriveTalon); 

    //all subsystems go at the end.
		
		turret = new Turret();
		hatchClaw = new HatchClaw();
		driveTrain = new DriveTrain();
		dashboard = new Dashboard();
	
	}
}
