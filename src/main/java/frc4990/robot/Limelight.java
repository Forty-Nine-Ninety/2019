package frc4990.robot;

import edu.wpi.first.networktables.*;

/**
 * Wrapper class for Limelight
 * 
 * @author MajikalExplosions
 */
public class Limelight {

    /**
     * Creates a new Limelight object
     * @deprecated
     */
    public Limelight() {
        //empty for now I'm not sure what to put here yet. Most of the other functions *are* static.
    }

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
     * Returns the target area, a percentage of the screen filled by the target box
     * See this part of the documentation: http://docs.limelightvision.io/en/latest/vision_pipeline_tuning.html#target-area
     * @return The target area (0% of image to 100% of image)
     */
    public static double getTargetArea() {
        return getNetworkTableEntry("ta");
    }

    /**
     * Returns the skew/rotation of a target. (basically direction filter)
     * see here for direction filter example https://giant.gfycat.com/HalfUnselfishHarvestmen.gif
     * @return The skew or rotation (-90 degrees to 0 degrees)
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
        return (int) Math.round(getNetworkTableEntry("ts")) + 11;//See http://docs.limelightvision.io/en/latest/networktables_api.html
    }

    /**
     * Queries the NetworkTable
     * @param s The key to query for
     * @return The value in the NetworkTable
     */
    private static double getNetworkTableEntry(String s) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(s).getDouble(0);
    }
}
