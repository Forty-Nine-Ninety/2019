package griffin.vision;

import edu.wpi.first.networktables.*;

/**
 * Wrapper class for Limelight
 * @author MajikalExplosions
 */
public class GLimelight {

    /**
     * Gets valid target
     * @return True if a valid target is found; false if otherwise.
     */
    public static boolean hasValidTarget() {
        return (getNetworkTableEntry("tv") == 1);
    }

    /**
     * Gets the horizontal offset from the crosshair to the target
     * @return The horizontal offset from the crosshair to the target
     */
    public static double getCrosshairHorizontalOffset() {
        return getNetworkTableEntry("tx");
    }

    /**
     * Gets the vertical offset from the crosshair to the target
     * @return The vertical offset from the crosshair to the target
     */
    public static double getCrosshairVerticalOffset() {
        return getNetworkTableEntry("ty");
    }

    /**
     * Returns the target area, a percentage of the screen filled by the target box.
     * @return The target area (0% of image to 100% of image)
     */
    public static double getTargetArea() {
        return getNetworkTableEntry("ta");
    }

    /**
     * Returns the skew/rotation of a target.
     * @return The skew or rotation
     */
    public static double getSkew() {
        return getNetworkTableEntry("ts");
    }

    /**
     * Returns the pipeline's latency contribution for non-image capture
     * @return The pipeline’s latency contribution in milliseconds
     */
    public static int getLatency() {
        return (int) Math.round(getNetworkTableEntry("ts"));
    }

    /**
     * Returns the pipeline's minimum latency contribution for image capture
     * @return The pipeline’s minimum latency contribution in milliseconds
     */
    public static int getImageCaptureLatency() {
        return (int) Math.round(getNetworkTableEntry("ts")) + 11;
    }

    /**
     * Sets the Limelight's LED state.
     * @param n Settings: 0 = default for pipeline, 1 = off, 2 = blink, 3 = on
     */
    public static void setLedMode(int n) {
        if (n < 0 || n > 4)
            return;
        setNetworkTableEntry("ledMode", n);
    }

    /**
     * Gets the Limelight's LED state.
     * @return The Limelight's current LED mode (0 = default for pipeline, 1 = off, 2 = blink, 3 = on)
     */
    public static int getLedMode() {
        return (int) getNetworkTableEntry("ledMode");
    }

    /**
     * Toggles the Limelight's LED mode between 0 and 1. If the current setting is 2 or 3, the code will change it to 1.
     */
    public static void toggleLedMode() {
        switch (getLedMode()) {
        case 1:
            setLedMode(0);
            break;
        default:// 0, 2, and 3
            setLedMode(1);
            break;
        }
    }

    /**
     * Sets the Limelight's Picture-in-picture state.
     * @param n Settings: 0 = default side by side, 1 = PiP Main, 2 = PiP Secondary
     */
    public static void setPiPMode(int n) {
        if (n < 0 || n > 2)
            return;
        setNetworkTableEntry("stream", n);
    }

    /**
     * Gets the Limelight's LED state.
     * @return The Limelight's current LED mode (0 = default side by side, 1 = PiP Main, 2 = PiP Secondary)
     */
    public static int getPiPMode() {
        return (int) getNetworkTableEntry("stream");
    }

    /**
     * Toggles the Limelight's PiP Mode. 0 => 1, 1 => 2, 2 => 0 (0 = default side byside, 1 = PiP Main, 2 = PiP Secondary)
     */
    public static void togglePiPMode() {
        setNetworkTableEntry("stream", (getPiPMode() + 1) % 3);
    }

    /**
     * Sets the Limelight's pipeline.
     * @param n Pipeline number between 0..9 inclusive.
     */
    public static void setPipeline(int n) {
        if (n < 0 || n > 9) return;
        setNetworkTableEntry("pipeline", n);
    }

    /**
     * Gets the Limelight's pipeline.
     * @return The currently selected pipeline number
     */
    public static int getPipeline() {
        return (int) getNetworkTableEntry("getpipe");
    }

    /**
     * Sets the Limelight's camMode. (0 = Vision Processing, 1 = Driver Camera
     * (Increases exposure, disables vision processing))
     */
    public static void setCamMode(int n) {
        setNetworkTableEntry("camMode", n);
    }

    //TODO perhaps add in values other than doubles

    /**
     * Queries the NetworkTable
     * 
     * @param s The key to query for
     * @return The value in the NetworkTable
     */
    private static double getNetworkTableEntry(String s) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(s).getDouble(-1);
    }

    /**
     * Sets a value in the NetworkTable
     * 
     * @param k The key to set
     * @param s The value to set the key to
     * @return The value in the NetworkTable
     */
    private static void setNetworkTableEntry(String k, double s) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry(k).setNumber(s);
    }
}
