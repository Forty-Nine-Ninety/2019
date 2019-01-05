package org.usfirst.frc.team4990.robot;

import org.usfirst.frc.team4990.robot.Robot.*;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardController {

	private static Preferences preferences = Preferences.getInstance();

	/**
	 * Retrieves a numerical constant from SmartDashbaord/Shuffleboard.
	 * 
	 * @param key
	 *            string key to identify value
	 * @param def
	 *            number to return if no value is retrieved
	 * @author Deep Blue Robotics (Team 199)
	 */

	public static double getConst(String key, double def) {

		if (!preferences.containsKey("Const/" + key)) {
			preferences.putDouble("Const/" + key, def);
			if (preferences.getDouble("Const/ + key", def) != def) {
				System.err.println("pref Key" + "Const/" + key + "already taken by a different type");
				return def;
			}
		}
		return preferences.getDouble("Const/" + key, def);
	}

	/**
	 * Retrieves a boolean from SmartDashbaord/Shuffleboard.
	 * 
	 * @param key
	 *            string key to identify value
	 * @param def
	 *            value to return if no value is retrieved
	 * @author MajikalExplosions
	 */

	public static boolean getBoolean(String key, boolean def) {

		if (!preferences.containsKey("Const/" + key)) {
			preferences.putBoolean("Const/" + key, def);
			if (preferences.getBoolean("Const/ + key", def) != def) {
				System.err.println("pref Key" + "Const/" + key + "already taken by a different type");
				return def;
			}
		}
		return preferences.getBoolean("Const/" + key, def);
	}

	/**
	 * Adds a constant to SmartDashbaord/Shuffleboard.
	 * 
	 * @param key
	 *            string key to identify value
	 * @param def
	 *            value to be stored
	 * @author Deep Blue Robotics (Team 199)
	 */

	
	public static void putConst(String key, double def) {
		preferences.putDouble("Const/" + key, def);
		if (preferences.getDouble("Const/ + key", def) != def) {
			System.err.println("pref Key" + "Const/" + key + "already taken by a different type");
		}

	}

	/**
	 * Adds a boolean to SmartDashbaord/Shuffleboard.
	 * 
	 * @param key
	 *            string key to identify value
	 * @param def
	 *            value to be stored
	 * @author MajikalExplosions
	 */

	
	public static void putConst(String key, boolean def) {
		preferences.putBoolean("Const/" + key, def);
		if (preferences.getBoolean("Const/ + key", def) != def) {
			System.err.println("pref Key" + "Const/" + key + "already taken by a different type");
		}

	}



	/**
	 * Adds SendableChooser to SmartDashboard for Auto route choosing.
	 */

	public static void updateAutoDashboard() {
		// Auto chooser
		Robot.autoChooser = new SendableChooser<StartingPosition>();
		Robot.autoChooser.addDefault("Forward (cross line)", StartingPosition.FORWARD);
		Robot.autoChooser.addObject("Left", StartingPosition.LEFT);
		Robot.autoChooser.addObject("Center", StartingPosition.CENTER);
		Robot.autoChooser.addObject("Right", StartingPosition.RIGHT);
		Robot.autoChooser.addObject("Stay", StartingPosition.STAY);
		Robot.autoChooser.addObject("Test", StartingPosition.TEST);

		Robot.autoChooser.setName("AutonomusControl", "Auto Chooser");
		Robot.startPos = Robot.autoChooser.getSelected();
		SmartDashboard.putData("DriveTeam/Auto Chooser", Robot.autoChooser);
		SmartDashboard.putString("Drive/Selected Starting Position", Robot.startPos.toString());

		SmartDashboard.updateValues(); // always run at END of updateAutoDashboard

	}

	public static void smartDashboardInit() {

		// DriveTrain
		RobotMap.driveTrain.left.motorGroup.setName("DriveTrain", "LeftMotors");
		RobotMap.driveTrain.right.motorGroup.setName("DriveTrain", "RightMotors");
		RobotMap.driveTrain.left.encoder.setName("DriveTrain", "LeftEncoder");
		RobotMap.driveTrain.right.encoder.setName("DriveTrain", "RightEncoder");
		// RobotMap.differentialDrive.setName("DriveTrain", "DifferentialDrive");

		// Base Sensors
		RobotMap.pdp.setName("Sensors", "PDP");
		RobotMap.ahrs.setName("Sensors", "AHRS Gyro");
		RobotMap.ultrasonic.setName("Sensors", "Ultrasonic");
	}

	public void smartDashboardPeriodic() {

		SmartDashboard.putData("Sensors/PDP", RobotMap.pdp);
		SmartDashboard.putData("Sensors/AHRS Gyro", RobotMap.ahrs);

		SmartDashboard.putNumber("Debug/Left Encoder Distance", RobotMap.driveTrain.left.getDistanceTraveled());
		SmartDashboard.putNumber("Debug/Right Encoder Distance", RobotMap.driveTrain.right.getDistanceTraveled());

		SmartDashboard.putNumber("DriveSystem/teleop/turnSteepness", OI.turnSteepnessAnalogButton.getRawAxis());
		SmartDashboard.putNumber("DriveSystem/teleop/throttle", OI.throttleAnalogButton.getRawAxis());

		SmartDashboard.putData("Subsystems/DriveTrainSubsystem", RobotMap.driveTrain);
		if (Robot.autonomusCommand != null) {
			SmartDashboard.putData("Autonomus/AutonomusCommand", Robot.autonomusCommand);
		}
		SmartDashboard.updateValues();
	}
}