/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.components.TalonWithMagneticEncoder;
import frc4990.robot.subsystems.DriveTrain;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class FollowPath extends Command {

  private EncoderFollower leftFollower, rightFollower;
  private Trajectory leftTrajectory, rightTrajectory;
  private int pathInverted = 1;

  public FollowPath(String pathName) {
    try {
      rightTrajectory = PathfinderFRC.getTrajectory(pathName + ".left"); //inverted left/right per http://wpilib.screenstepslive.com/s/currentCS/m/84338/l/1021631-integrating-path-following-into-a-robot-program
      leftTrajectory = PathfinderFRC.getTrajectory(pathName + ".right");
    } catch (IOException e) {
      e.printStackTrace();
      this.cancel();
    }
    
    leftFollower = new EncoderFollower(leftTrajectory);
    rightFollower = new EncoderFollower(rightTrajectory);

    leftFollower.configureEncoder(RobotMap.leftFrontDriveTalon.getPosition(), TalonWithMagneticEncoder.ticksPerRevolution, RobotMap.wheelDiameter);
    rightFollower.configureEncoder(RobotMap.rightFrontDriveTalon.getPosition(), TalonWithMagneticEncoder.ticksPerRevolution, RobotMap.wheelDiameter);
    
    leftFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.maxVelocity, 0); //todo: configure PID/VA constents
    rightFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.maxVelocity, 0); //todo: configure PID/VA constents
  }

  public FollowPath(String pathName, boolean inverted) {
    this(pathName);
    pathInverted = inverted ? -1 : 1;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double left_speed = leftFollower.calculate(RobotMap.leftFrontDriveTalon.getPosition());
    double right_speed = rightFollower.calculate(RobotMap.rightFrontDriveTalon.getPosition());
    double heading = RobotMap.ahrs.getAngle();
    double desired_heading = Pathfinder.r2d(leftFollower.getHeading());
    double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
    double turn =  0.8 * (-1.0/80.0) * heading_difference;
    DriveTrain.setSpeed((left_speed + turn) * pathInverted, (right_speed - turn) * pathInverted);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return leftFollower.isFinished() || rightFollower.isFinished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriveTrain.setSpeed(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
