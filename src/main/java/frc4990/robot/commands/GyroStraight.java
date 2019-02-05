package frc4990.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;

public class GyroStraight extends Command implements PIDOutput, PIDSource {

    /* The following PID Controller coefficients will need to be tuned 
     to match the dynamics of your drive system.  Note that the      
     SmartDashboard in Test mode has support for helping you tune    
     controllers by displaying a form where you can enter new P, I,  
     and D constants and test the mechanism.                         */
	
	PIDController turnController = new PIDController(Dashboard.getConst("tP", 0.2), 
	Dashboard.getConst("tI", 0), 
	Dashboard.getConst("tD", 0), (PIDSource) RobotMap.ahrs, this);
	double speed;

	public GyroStraight(double speed, double distance) {
		this.speed = Dashboard.getConst("GyroStraight-speed", speed);
	}

	public void initialize() {
		System.out.println("Initalizing GyroStraight");
		RobotMap.ahrs.setPIDSourceType(PIDSourceType.kDisplacement);
	    this.setName("DriveSystem", "GyroStraight");    
	    SmartDashboard.putData(this);

		RobotMap.ahrs.zeroYaw();
		turnController.setInputRange(-180, 180);
		turnController.setOutputRange(-1, 1);
		turnController.setName("DriveSystem", "turnController");
		SmartDashboard.putData(turnController);
	  
		RobotMap.driveTrain.resetDistanceTraveled();
		
		turnController.setPercentTolerance(2);
		turnController.setSetpoint(0);
		turnController.enable();
		turnController.setEnabled(true);
		
	}

	public void execute() {
		System.out.println("speed = " + speed + ", turnOutput = " + this.turnController.get() + ", ahrs = " + RobotMap.ahrs.pidGet() + ", isEnabled = "+turnController.isEnabled());
		this.pidOutput(this.turnController.get(), speed);
		if(this.turnController.isEnabled() == false) {
			turnController.setEnabled(true);
		}
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
	
	public void pidOutput(double turnOutput, double speed) {
		RobotMap.driveTrain.setSpeed(speed + turnOutput, speed - turnOutput);
	}


	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return null;
	}

	@Override
	public double pidGet() {
		return RobotMap.ahrs.pidGet();
	}

	@Override
	public void pidWrite(double output) {
		SmartDashboard.putNumber("turnController-output", output);
		SmartDashboard.putNumber("turnController-error", turnController.getError());
	}
}
