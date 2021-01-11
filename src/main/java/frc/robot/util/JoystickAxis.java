package frc.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickAxis {

    private double m_rate = 1.0;
    private double m_expo = 0.0;
    private double m_superRate = 0.0;
    private double m_deadband = 0.01;

    private Joystick m_controller;
    private int m_axis;
    
    /**
     * Representation of a joystick's axis that applies RaceFlight axis rates along with properly scaled deadband.
     * @param controller The controller/joystick the axis is from.
     * @param axis The zero-indexed axis id to use.
     * @param rate A general scalar on the output.
     * @param expo A percentage representing how exponential the axis response is.
     * @param superRate Amplifies the extreme ends of the axis response.
     * @param deadband Clamps the axis to zero when within this range of zero, scaling each side of the axis response appropriately.
     */
    public JoystickAxis(Joystick controller, int axis, double rate, double expo, double superRate, double deadband) {
        m_controller = controller;
        m_axis = axis;
        m_rate = rate;
        m_expo = expo;
        m_superRate = superRate;
        m_deadband = deadband;
    }

    /**
     * Gets the current value of this axis.
     */
    public double get() {
        double command = m_controller.getRawAxis(m_axis);

        // compute deadband
        if(command > m_deadband) {
            command = (1 / (1 - m_deadband)) * command - (m_deadband / (1 - m_deadband));
        } else if (command < -m_deadband) {
            command = (1 / (1 - m_deadband)) * command + (m_deadband / (1 - m_deadband));
        } else {
            command = 0;
        }

        // compute exponential gains
        double retval = ((1 + 0.01 * m_expo * (command * command - 1.0)) * command);

        // compute rates
        retval = (retval * (m_rate + (Math.abs(retval) * m_rate * m_superRate * 0.01)));

        return retval;
    }
}
