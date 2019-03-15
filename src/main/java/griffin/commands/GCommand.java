package griffin.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Base class for commands
 * @author MajikalExplosions
 */
public class GCommand extends Command {
    //TODO figure out what code all commands have in common
    public GCommand() {}

    @Override
	public void initialize() {}

    @Override
	public void execute() {}
    
    @Override
	public void end() {}
    
    @Override
	public void interrupted() { end(); }
    
    @Override
	public boolean isFinished() {
        if (isTimedOut()) return true;
		return this.isTimedOut();
	}
}
