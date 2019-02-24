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
		super(Dashboard.getConst("PIDTurretTurn/p", 0.6),
		Dashboard.getConst("PIDTurretTurn/i", 0),
		Dashboard.getConst("PIDTurretTurn/d", 6));

		switch(point) {
			case Forward:
				target = 15000;//0;
				break;
			case Left:
				target = 7000; //270d * 4096d / 180d;
				break;
			case Right:
				target = 24000;//90d * 4096d / 180d;
				break;
			case Back:
				target = -2000; //180d * 4096d / 180d;
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
		//this.getPIDController().setInputRange(0, 4096*2);
		this.getPIDController().setOutputRange(-0.5, 0.5);
		//this.getPIDController().setContinuous(true);
		this.getPIDController().setAbsoluteTolerance(20);

		this.getPIDController().setSetpoint(target);
		this.getPIDController().enable();
	}

	public void execute() {
		Dashboard.putConst("PIDTurretTurn/error", this.getPIDController().getError());
	}
	
	public void end() {
		RobotMap.turretTalon.set(0);
		this.close();
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
		RobotMap.turret.setSpeed(output);
	}
}