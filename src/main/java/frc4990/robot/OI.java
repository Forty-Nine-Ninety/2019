/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.commands.*;
import frc4990.robot.commands.TeleopDriveTrainController.StickShapingMode;
import frc4990.robot.commands.TurretTurn.TurretPoint;
import frc4990.robot.components.*;
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
	
	public static JoystickAnalogButton throttle = RobotMap.driveGamepad.leftJoystickY;
	public static JoystickAnalogButton turnSteepness = RobotMap.driveGamepad.rightJoystickX;

	public static JoystickAnalogButton turretTurn = RobotMap.driveGamepad.leftJoystickX;
	public static Button turretForwardButton = RobotMap.opGamepad.getButton(Buttons.y);
	public static Button turretLeftButton = RobotMap.opGamepad.getButton(Buttons.x);
	public static Button turretRightButton = RobotMap.opGamepad.getButton(Buttons.b);
	public static Button turretBackButton = RobotMap.opGamepad.getButton(Buttons.a);
	public static Button turretSafeButton = RobotMap.opGamepad.getButton(Buttons.start);

	public static JoystickAnalogButton hatchPneumatic = RobotMap.opGamepad.rightJoystickX;
	public static POVButton hatchMotorUp = RobotMap.opGamepad.getPOVButton(POV.north);
	public static POVButton hatchMotorDown = RobotMap.opGamepad.getPOVButton(POV.south);

	public static Button manualIntakeSequence = RobotMap.opGamepad.getButton(Buttons.leftBumper);
	public static Button manualOutakeSequence = RobotMap.opGamepad.getButton(Buttons.rightBumper);
	public static Button limelightIntakeSequence = RobotMap.opGamepad.leftTrigger;
	public static Button limelightOutakeSequence = RobotMap.opGamepad.rightTrigger;

	public static Button frontPneumatics = RobotMap.driveGamepad.rightBumper;
	public static Button rearPneumatics = RobotMap.driveGamepad.leftBumper;
	public static Button climbSequence = RobotMap.driveGamepad.rightTrigger;

	
	/* Controller Mapping:
		Drive Train: (drive controller)
		    Joysticks 1 and 2: forward/backward and turn left/right
			X button: toggle slow throttle
			B button: toggle slow turning
			Y button: toggle stick shaping methods (differential drive and 2018-season)
		
		Turret: (OP controller)
			POV pad: (up, down, left & right) move turret to point forwards, backwards, left & right)
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
		RobotMap.driveGamepad.x.toggleWhenPressed(driveSpeedToggle());
		RobotMap.driveGamepad.b.toggleWhenPressed(turnSpeedToggle());
		RobotMap.driveGamepad.y.whenPressed(stickShapingToggle());

		
		//controller check (not needed)
		//RobotMap.driveGamepad.back.toggleWhenPressed(new InstantCommand("DriveControllerCheck", () -> System.out.println("START pressed on Drive Gamepad.")));
		//RobotMap.opGamepad.back.toggleWhenPressed(new InstantCommand("OPControllerCheck", () -> System.out.println("START pressed on OP Gamepad.")));

		//turret
		turretTurn.whileHeld(RobotMap.turret.setTurretSpeed(turretTurn.getRawAxis()));
		turretForwardButton.toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Forward));
		turretLeftButton.toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Left));
		turretRightButton.toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Right));
		turretBackButton.toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Back));
		RobotMap.opGamepad.a.toggleWhenPressed(new TurretTurn(0.8, TurretPoint.Safe));
    
		//Hatch
		RobotMap.opGamepad.leftBumper.whenPressed(RobotMap.hatchPneumatic.toggleCommand());
		RobotMap.opGamepad.b.whenPressed(HatchClaw.toggleMotor());
		

		//Pneumatics
		RobotMap.opGamepad.x.whenPressed(RobotMap.frontSolenoid.toggleCommand());
		RobotMap.opGamepad.y.whenPressed(RobotMap.rearSolenoid.toggleCommand());
		RobotMap.opGamepad.back.whenPressed(compressorToggle());
	}
	
	public static InstantCommand stickShapingToggle() {
		return new InstantCommand("StickShapingToggle", (Runnable) () -> {
			TeleopDriveTrainController.stickShapingMode = (TeleopDriveTrainController.stickShapingMode == StickShapingMode.DifferentialDrive) ? StickShapingMode.SquaredThrottle : StickShapingMode.DifferentialDrive;
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
