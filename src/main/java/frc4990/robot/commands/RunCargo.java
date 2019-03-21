/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;

public class RunCargo extends Command {

  private DoubleSupplier input;

  public RunCargo(DoubleSupplier input) {
    this.input = input;
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    execute();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    RobotMap.cargoTalon.set(input.getAsDouble());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return input.getAsDouble() == 0;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    RobotMap.cargoTalon.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
