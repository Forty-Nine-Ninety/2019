package frc4990.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc4990.robot.OI;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;
import frc4990.robot.components.SendableObject;
import frc4990.robot.components.CLimelight.Pipeline;
import frc4990.robot.subsystems.Dashboard;

public class LimelightDetection extends Command {

	PIDController limelightDetectionPID = new PIDController(RobotMap.LimelightCorrectionkPH, 0, 0, 
	new PIDSource(){
		public void setPIDSourceType(PIDSourceType pidSource) {} 
		public double pidGet() {return CLimelight.getCrosshairHorizontalOffset();} 
		public PIDSourceType getPIDSourceType() { return PIDSourceType.kDisplacement;}
	}, (PIDOutput) (double output) -> {});

	public LimelightDetection() {
		limelightDetectionPID.setInputRange(-29.8, 29.8);
		limelightDetectionPID.setOutputRange(-1, 1);
		limelightDetectionPID.setSetpoint(0);
		limelightDetectionPID.setAbsoluteTolerance(0.25);
		limelightDetectionPID.enable();
		limelightDetectionPID.setEnabled(true);
		LiveWindow.add(limelightDetectionPID);
		Dashboard.debugTab.add("limelightDetectionPID/output", new SendableObject(limelightDetectionPID::get));
		Dashboard.debugTab.add("limelightDetectionPID/error", new SendableObject(limelightDetectionPID::getError));
	}

	public void initialize() {
		System.out.println("[Debug] Initializing Limelight.");
		CLimelight.setPipeline(Pipeline.Vision);
		CLimelight.setLedMode(0);
	}

	public void execute() {
		if (Math.abs(OI.turretTurn.getRawAxis()) > 0.05) {
			return;
		}
		if (! CLimelight.hasValidTarget()) {//If no target found
			RobotMap.turret.controlDisabled = false;
			//RobotMap.driveTrain.controlDisabled = false;
			return;
		}
		//This code will only run if there's a valid target.

		RobotMap.turret.controlDisabled = true;
		double hError = CLimelight.getCrosshairHorizontalOffset();
		if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {//Follow the target.
			//horizontal (turret) error
			RobotMap.turret.setSpeed(clamp(hError * RobotMap.LimelightCorrectionkPH,-1,1));
			//RobotMap.turret.setSpeed(limelightDetectionPID.get());
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
		CLimelight.setPipeline(Pipeline.Driver.get());
		Scheduler.getInstance().add(new InstantCommand(() -> {
			RobotMap.turret.controlDisabled = false;
        RobotMap.driveTrain.controlDisabled = false;
		}));
		System.out.println("[Debug] Turned off Limelight.");
	}
	
	public void interrupted() {
		System.out.println("[Debug] Limelight Interrupted.");
		end();
	}
	
	public boolean isFinished() {
		return false;
	}

	private static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}
}
