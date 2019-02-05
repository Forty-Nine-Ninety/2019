package frc4990.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardController {

	private static Preferences preferences = Preferences.getInstance();
	@java.lang.FunctionalInterface
	interface FunctionalInterface { Object anything();}

	public static ShuffleboardTab driveTab, debugTab;
	/**
	 * Initializes the dashboards(debug and drive modes) with values and things.
	 *
	 */
	public static void initializeDashboard(boolean debug) {
		System.out.println("Starting Initializing Dashboard.");
		driveTab = Shuffleboard.getTab("Drive");
		debugTab = Shuffleboard.getTab("Debug");
		setDashboardMode(! debug);
		setDashboardMode(debug);
		System.out.println("Done Initializing Dashboard.");
	}


	public static void setDashboardMode(boolean debug) {
		if (debug && debugTab.getComponents().size() == 0) {
			System.out.println("Adding Debug Tab Components.");

			//Base sensors
			debugTab.add("Base/PDP", RobotMap.pdp).withWidget(BuiltInWidgets.kPowerDistributionPanel)./*withSize(3, 2).*/withPosition(8, 3);
			debugTab.add("Base/NavX-MXP-AHRS", RobotMap.ahrs).withWidget(BuiltInWidgets.kGyro)/*.withSize(2, 2)*/;

			//Drive Train Components
			debugTab.add("DriveTrain/Left/Encoder", RobotMap.leftFrontDriveTalon).withWidget(BuiltInWidgets.kEncoder)./*withSize(2, 1).*/withPosition(11, 0);
			debugTab.add("DriveTrain/Right/Encoder", RobotMap.rightFrontDriveTalon).withWidget(BuiltInWidgets.kEncoder)./*withSize(2, 1).*/withPosition(11, 1);
			debugTab.add("DriveTrain/Left/motorGroup", RobotMap.leftMotorGroup).withWidget(BuiltInWidgets.kSpeedController)./*withSize(2, 1).*/withPosition(11, 2);
			debugTab.add("DriveTrain/Right/motorGroup", RobotMap.rightMotorGroup).withWidget(BuiltInWidgets.kSpeedController)./*withSize(2, 1).*/withPosition(11, 3);
			debugTab.add("DifferentialDrive", RobotMap.driveTrain.differentialDrive).withWidget(BuiltInWidgets.kDifferentialDrive).withSize(2, 2).withPosition(11, 4);
			
			//Drive Station Inputs
			debugTab.add("DriveStationInput/turnSteepness", new SendableObject(() -> {return OI.turnSteepnessAnalogButton.getRawAxis().toString(); }));
			debugTab.add("DriveStationInput/throttle", new SendableObject(() -> {return OI.throttleAnalogButton.getRawAxis().toString(); }));
			
			//Autonomus
			if (Robot.autonomusCommand != null) {
				debugTab.add("Autonomus/AutonomusCommand", Robot.autonomusCommand).withWidget(BuiltInWidgets.kCommand);//.withPosition(11, 4);
			}

			//Subsystems
			debugTab.add("Subsystem/DriveTrain", RobotMap.driveTrain);
			//debugTab.add("Subsystem/otherSubsystem", RobotMap.otherSubsystem;

			//Pneumatics
			debugTab.add("Pneumatics/Compressor", RobotMap.compressor);
			debugTab.add("Pneumatics/FrontSolenoid", RobotMap.frontSolenoid);
			debugTab.add("Pneumatics/RearSolenoid", RobotMap.rearSolenoid);

		} else if (! debug) { //drive
			System.out.println("Adding Drive Tab Components.");
			driveTab.add("Scheduler", Scheduler.getInstance()).withSize(2, 3).withPosition(0, 0);
			driveTab.add("SelectedStartPosition", new SendableObject(() -> { return Robot.autoChooser.getSelected().name(); })).withSize(2, 1).withPosition(3, 3);
			driveTab.add("AutoChooser", Robot.autoChooser).withWidget(BuiltInWidgets.kComboBoxChooser).withSize(2, 1).withPosition(4, 3);
		}
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

}
