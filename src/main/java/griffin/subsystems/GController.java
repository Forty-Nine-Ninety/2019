package griffin.subsystems;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Base class for subsystems
 * 
 * @author MajikalExplosions
 */
public class GController extends Joystick {

	/**
	 * Initializes a new Controller
	 * @param port Driver station port number
	 */
	public GController(int port) {
		super(port);
	}

	//TODO put code that all controllers (should) have in common here

	//TODO write code that binds buttons to commands
}
