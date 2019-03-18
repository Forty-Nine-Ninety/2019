/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;

public class manualOutakeSequence extends CommandGroup {
   /**
   * Intakes a hatch without Limelight support.
   */
  public manualOutakeSequence() {
    //assume turretPneumatic is retracted and hatchPneumatic is extended
    addSequential(RobotMap.turretPneumatic.extend());
    addSequential(RobotMap.hatchPneumatic.extend());
    addSequential(new wait(0.3));
    addSequential(RobotMap.turretPneumatic.retract());
    addSequential(new wait(0.1));
    addSequential(new InstantCommand(() -> {
			RobotMap.turret.controlDisabled = false;
        RobotMap.driveTrain.controlDisabled = false;
		}));
    //only move hatch grabber up if facing forward or back 
    //addSequential(RobotMap.hatchPneumatic.retract());
    /*addSequential(new ConditionalCommand(RobotMap.hatchPneumatic.retract(), 
      new PrintCommand("[ManualOutakeSequence] not retracting, on side")){

      @Override
      protected boolean condition() {
        return RobotMap.turret.findNearestTurretPoint() == TurretPoint.Back || 
          RobotMap.turret.findNearestTurretPoint() == TurretPoint.Forward;
      }

    });*/
  }
}
