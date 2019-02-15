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
import frc4990.robot.commands.TurretTurn.TurretPoint;
import frc4990.robot.components.F310Gamepad.Axis;
import frc4990.robot.components.F310Gamepad.Buttons;
import frc4990.robot.components.F310Gamepad.POV;
import frc4990.robot.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author Class of '21 (created in 2018 season)
 */
public class OI{
	
	public static Axis throttleAxis = Axis.leftJoystickY; //used in TeleopDriveTrain Controller
	public static Axis turnSteepnessAxis = Axis.rightJoystickX; //used in TeleopDriveTrain Controller
	
	/* Controller Mapping:
		Drive Train: (drive controller)
		    Joysticks 1 and 2: forward/backward and turn left/right
			X button: toggle slow throttle
			B button: toggle slow turning
			Y button: toggle stick shaping methods (differential drive and 2018-season)
		
		Turret: (OP controller)
			POV pad: (up, down, left & right) move turret to point forwards, backwards, left & right
			A button: move turret to safe point (45 degrees) (inside frame perimiter)
		
		HatchClaw: (OP controller)
			B button: toggle hatch motor (90 degrees and 0 degrees)
			Left bumper (button): toggle HatchClaw pnuematic actuator
		
		Climbing Pneumatics: (Op controller)
			X Button: toggle front pneumatics
			Y Button: toggle rear pneumatics

		Compressor: (OP controller)
			Right bumper (button): toggle compressor (on until about 115 psi is reached or just off)

		
		Check which controller is which: (both)
		    START key (RIGHT Middle): prints in console which controller it is being pressed on
	 */
	
	public OI() {

		//drivetrain
		RobotMap.driveGamepad.getButton(Buttons.x).toggleWhenPressed(driveSpeedToggle());
		RobotMap.driveGamepad.getButton(Buttons.b).toggleWhenPressed(turnSpeedToggle());
		RobotMap.driveGamepad.getButton(Buttons.y).whenPressed(stickShapingToggle());

		
		//controller check
		RobotMap.driveGamepad.getButton(Buttons.start).toggleWhenPressed(
			new InstantCommand("DriveControllerCheck", () -> System.out.println("START pressed on Drive Gamepad.")));
		RobotMap.opGamepad.getButton(Buttons.start).toggleWhenPressed(
			new InstantCommand("OPControllerCheck", () -> System.out.println("START pressed on OP Gamepad.")));

		//turret (manual turning)
		RobotMap.opGamepad.getAxis(Axis.leftTrigger).whileHeld(RobotMap.turret.setTurretSpeed(
			-1 * RobotMap.opGamepad.getAxis(Axis.leftTrigger).getRawAxis()));
		RobotMap.opGamepad.getAxis(Axis.rightTrigger).whileHeld(RobotMap.turret.setTurretSpeed(
			RobotMap.opGamepad.getAxis(Axis.rightTrigger).getRawAxis()));
		
		//turret (setpoints)	
		RobotMap.opGamepad.getPOVButton(POV.north).toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Forward));
		RobotMap.opGamepad.getPOVButton(POV.west).toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Left));
		RobotMap.opGamepad.getPOVButton(POV.east).toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Right));
		RobotMap.opGamepad.getPOVButton(POV.south).toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Back));
		RobotMap.opGamepad.getButton(Buttons.a).toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Safe));
    
		//Hatch
		RobotMap.opGamepad.getButton(Buttons.leftBumper).whenPressed(RobotMap.hatchPneumatic.toggle(RobotMap.hatchPneumatic));
		RobotMap.opGamepad.getButton(Buttons.b).whenPressed(HatchClaw.toggleMotor());
		

		//Pneumatics
		RobotMap.opGamepad.getButton(Buttons.x).whenPressed(RobotMap.frontSolenoid.toggle(RobotMap.frontSolenoid));
		RobotMap.opGamepad.getButton(Buttons.y).whenPressed(RobotMap.rearSolenoid.toggle(RobotMap.rearSolenoid));
		RobotMap.opGamepad.getButton(Buttons.rightBumper).whenPressed(compressorToggle());
	}
	
	public static InstantCommand stickShapingToggle() {
		return new InstantCommand("StickShapingToggle", (Runnable) () -> {
			TeleopDriveTrainController.stickShapingMode = (TeleopDriveTrainController.stickShapingMode == StickShapingMode.DifferentialDrive) ? 
			StickShapingMode.SquaredThrottle : StickShapingMode.DifferentialDrive;
			System.out.println("[StickShaping Method] Changed to:" + TeleopDriveTrainController.stickShapingMode.toString());
		});
		
	}

	public static InstantCommand driveSpeedToggle() {
		return new InstantCommand("DriveSpeedToggle", (Runnable) () -> {
			TeleopDriveTrainController.currentThrottleMultiplier = TeleopDriveTrainController.currentThrottleMultiplier == 
				Dashboard.getConst("DriveDpiToggle/lowThrottleMultiplier", 0.5) ? 
				TeleopDriveTrainController.currentThrottleMultiplier = Dashboard.getConst("DriveDpiToggle/defaultThrottleMultiplier", 1.0) : 
				Dashboard.getConst("DriveDpiToggle/lowThrottleMultiplier", 0.5);
			System.out.println("Throttle Speed: " + TeleopDriveTrainController.currentThrottleMultiplier + "x");
		});
	}

	public static InstantCommand turnSpeedToggle() {
		return new InstantCommand("TurnSpeedToggle", (Runnable) () -> {
			TeleopDriveTrainController.currentTurnSteepnessMultiplier = TeleopDriveTrainController.currentTurnSteepnessMultiplier == 
				Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier", 0.6) ? Dashboard.getConst("DriveDpiToggle/defaultTurnSpeedMultiplier", 1.0) : 
				Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier", 0.6);
			System.out.println("Turn Speed: " + TeleopDriveTrainController.currentTurnSteepnessMultiplier + "x");
		});
	}

	public static InstantCommand compressorToggle() {
		return new InstantCommand("CompressorToggle", (Runnable) () -> {
			RobotMap.compressor.setClosedLoopControl(!RobotMap.compressor.getClosedLoopControl());
			System.out.println(RobotMap.compressor.getClosedLoopControl() ? "Compressor off" : "Compressor holding pressure");
		});
	}
}
