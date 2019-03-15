package frc4990.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.DriveTrain;

public class GyroTurn extends Command implements PIDOutput {

    /* The following PID Controller coefficients will need to be tuned 
     to match the dynamics of your drive system.  Note that the      
     SmartDashboard in Test mode has support for helping you tune    
     controllers by displaying a form where you can enter new P, I,  
     and D constants and test the mechanism.                         */
	
	PIDController turnController = new PIDController(Dashboard.getConst("gyroTurn/tP", 0.02), 
	Dashboard.getConst("gyroTurn/tI", 0.00002), 
	Dashboard.getConst("gyroTurn/tD", 0.06), (PIDSource) RobotMap.ahrs, this);
	double maxSpeed;

	/**
	 * @param turnRadius The radius of turn in meters.
	 * @param isInPlace Whether to ignore turnRadius or not.
	 */
	public GyroTurn(double target, double maxSpeed, double timeout, double turnRadius, boolean isInPlace) {
		this.setTimeout(timeout);
		this.maxSpeed = Dashboard.getConst("gyroTurn/speed", maxSpeed);
		Dashboard.getConst("gyroTurn/turnRadius", turnRadius);
		Dashboard.getBoolean("gyroTurn/turnInPlace", isInPlace);
		turnController.setSetpoint(target);
	}

	public GyroTurn(double target, double maxSpeed, double timeout) {
		this(target, maxSpeed, timeout, -1, true);
	}

	public void initialize() { 
		System.out.println("Initalizing GyroTurn");
		RobotMap.ahrs.setPIDSourceType(PIDSourceType.kDisplacement);
	    this.setName("DriveSystem", "GyroTurn");    
	    SmartDashboard.putData(this);

		RobotMap.ahrs.zeroYaw();
		turnController.setInputRange(-180, 180);
		turnController.setOutputRange(-maxSpeed, maxSpeed);
		turnController.setName("DriveSystem", "gyroTurn/turnController");
		SmartDashboard.putData(turnController);
	  
		RobotMap.driveTrain.resetDistanceTraveled();
		
		turnController.setPercentTolerance(2);
		turnController.setContinuous(true);
		turnController.enable();
		turnController.setEnabled(true);
		
	}

	public void execute() {
		System.out.println("maxSpeed = " + maxSpeed + ", turnOutput = " + this.turnController.get() + ", ahrs = " + RobotMap.ahrs.pidGet() + ", isEnabled = "+turnController.isEnabled());
		if (Dashboard.getBoolean("gyroTurn/turnInPlace", true)) {
			DriveTrain.setSpeed(
				this.turnController.get(), 
				-this.turnController.get());
		}
		else {
			double radius = Dashboard.getConst("gyroTurn/turnRadius", 0);
			double ratio;
			if (radius < 0) {
				radius += RobotMap.DRIVETRAIN_WIDTH / 2;
				ratio = radius / (radius + RobotMap.DRIVETRAIN_WIDTH);
				if (Math.abs(ratio) < 1) {
					DriveTrain.setSpeed(maxSpeed, ratio * maxSpeed);
				}
				else {
					DriveTrain.setSpeed((1 / ratio) * maxSpeed, maxSpeed);
				}
			}
			else if (radius > 0) {
				radius -= RobotMap.DRIVETRAIN_WIDTH / 2;
				ratio = radius / (radius + RobotMap.DRIVETRAIN_WIDTH);
				if (Math.abs(ratio) < 1) {
					DriveTrain.setSpeed(ratio * maxSpeed, maxSpeed);
				}
				else {
					DriveTrain.setSpeed(maxSpeed, (1 / ratio) * maxSpeed);
				}
			}
			else {
				DriveTrain.setSpeed(this.turnController.get(), -this.turnController.get());
			}
		}
		if(this.turnController.isEnabled() == false) turnController.setEnabled(true);
	}
	
	public void end() {
		turnController.disable();
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	public void pidWrite(double output) {
		SmartDashboard.putNumber("gyroTurn/output", output);
		SmartDashboard.putNumber("gyroTurn/error", turnController.getError());
	}
}
