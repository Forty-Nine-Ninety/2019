package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Turret.TurretPoint;
import frc4990.robot.components.CLimelight;
import frc4990.robot.components.CLimelight.Pipeline;
import frc4990.robot.subsystems.DriveTrain;

public class LimelightCorrection extends Command {
	private TurretPoint target;

	public LimelightCorrection(TurretPoint target) {
		requires(RobotMap.driveTrain);
		//requires(RobotMap.driveTrain);
		this.target = target;
	}

	public void initialize() {
		CLimelight.setPipeline(Pipeline.Vision.get());
		RobotMap.driveTrain.configOpenloopRamp(0);
	}

	public void execute() {
		double hError = CLimelight.getCrosshairHorizontalOffset() * -1, dError = CLimelight.getCrosshairVerticalOffset() * -1;
		double speedL = RobotMap.LimelightCorrectionSpeed, speedR = RobotMap.LimelightCorrectionSpeed;
		if (target == TurretPoint.Forward || target == TurretPoint.Back) return;//Because forward and back are broken
		switch(target) {
			case Forward:
			case Back:
				if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {
					//horizontal (turret) error
					RobotMap.turret.setSpeed(clamp(hError * RobotMap.LimelightCorrectionkPH, -1, 1));
				}
				else if (Math.abs(dError) > RobotMap.LIMELIGHT_ACCURACY) {//Because the horizontal offset changes depending on the turret's rotation...right? If not using an if instead of an else if would be faster.
					//distance error
					speedL += dError * RobotMap.LimelightCorrectionkP;
					speedR += dError * RobotMap.LimelightCorrectionkP;
				}
				break;
			case Left:
			case Right:
				if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {
					if (hError < 0) {
						speedL = RobotMap.LimelightCorrectionSpeed * -1;
						speedR = speedL;
					}
					speedL += hError * RobotMap.LimelightCorrectionkP;
					speedR += hError * RobotMap.LimelightCorrectionkP;
				}
				break;
			case Safe:
			default:
				break;
		}

		if (target == TurretPoint.Forward) {
			double temp = speedL;
			speedL = speedR * -1;
			speedR = temp * -1;
		}
		else if (target == TurretPoint.Left) {
			speedL *= -1;
			speedR = speedL;																																																				
		}

		//System.out.println("[DEBUG] " + hError + " " + dError);
		
		DriveTrain.setSpeed(clamp(speedL, -1, 1), clamp(speedR, -1, 1));
	}
	
	public void end() {
		System.out.println("Done");
		RobotMap.driveTrain.configOpenloopRamp();
		DriveTrain.setSpeed(0);
		CLimelight.setPipeline(Pipeline.Driver.get());
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return Math.abs(CLimelight.getCrosshairHorizontalOffset()) <= RobotMap.LIMELIGHT_ACCURACY;
	}

	private static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}
}
