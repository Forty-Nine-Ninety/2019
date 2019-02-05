package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;

public class RobotDriveStraight extends Command {

	public static double targetTime = Dashboard.getConst("RobotDriveStraight/defaultTargetTime", 2.8);
	double speed = Dashboard.getConst("RobotDriveStraight/defaultSpeed", 0.3);
	
	public RobotDriveStraight(double time) {
		requires(RobotMap.driveTrain);
		RobotDriveStraight.targetTime = time;
		//requires(RobotMap.driveTrain);
	}
	
	public RobotDriveStraight(double time, double speed) {
		requires(RobotMap.driveTrain);
		RobotDriveStraight.targetTime = time;
		this.speed = speed;
		//requires(RobotMap.driveTrain);
	}

	public RobotDriveStraight() {
		requires(RobotMap.driveTrain);
		//requires(RobotMap.driveTrain);
	}

	public void initialize() {
		System.out.println("Initalizing GyroStraight with time " + RobotDriveStraight.targetTime);
		RobotMap.driveTrain.resetDistanceTraveled();
		RobotMap.driveTrain.setSpeed(speed);
	}

	public void execute() {
		System.out.println("RobotDriveStraight. Time: " + this.timeSinceInitialized() + " stopping at: " + RobotDriveStraight.targetTime);
	}
	
	public void end() {
		RobotMap.driveTrain.setSpeed(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return this.timeSinceInitialized() >= RobotDriveStraight.targetTime;
	}

}
