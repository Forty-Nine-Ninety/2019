/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;

public class ClimbingSequence extends CommandGroup {
  /**
   * Auto-magically climbes HAB level 2.
   */
  public ClimbingSequence() {
    addSequential(RobotMap.frontSolenoid.toggleCommand());
    addSequential(new RobotDriveStraight(1, 0.5));
    //addSequential(RobotMap.rearSolenoid.toggleCommand());
    addSequential(RobotMap.frontSolenoid.toggleCommand());
    addSequential(new RobotDriveStraight(2, 0.2));
    //addSequential(RobotMap.rearSolenoid.toggleCommand());
    addSequential(new RobotDriveStraight(0.3, -0.3));
    addSequential(new RobotDriveStraight(1.5, 0.2));

  }
}
