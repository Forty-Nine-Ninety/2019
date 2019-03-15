package frc4990.robot.subsystems;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc4990.robot.OI;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;
import frc4990.robot.components.SendableObject;

public class Dashboard extends Subsystem{

	private static Preferences preferences = Preferences.getInstance();
	@java.lang.FunctionalInterface
	public interface FunctionalInterface { Object get();}
	
	public static ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");
	public static ShuffleboardTab debugTab = Shuffleboard.getTab("Debug");

	public static InstantCommand initDebugDashboard =
		new InstantCommand("initDebugDashboard") { 
			public void initialize() {
				setRunWhenDisabled(true);
				Dashboard.addDashboardTab(true);
			}
	};

	public enum StartingPosition { STAY, FORWARD, LEFT, CENTER, RIGHT, TEST };

	public static StartingPosition startPos = StartingPosition.FORWARD;

	public static SendableChooser<StartingPosition> autoChooser	= 
		new SendableChooser<StartingPosition>(){{
			setDefaultOption("Forward (cross line)", StartingPosition.FORWARD);
			addOption("Left", StartingPosition.LEFT);
			addOption("Center", StartingPosition.CENTER);
			addOption("Right", StartingPosition.RIGHT);
			addOption("Stay", StartingPosition.STAY);
			addOption("Test", StartingPosition.TEST);
	}};

	/**
	 * Initializes the dashboards(debug and drive modes) with values and things.
	 *
	 */
	public Dashboard() {
		System.out.println("Starting Initializing Dashboard.");
		addDashboardTab(false); //drive tab
		//addDashboardTab(true); //debug tab
		System.out.println("Done Initializing Dashboard.");
	}


	public static void addDashboardTab(boolean debug) {
		if (debug && debugTab.getComponents().size() == 0) {
			System.out.println("Adding Debug Tab Components.");

			//Base sensors
			debugTab.add("Base/PDP", RobotMap.pdp);//.withWidget(BuiltInWidgets.kPowerDistributionPanel)./*withSize(3, 2).*/withPosition(8, 3);
			debugTab.add("Base/NavX-MXP-AHRS", RobotMap.ahrs);//.withWidget(BuiltInWidgets.kGyro)/*.withSize(2, 2)*/;
			debugTab.add("Base/Compressor", RobotMap.compressor);

			//Drive Train Components
			debugTab.add("DriveTrain/Left/Encoder", RobotMap.leftFrontDriveTalon);//.withWidget(BuiltInWidgets.kEncoder)./*withSize(2, 1).*/withPosition(11, 0);
			debugTab.add("DriveTrain/Right/Encoder", RobotMap.rightFrontDriveTalon);//.withWidget(BuiltInWidgets.kEncoder)./*withSize(2, 1).*/withPosition(11, 1);
			debugTab.add("DriveTrain/Left/motorGroup", RobotMap.leftMotorGroup);//.withWidget(BuiltInWidgets.kSpeedController)./*withSize(2, 1).*/withPosition(11, 2);
			debugTab.add("DriveTrain/Right/motorGroup", RobotMap.rightMotorGroup);//.withWidget(BuiltInWidgets.kSpeedController)./*withSize(2, 1).*/withPosition(11, 3);
			debugTab.add("DifferentialDrive", RobotMap.driveTrain.differentialDrive);//.withWidget(BuiltInWidgets.kDifferentialDrive).withSize(2, 2).withPosition(11, 4);
			
			debugTab.add("DriveAdjust/left", RobotMap.driveTrain.leftSpeedAdjust);
			debugTab.add("DriveAdjust/right", RobotMap.driveTrain.rightSpeedAdjust);

			//Drive Station Inputs
			debugTab.add("DriveStationInput/turnSteepness", new SendableObject((FunctionalInterface) () -> (double) OI.turnSteepness.getRawAxis()));
			debugTab.add("DriveStationInput/throttle", new SendableObject((FunctionalInterface) () -> (double) OI.throttle.getRawAxis()));

			//Autonomus
			/*if (Robot.autonomusCommand != null) {
				debugTab.add("Autonomus/AutonomusCommand", Robot.autonomusCommand).withWidget(BuiltInWidgets.kCommand);//.withPosition(11, 4);
			}*/

			//Subsystems
			debugTab.add("Subsystem/DriveTrain", RobotMap.driveTrain);
			debugTab.add("Subsystem/Turret", RobotMap.turret);

			//Climbing Pneumatics
			debugTab.add("Pneumatics/FrontSolenoid", RobotMap.frontSolenoid);
			//debugTab.add("Pneumatics/RearSolenoid", RobotMap.rearSolenoid);

			//Hatch manipulator
			debugTab.add("Pneumatics/HatchPneumatic", RobotMap.hatchPneumatic);
			debugTab.add("Pneumatics/TurretPneumatic", RobotMap.turretPneumatic);

			//Turret
			debugTab.add("Turret/TurretMotor", RobotMap.turretTalon);
			debugTab.add("Turret/TurretSensor", new SendableObject(RobotMap.turretSensor));

		} else if (! debug) { //drive
			System.out.println("Adding Drive Tab Components.");
			//driveTab.add("Scheduler", Scheduler.getInstance()).withSize(2, 3);
			driveTab.add("initDebugDashboard", initDebugDashboard);
			
			driveTab.add("Turret Reset Position", new SendableObject(RobotMap.turretSensor)).withWidget(BuiltInWidgets.kBooleanBox).withSize(2, 1);
			driveTab.add("Turret Sensor A", new SendableObject((BooleanSupplier) () -> (boolean) RobotMap.turretSensorA.get())).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1);
			driveTab.add("Turret Sensor B", new SendableObject((BooleanSupplier) () -> (boolean) RobotMap.turretSensorB.get())).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1);
			driveTab.add("Turret Position", new SendableObject((FunctionalInterface) () -> RobotMap.turretTalon.getPosition()));
			
			driveTab.add("Target Not Visible", new SendableObject((BooleanSupplier) () -> (boolean) !CLimelight.hasValidTarget())).withWidget(BuiltInWidgets.kBooleanBox).withSize(3, 1);
			driveTab.add("Target Visible, too FAR", new SendableObject((BooleanSupplier) () -> (boolean) CLimelight.hasValidTarget() && CLimelight.tooFar())).withWidget(BuiltInWidgets.kBooleanBox).withSize(3, 1);
			driveTab.add("Target Visible, too CLOSE", new SendableObject((BooleanSupplier) () -> (boolean) CLimelight.hasValidTarget() && !CLimelight.tooFar())).withWidget(BuiltInWidgets.kBooleanBox).withSize(3, 1);
			driveTab.add("Target In Range", new SendableObject((BooleanSupplier) () -> (boolean) CLimelight.inRange())).withWidget(BuiltInWidgets.kBooleanBox).withSize(3, 1);
			driveTab.add("Limelight Status", new SendableObject((FunctionalInterface) () -> CLimelight.getStatus()));


			//driveTab.add("DebugTabComponents", new SendableObject(() -> debugTab.getComponents().size()));

			//driveTab.add("SelectedStartPosition", new SendableObject((FunctionalInterface) () -> autoChooser.getSelected().name())).withSize(2, 1).withPosition(3, 3);
			//driveTab.add("AutoChooser", autoChooser).withWidget(BuiltInWidgets.kComboBoxChooser).withSize(2, 1).withPosition(4, 3);
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

	@Override
	protected void initDefaultCommand() {}

}
