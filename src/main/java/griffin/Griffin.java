package griffin;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.command.Scheduler;
import griffin.subsystems.GController;
import griffin.subsystems.GSubsystem;
import griffin.util.GameMode;

/**
 * Pretty-ish slightly more user and non-programmer friendly wrapper for WPILib
 * @author MajikalExplosions
 * @version 0.1.0
 * @see <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">Neither here nor there.</a>
 */
public class Griffin {

    private static ArrayList<Griffin> griffins = new ArrayList<>();
    
    private HashMap<String, GSubsystem> subsystems;
    private HashMap<String, GController> controllers;

    /**
     * Creates a new Griffin object
     */
    public Griffin() {
        subsystems = new HashMap<>();
        controllers = new HashMap<>();
        griffins.add(this);
        System.out.println("[Griffin] Instance #" + (griffins.size() - 1) + " Initialized.");
    }

    //Griffin

    /**
     * Returns the default Griffin object
     * @return The default Griffin object
     */
    public static Griffin getGriffin() {
        return getGriffin(0);
    }

    /**
     * Returns the n-th Griffin object created
     * @param index The index of the Griffin object
     * @return The n-th Griffin object created
     */
    public static Griffin getGriffin(int index) {
        return griffins.get(index);
    }

    //Subsystems
    //TODO think about switching all those GSubsystem references to strings and using Class.forName(<classname>).newInstance() or something similar

    /**
     * Returns the subsystem with key k
     * @param k The key of the subsystem
     * @return The subsystem with key k
     */
    public GSubsystem getSubsystem(String k) {
        return subsystems.get(k);
    }

    /**
     * Adds a subsystem
     * @param key The key to associate with the subsystem
     * @param subsystem The subsystem to add
     * @return True when the subsystem was successfully added, false if not
     */
    public boolean addSubsystem(String key, GSubsystem subsystem) {
        if (containsSubsystem(key)) return false;
        subsystems.put(key, subsystem);
        return true;
    }

    /**
     * Queries for the existence of a subsystem
     * @param key The key of the subsystem
     * @return True if the subsystem was found, false if not
     */
    public boolean containsSubsystem(String key) {
        return subsystems.containsKey(key);
    }

    //Controllers

    /**
     * Returns the controller with key k
     * @param k The key of the controller
     * @return The controller with key k
     */
    public GController getController(String k) {
        return controllers.get(k);
    }

    /**
     * Adds a controller
     * @param key The key to associate with the controller
     * @param controller The controller to add
     * @return True when the controller was successfully added, false if not
     */
    public boolean addController(String key, GController controller) {
        if (containsController(key)) return false;
        controllers.put(key, controller);
        return true;
    }

    /**
     * Queries for the existence of a controller
     * @param key The key of the controller
     * @return True if the controller was found, false if not
     */
    public boolean containsController(String key) {
        return controllers.containsKey(key);
    }

    //Robot

    /**
     * Initializes Griffin for a game mode
     * @param m The game mode to initialize
     */
    public void initialize(GameMode m) {
        System.out.println("[Griffin] Initialized " + m.toString());
        switch(m) {
            case Disabled:
                Scheduler.getInstance().removeAll();
                break;
            case Autonomous://TODO add auto command
                break;
            case Teleop://TODO add teleop command
                break;
            case Test://TODO add test command
                break;
        }
    }

    /**
     * Runs periodic function for a game mode
     * @param m The game mode the robot is in
     */
    public void periodic(GameMode m) {
        switch(m) {
            case Disabled:
                break;
            case Autonomous:
                Scheduler.getInstance().run();
                break;
            case Teleop:
                Scheduler.getInstance().run();
                break;
            case Test:
                Scheduler.getInstance().run();
                break;
        }
    }
}