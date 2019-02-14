package frc4990.robot.commands;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.HatchClaw.HatchPosition;
public class SetHatchPosition extends Command {
	
	private HatchPosition target;
	private double speed;
	private boolean isFinished = false;

	public SetHatchPosition(HatchPosition position, double speed) {
		requires(RobotMap.hatchClaw);
		target = position;
		this.speed = speed;
	}

	public SetHatchPosition(double speed) {
		requires(RobotMap.hatchClaw);
		target = (RobotMap.hatchClaw.hatchPosition == HatchPosition.Engaged) ? HatchPosition.Relaxed : HatchPosition.Engaged;
		this.speed = speed;
	}

	public SetHatchPosition() {
		requires(RobotMap.hatchClaw);
		target = RobotMap.hatchClaw.hatchPosition == HatchPosition.Engaged ? HatchPosition.Relaxed : HatchPosition.Engaged;
		this.speed = Dashboard.getConst("Hatch/defaultSpeed", 0.2);
	}

	public void initialize() {
		RobotMap.hatchClaw.setPIDSourceType(PIDSourceType.kDisplacement);
	    this.setName("Hatch", "SetHatchPosition");    
		Dashboard.debugTab.add(this);
		RobotMap.hatchClaw.resetCounter();
	}

	public void execute() {
		if (target == HatchPosition.Engaged && RobotMap.hatchClaw.getEncoderDistance() < -85) RobotMap.hatchMotor.set(speed);
		else if (target == HatchPosition.Relaxed && RobotMap.hatchClaw.getEncoderDistance() < 85) RobotMap.hatchMotor.set(-1 * speed);
		else isFinished = true;
	}
	
	public void end() {
		RobotMap.hatchMotor.set(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		if (isFinished) {
			target = RobotMap.hatchClaw.hatchPosition;
			return true;
		}
		return this.isTimedOut();
	}


}
