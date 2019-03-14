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
     * Queries the NetworkTable
     * @param s The key to query for
     * @return The value in the NetworkTable, -1 if no value is found.
     */
    private static double getNetworkTableEntry(String s) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(s).getDouble(-1);
    }
}
