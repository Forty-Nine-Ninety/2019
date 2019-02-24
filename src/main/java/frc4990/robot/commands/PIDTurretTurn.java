package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;

public class PIDTurretTurn extends PIDCommand {
	public enum TurretPoint {
		Forward,
		Left,
		Right,
		Back,
		Safe
	};

	private double target;//in degrees


	public PIDTurretTurn(double speed, TurretPoint point) {
		super(Dashboard.getConst("PIDTurretTurn/p", 0.4),
		Dashboard.getConst("PIDTurretTurn/i", 0),
		Dashboard.getConst("PIDTurretTurn/d", 0.2));

		switch(point) {
			case Forward:
				target = 0;
				break;
			case Left:
				target = 270d * 4096d / 180d;
				break;
			case Right:
				target = 90d * 4096d / 180d;
				break;
			case Back:
				target = 180d * 4096d / 180d;
				break;
			case Safe:
				target = 45d * 4096d / 180d;
				break;
			default:
				break;
		}
	}

	public void initialize() {
		System.out.println("Initalizing PIDTurretTurn");
		//this.setSubsystem("Turret");
		SmartDashboard.putData(this);
		RobotMap.turretTalon.resetEncoder();
		//this.getPIDController().setInputRange(0, 4096);
		this.getPIDController().setOutputRange(-1, 1);
		this.getPIDController().setPercentTolerance(10);
		//this.getPIDController().setContinuous(true);
		this.getPIDController().setSetpoint(target);
		this.getPIDController().enable();
	}

	public void execute() {
		Dashboard.putConst("PIDTurretTurn/error", this.getPIDController().getError());
	}
	
	public void end() {
		RobotMap.turretTalon.set(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return this.getPIDController().onTarget();
	}

	@Override
	protected double returnPIDInput() {
		return RobotMap.turretTalon.getPosition();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.turret.setSpeed(-output);
	}
}