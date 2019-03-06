package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.DriveTrain;

public class RobotDriveStraight extends Command {

	public static double targetTime = Dashboard.getConst("RobotDriveStraight/defaultTargetTime", 2.8);
	double speed = Dashboard.getConst("RobotDriveStraight/defaultSpeed", 0.3);
	
	public RobotDriveStraight(double time) {
		requires(RobotMap.driveTrain);
		RobotDriveStraight.targetTime = time;
	}
	
	public RobotDriveStraight(double time, double speed) {
		requires(RobotMap.driveTrain);
		RobotDriveStraight.targetTime = time;
		this.speed = speed;
	}

	public RobotDriveStraight() {
		requires(RobotMap.driveTrain);
	}

	public void initialize() {
		System.out.println("Initalizing Simple Drive Straight with time " + RobotDriveStraight.targetTime);
		RobotMap.driveTrain.resetDistanceTraveled();
		DriveTrain.setSpeed(speed);
	}

	public void execute() {
		System.out.println("RobotDriveStraight. Time: " + this.timeSinceInitialized() + " stopping at: " + RobotDriveStraight.targetTime);
	}
	
	public void end() {
		DriveTrain.setSpeed(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return this.timeSinceInitialized() >= RobotDriveStraight.targetTime;
	}

}
