/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;

public class manualOutakeSequence extends CommandGroup {
   /**
   * Intakes a hatch without Limelight support.
   */
  public manualOutakeSequence() {
    //assume turretPneumatic is retracted and hatchPneumatic is extended
    addSequential(RobotMap.hatchPneumatic.toggleCommand());
    addSequential(new wait(0.1));
    addSequential(RobotMap.turretPneumatic.toggleCommand());
    addSequential(new wait(0.2));
    addSequential(RobotMap.turretPneumatic.toggleCommand());
    addSequential(new wait(0.1));
    addSequential(RobotMap.hatchPneumatic.toggleCommand());
  }
}
