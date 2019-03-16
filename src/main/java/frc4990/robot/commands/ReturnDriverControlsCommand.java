package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;

public class ReturnDriverControlsCommand extends InstantCommand {
    
    @Override
    public void initialize() {
        RobotMap.turret.controlDisabled = false;
        RobotMap.driveTrain.controlDisabled = false;
    }

    @Override
    public void execute() {
        initialize();
    }
}
