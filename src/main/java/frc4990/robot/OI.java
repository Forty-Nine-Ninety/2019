/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.commands.*;
import frc4990.robot.commands.TeleopDriveTrainController.StickShapingMode;
import frc4990.robot.components.JoystickAnalogButton;
import frc4990.robot.subsystems.Dashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author Class of '21 (created in 2018 season)
 */
public class OI{
	
	public static JoystickAnalogButton throttleAnalogButton = RobotMap.driveGamepad.leftJoystickY;
	public static JoystickAnalogButton turnSteepnessAnalogButton = RobotMap.driveGamepad.rightJoystickX;
	
	/* Controller Mapping:
		Drive Train: (drive controller)
		    Joysticks 1 and 2: forward/backward and turn left/right
			X button: toggle slow throttle
			B button: toggle slow turning
		
		Check which controller is which: (both)
		    START key (RIGHT Middle): prints in console which controller it is being pressed on
	 */
	
	public OI() {

		//drivetrain
		RobotMap.driveGamepad.x.toggleWhenPressed(new DriveSpeedToggle());
		RobotMap.driveGamepad.b.toggleWhenPressed(new TurnSpeedToggle());
		RobotMap.driveGamepad.y.whenPressed(new stickShapingToggle());

		
		//controller check
		RobotMap.driveGamepad.start.toggleWhenPressed(new InstantCommand("DriveControllerCheck", () -> System.out.println("START pressed on Drive Gamepad.")));
		RobotMap.opGamepad.start.toggleWhenPressed(new InstantCommand("OPControllerCheck", () -> System.out.println("START pressed on OP Gamepad.")));

		//Pneumatics
		//RobotMap.opGamepad.x.whenPressed(new TogglePneumatic(RobotMap.pneumatic1));
		//RobotMap.opGamepad.y.whenPressed(new TogglePneumatic(RobotMap.pneumatic2));
	}
	
	
	public class stickShapingToggle extends InstantCommand {

		public stickShapingToggle() {}
		
		public void initialize() {
			TeleopDriveTrainController.stickShapingMode = (TeleopDriveTrainController.stickShapingMode == StickShapingMode.DifferentialDrive) ? StickShapingMode.SquaredThrottle : StickShapingMode.DifferentialDrive;
			System.out.println("[StickShaping Method] Changed to:" + TeleopDriveTrainController.stickShapingMode.toString());
	
		}
		
	}

	public class DriveSpeedToggle extends InstantCommand {
		public void initialize() {
			TeleopDriveTrainController.currentThrottleMultiplier = TeleopDriveTrainController.currentThrottleMultiplier == 
				Dashboard.getConst("DriveDpiToggle/lowThrottleMultiplier", 0.5) ? 
				TeleopDriveTrainController.currentThrottleMultiplier = Dashboard.getConst("DriveDpiToggle/defaultThrottleMultiplier", 1.0) : 
				Dashboard.getConst("DriveDpiToggle/lowThrottleMultiplier", 0.5);
			System.out.println("Throttle Speed: " + TeleopDriveTrainController.currentThrottleMultiplier + "x");
		}
	}

	public class TurnSpeedToggle extends InstantCommand {
		public void initialize() {
			TeleopDriveTrainController.currentTurnSteepnessMultiplier = TeleopDriveTrainController.currentTurnSteepnessMultiplier == 
				Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier", 0.6) ? Dashboard.getConst("DriveDpiToggle/defaultTurnSpeedMultiplier", 1.0) : 
				Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier", 0.6);
			System.out.println("Turn Speed: " + TeleopDriveTrainController.currentTurnSteepnessMultiplier + "x");
		}
	}
}
