/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc4990.robot.components;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class InstantCommandRunDisabled extends InstantCommand {
		  
    public InstantCommandRunDisabled() {
        this.setRunWhenDisabled(true);
    }

    public InstantCommandRunDisabled(String name) {
      super(name);
      this.setRunWhenDisabled(true);
    }

    public InstantCommandRunDisabled(Subsystem subsystem) {
      super(subsystem);
      this.setRunWhenDisabled(true);
    }
  
    public InstantCommandRunDisabled(String name, Subsystem subsystem) {
      super(name, subsystem);
      this.setRunWhenDisabled(true);
    }
  
    public InstantCommandRunDisabled(Runnable func) {
      super(func);
      this.setRunWhenDisabled(true);
      
    }

    public InstantCommandRunDisabled(String name, Runnable func) {
      super(name, func);
      this.setRunWhenDisabled(true);
    }
  
    public InstantCommandRunDisabled(Subsystem requirement, Runnable func) {
      super(requirement, func);
      this.setRunWhenDisabled(true);
    }

    public InstantCommandRunDisabled(String name, Subsystem requirement, Runnable func) {
      super(name, requirement, func);
      this.setRunWhenDisabled(true);
    }
}
