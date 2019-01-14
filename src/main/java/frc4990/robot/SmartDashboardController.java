package frc4990.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class SmartDashboardController {

	private static Preferences preferences = Preferences.getInstance();
	private static ShuffleboardTab tab;
	public static HashMap<String,Object> debugDashboard = new HashMap<>(), driveDashboard = new HashMap<>();
	

	interface FunctionalInterface { Object anything();}
	
	static
    {
        debugDashboard = new HashMap<String, Object>(); //Second one is a reference, first one is key
		debugDashboard.put("Base/PDP", RobotMap.pdp);
		debugDashboard.put("Base/Ultrasonic", RobotMap.ultrasonic);
		debugDashboard.put("Base/NavX-MXP-AHRS", RobotMap.ahrs);

		debugDashboard.put("DriveTrain/Left/Encoder", RobotMap.leftEncoder);
		debugDashboard.put("DriveTrain/Right/Encoder", RobotMap.rightEncoder);
		debugDashboard.put("DriveTrain/Left/motorGroup", RobotMap.driveTrain.left.motorGroup);
		debugDashboard.put("DriveTrain/Right/motorGroup", RobotMap.driveTrain.right.motorGroup);
		//debugDashboard.put("DriveTrain/DifferentialDrive", DriveTrain.differentialDrive);
		
		debugDashboard.put("DriveStationInput/turnSteepness", (FunctionalInterface) () -> { return OI.turnSteepnessAnalogButton.getRawAxis(); });
		debugDashboard.put("DriveStationInput/throttle", (FunctionalInterface) () -> { return OI.throttleAnalogButton.getRawAxis(); });
		
		if (Robot.autonomusCommand != null) {
			debugDashboard.put("Autonomus/AutonomusCommand", Robot.autonomusCommand);
		}

		driveDashboard = new HashMap<String, Object>();

		driveDashboard.put("AutoChooser/SelectedStartPosition", 
			(FunctionalInterface) () -> { return Robot.autoChooser.getSelected().toString(); });
		driveDashboard.put("AutoChooser/AutoChooser", Robot.autoChooser);
		driveDashboard.put("FMS", DriverStation.getInstance());
		
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

	//New code below

	public static void updateDashboard() {
		if (tab.getTitle().equals("Debug")) updateDashboard(debugDashboard); 
		else updateDashboard(driveDashboard);
	}

	private static void updateDashboard(HashMap<String,Object> dashboardValues) {
		for (String key : dashboardValues.keySet())
			tab.add(key, dashboardValues.get(key)).getEntry().setValue(dashboardValues.get(key));
	}

	public static void setDashboardMode(boolean debug) {
		if (debug) {
			tab = Shuffleboard.getTab("Debug");

		} else {
			tab = Shuffleboard.getTab("Drive");

		}
	}
}