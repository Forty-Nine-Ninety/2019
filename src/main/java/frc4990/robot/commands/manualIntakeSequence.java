/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;

public class manualIntakeSequence extends CommandGroup {
  /**
   * Intakes a hatch without Limelight support.
   */
  public manualIntakeSequence() {
    //assume turretPneumatic is retracted and hatchPneumatic is extended
    addSequential(RobotMap.hatchPneumatic.extend());
    addSequential(RobotMap.turretPneumatic.extend());
    addSequential(new wait(0.2));
    addSequential(RobotMap.hatchPneumatic.retract());
    addSequential(new wait(0.3));
    addSequential(RobotMap.turretPneumatic.retract());
    addSequential(new ReturnDriverControlsCommand());
  }
}
