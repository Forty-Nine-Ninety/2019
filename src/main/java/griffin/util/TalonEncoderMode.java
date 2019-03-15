package griffin.util;

/* FOR REFERENCE:
 * | Parameter            | Absolute Mode (SensorMode 0)      | Relative Mode (SensorMode 1)      |
 * | -------------------- | --------------------------------- | --------------------------------- |
 * | Update rate (period) | 4 ms                              | 100 us (microseconds?)            |
 * | Max RPM              | 7,500 RPM                         | 15,000 RPM                        |
 * | Accuracy             | 12 bits (4096 units per rotation) | 12 bits (4096 units per rotation) |
 * | Software API         | Select Pulse Width                | Select Quadrature                 |
 * (Both modes wrap from 4095 => 4096 => 4097 when increasing and 0 => -1 => -2 when decreasing)
*/

/**
 * Simple enum for magnetic encoder on talons
 * @author MajikalExplosions
 */
public enum TalonEncoderMode {
    Absolute,
    Relative
}