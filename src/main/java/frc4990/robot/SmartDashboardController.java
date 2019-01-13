package frc4990.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc4990.robot.Robot.StartingPosition;

public class SmartDashboardController {

	private static Preferences preferences = Preferences.getInstance();
	private static ShuffleboardTab tab;
	public static HashMap<String,Object> debugDashboard = new HashMap<>(), driveDashboard = new HashMap<>();
	static
    {
        debugDashboard = new HashMap<String, Object>();
        debugDashboard.put("a", "d");//Second one is a reference, first one is key
		debugDashboard.put("c", "d");

		driveDashboard = new HashMap<String, Object>();
		/*
        driveDashboard.put("AutoChooser/SelectedStartPosition", () -> {
			return Robot.autoChooser.getSelected().toString();
		});*/

		IStartPosition isp = () -> { return Robot.autoChooser.getSelected(); };// *definitely* not a reference to iSorrowProductions
		driveDashboard.put("AutoChooser/SelectedStartPosition", isp);
		driveDashboard.put("c", "d");
    }

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
		Robot.autoChooser.setDefaultOption("Forward (cross line)", StartingPosition.FORWARD);
		Robot.autoChooser.addOption("Left", StartingPosition.LEFT);
		Robot.autoChooser.addOption("Center", StartingPosition.CENTER);
		Robot.autoChooser.addOption("Right", StartingPosition.RIGHT);
		Robot.autoChooser.addOption("Stay", StartingPosition.STAY);
		Robot.autoChooser.addOption("Test", StartingPosition.TEST);

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

		//Put data into smartdashboard
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
	}

	//New code below...?

	public static void updateDashboard() {
		if (tab.getTitle().equals("Debug")) updateDashboard(debugDashboard); else updateDashboard(driveDashboard);
	}

	private static void updateDashboard(HashMap<String,Object> dashboardValues) {
		for (String key : dashboardValues.keySet())
			tab.add(key, dashboardValues.get(key)).getEntry().setValue(dashboardValues.get(key));
	}

	public static void setDashboardMode(boolean debug) {
		if (debug) tab = Shuffleboard.getTab("Debug");
		else tab = Shuffleboard.getTab("Drive");
	}

	interface IStartPosition { Robot.StartingPosition getPosition();}
}