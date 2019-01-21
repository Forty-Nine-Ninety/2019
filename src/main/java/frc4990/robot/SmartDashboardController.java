package frc4990.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class SmartDashboardController {

	private static Preferences preferences = Preferences.getInstance();
	private static ShuffleboardTab tab;
	public static HashMap<String,Object> debugDashboard = new HashMap<>(), driveDashboard = new HashMap<>();
	interface FunctionalInterface { Object anything();}
	public static ShuffleboardTab driveTab, debugTab;
	/**
	 * Initializes the dashboards(debug and drive modes) with values and things.
	 * 
	 */
	public static void initializeDashboard() {
		debugDashboard = new HashMap<>();
		driveDashboard = new HashMap<>();
		driveTab = Shuffleboard.getTab("Drive");
		debugTab = Shuffleboard.getTab("Debug");
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
			tab = debugTab;
			tab.add("Base/PDP", RobotMap.pdp).withWidget(BuiltInWidgets.kPowerDistributionPanel);//.withSize(3, 2).withPosition(8, 3);
			tab.add("Base/Ultrasonic", RobotMap.ultrasonic).withWidget(BuiltInWidgets.kNumberBar);//.withProperties(Map.of("min","0","max","50")).withSize(1, 2).withPosition(5,0);
			tab.add("Base/NavX-MXP-AHRS", RobotMap.ahrs).withWidget(BuiltInWidgets.kGyro);//.withSize(2, 2);
			tab.add("DriveTrain/Left/Encoder", RobotMap.leftEncoder).withWidget(BuiltInWidgets.kEncoder);//.withSize(2, 1).withPosition(11, 0);
			tab.add("DriveTrain/Right/Encoder", RobotMap.rightEncoder).withWidget(BuiltInWidgets.kEncoder);//.withSize(2, 1).withPosition(11, 1);
			tab.add("DriveTrain/Left/motorGroup", RobotMap.driveTrain.left.motorGroup).withWidget(BuiltInWidgets.kSpeedController);//.withSize(2, 1).withPosition(11, 2);
			tab.add("DriveTrain/Right/motorGroup", RobotMap.driveTrain.right.motorGroup).withWidget(BuiltInWidgets.kSpeedController);//.withSize(2, 1).withPosition(11, 3);
			//tab.add("DriveTrain/DifferentialDrive", DriveTrain.differentialDrive).withWidget(BuiltInWidgets.kDifferentialDrive).withSize(2, 2).withPosition(11, 4);
			//tab.add("DriveStationInput/turnSteepness", new SendableObject((Supplier<Double>) () -> { return OI.turnSteepnessAnalogButton.getRawAxis(); }));
			//tab.add("DriveStationInput/throttle", new SendableObject((Supplier<Double>) () -> { return OI.throttleAnalogButton.getRawAxis(); }));


			if (Robot.autonomusCommand != null) {
				tab.add("Autonomus/AutonomusCommand", Robot.autonomusCommand).withWidget(BuiltInWidgets.kCommand);//.withPosition(11, 4);
			}
		} 
			tab = driveTab;
			tab.add(Scheduler.getInstance());
			//tab.add("AutoChooser/SelectedStartPosition", (FunctionalInterface) () -> { return Robot.autoChooser.getSelected().toString(); });
			tab.add("AutoChooser/AutoChooser", Robot.autoChooser).withWidget(BuiltInWidgets.kComboBoxChooser);//.withSize(2, 1).withPosition(8, 1);
			//tab.add("DebugDashboard", new InstantCommand((Runnable) () -> {setDashboardMode(true);}));
		
	}
}