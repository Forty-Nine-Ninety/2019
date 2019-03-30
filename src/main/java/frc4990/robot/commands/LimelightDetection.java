package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc4990.robot.OI;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;
import frc4990.robot.components.CLimelight.Pipeline;

public class LimelightDetection extends Command {

	public LimelightDetection() {
		
	}

	public void initialize() {
		System.out.println("[Debug] Initializing.");
		CLimelight.setPipeline(Pipeline.Vision);
		CLimelight.setLedMode(0);
	}
	
	public void execute() {
		if (Math.abs(OI.turretTurn.getRawAxis()) > 0.05) return;
		if (! CLimelight.hasValidTarget()) {//If no target found
			System.out.println("[Debug] No valid target in frame.");
			RobotMap.turret.controlDisabled = false;
			//RobotMap.driveTrain.controlDisabled = false;
			return;
		}
		//This code will only run if there's a valid target.
		System.out.println("[Debug] Seeking target.");

		RobotMap.turret.controlDisabled = true;
		double hError = CLimelight.getCrosshairHorizontalOffset();
		if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {//Follow the target.
			//horizontal (turret) error
			RobotMap.turret.setSpeed(clamp(hError * RobotMap.LimelightCorrectionkPH, -1, 1));
			System.out.println("[Debug] Correcting turret: " + hError);
			//RobotMap.driveTrain.controlDisabled = false;
		}
		/*  Maybe enable this in the future but it doesn't work rn
		else if (CLimelight.inRange()) {//Limelight is facing target and said target is close enough
			System.out.println("[Debug] Running Outake: " + hError);
			RobotMap.driveTrain.controlDisabled = true;//If limelight has target then take over control of drivetrain
			DriveTrain.setSpeed(0);
			if (CLimelight.detectionMode == DetectionMode.Intake) Scheduler.getInstance().add(new manualIntakeSequence());
			else Scheduler.getInstance().add(new manualOutakeSequence());
			isDone = true;
		}
		*/
	}
	
	public void end() {
		System.out.println("[Debug] Done.");
		CLimelight.setPipeline(Pipeline.Driver.get());
		Scheduler.getInstance().add(new InstantCommand(() -> {
			RobotMap.turret.controlDisabled = false;
        RobotMap.driveTrain.controlDisabled = false;
		}));
	}
	
	public void interrupted() {
		System.out.println("[Debug] Interrupted.");
		end();
	}
	
	public boolean isFinished() {
		return false;
	}

	private static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}
}
