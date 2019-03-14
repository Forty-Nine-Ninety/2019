package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
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
	}

	public void execute() {
		double hError = CLimelight.getCrosshairHorizontalOffset() * -1, dError = CLimelight.getCrosshairVerticalOffset() * -1;
		double speedL = RobotMap.LimelightCorrectionSpeed, speedR = RobotMap.LimelightCorrectionSpeed;

		switch(target) {
			case Forward:
			case Back:
				if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {
					speedL += RobotMap.LimelightCorrectionkPD * dError + hError * RobotMap.LimelightCorrectionkP;
					speedR += -1 * (RobotMap.LimelightCorrectionkPD * dError + hError * RobotMap.LimelightCorrectionkP);
				}
				break;
			case Left:
			case Right:
				if (Math.abs(hError) > RobotMap.LIMELIGHT_ACCURACY) {
					if (hError < 0) {speedL = RobotMap.LimelightCorrectionSpeed * -1; speedR = speedL;};
					speedL += hError * RobotMap.LimelightCorrectionkP;
					speedR += hError * RobotMap.LimelightCorrectionkP;
				}
				break;
			case Safe:
			default:
				break;
		}

		if (target == TurretPoint.Back) {
			double temp = speedL;
			speedL = speedR * -1;
			speedR = temp * -1;
		}
		else if (target == TurretPoint.Left) {
			speedL *= -1;
			speedR = speedL;																																																								
		}

		System.out.println("[DEBUG] " + hError + " " + dError);
		/*
        if (hError > RobotMap.LIMELIGHT_ACCURACY) {
            switch(target) {
				case Forward:
					speedL += dError * kPD;
					speedR -= dError * kPD;
				case Left:
					speedL += kP * hError;
					speedR -= kP * hError;
					break;
				case Back:
					speedL -= dError * kPD;
					speedR += dError * kPD;
				case Right:
					speedL -= kP * hError;
					speedR += kP * hError;
					break;
				default:
					break;
			}
        }
        else if (hError < -1 * RobotMap.LIMELIGHT_ACCURACY) {
            switch(target) {
				case Forward:
					speedL -= dError * kPD;
					speedR += dError * kPD;
				case Left:
					speedL -= kP * hError;
					speedR += kP * hError;
					break;
				case Back:
					speedL += dError * kPD;
					speedR -= dError * kPD;
				case Right:
					speedL += kP * hError;
					speedR -= kP * hError;
					break;
				default:
					break;
			}
		}
		*/
		DriveTrain.setSpeed(clamp(speedL, -1, 1), clamp(speedR, -1, 1));
	}
	
	public void end() {
		System.out.println("Done");
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
