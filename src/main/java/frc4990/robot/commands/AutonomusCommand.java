package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.Dashboard.StartingPosition;

public class AutonomusCommand extends CommandGroup {

	public AutonomusCommand() {
		
		StartingPosition s = Dashboard.autoChooser.getSelected();
		System.out.println("Auto Logic INIT, startPos = "+ s.toString());
		
		if ((s != StartingPosition.STAY) && (s != StartingPosition.TEST)) {
			//if there is no game message (string) OR just cross auto line
			//System.out.println("Only Crossing Auto Line: GyroStraight((140/12), true)");
			addSequential(new RobotDriveStraight()); //forward 11 feet?
		} 
		
		switch (s) {
			case LEFT:
				//add left autonomus
				break;
			case CENTER:
				//add center autonomus
				return;
			case RIGHT:
				//add right autonomus
				return;
			case TEST:
				//add test autonomus
				return;
			default:
				return;
		}
	}
}
