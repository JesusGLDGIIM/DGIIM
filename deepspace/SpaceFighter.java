/*
 * @author: Jesús García León
 * @file: 
 */
package deepspace;

/**
 *
 * @author usuario
 */
public interface SpaceFighter {
    public float fire();
    public float protection();
    public ShotResult receiveShot(float shot);
}
