package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Turret.TurretPoint;
import frc4990.robot.components.CLimelight;
import frc4990.robot.components.CLimelight.DetectionMode;
import frc4990.robot.components.CLimelight.Pipeline;
import frc4990.robot.subsystems.DriveTrain;

public class LimelightDetection extends Command {

	private boolean vision;
	private boolean isDone;

	public LimelightDetection() {
		requires(RobotMap.driveTrain);
		//requires(RobotMap.driveTrain);
		isDone = false;
	}

	public void initialize() {
		RobotMap.driveTrain.configOpenloopRamp(0);
		CLimelight.setPipeline((CLimelight.getPipeline() == Pipeline.Driver.get()) ? Pipeline.Vision : Pipeline.Driver);
		vision = (CLimelight.getPipeline() == Pipeline.Vision.get());
		isDone = false;
	}

	public void execute() {
		if (! vision) return;//If it's on camera mode
		RobotMap.turret.controlDisabled = true;
		if (! CLimelight.hasValidTarget()) {
			RobotMap.turret.setTurretSpeed(0);
			return;//If no target is found
		}
		if (CLimelight.inRange()) {
			RobotMap.driveTrain.controlDisabled = true;//If limelight has target then take over control of drivetrain
			DriveTrain.setSpeed(0);
			double hError = CLimelight.getCrosshairHorizontalOffset() * -1;
			if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {
				//horizontal (turret) error
				RobotMap.turret.setSpeed(clamp(hError * RobotMap.LimelightCorrectionkPH, -1, 1));
			}
			else {
				if (CLimelight.detectionMode == DetectionMode.Intake) Scheduler.getInstance().add(new manualIntakeSequence());
				else Scheduler.getInstance().add(new manualOutakeSequence());
				isDone = true;
			}
		}
	}
	
	public void end() {
		System.out.println("Done");
		RobotMap.driveTrain.configOpenloopRamp();
		DriveTrain.setSpeed(0);
		CLimelight.setPipeline(Pipeline.Driver.get());
		RobotMap.driveTrain.controlDisabled = false;
		RobotMap.turret.controlDisabled = false;
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return isDone;
	}

	private static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}
}
