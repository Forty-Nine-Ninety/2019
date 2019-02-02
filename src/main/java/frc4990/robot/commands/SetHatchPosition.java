package frc4990.robot.commands;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc4990.robot.RobotMap;

//TODO Fix the multiple issues in this class and maybe add PID?
public class SetHatchPosition extends Command {
	
	private HatchPosition target;
	private double speed;
	private boolean isFinished;

	public SetHatchPosition(HatchPosition position, double speed) {
		target = position;
		this.speed = speed;
	}

	public void initialize() {
		RobotMap.hatch.setPIDSourceType(PIDSourceType.kDisplacement);
	    this.setName("Hatch", "SetHatchPosition");    
		SmartDashboard.putData(this);
		isFinished = false;
		RobotMap.hatch.resetCounter();
	}

	public void execute() {
		if (target == HatchPosition.Engaged && RobotMap.hatch.getEncoderDistance() < -85) RobotMap.hatchMotor.set(speed);
		else if (target == HatchPosition.Relaxed && RobotMap.hatch.getEncoderDistance() < 85) RobotMap.hatchMotor.set(-1 * speed);
		else isFinished = true;
	}
	
	public void end() {
		RobotMap.hatchMotor.set(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		if (isFinished) return true;
		return this.isTimedOut();
	}

	public static enum HatchPosition { Engaged, Relaxed }
}
