package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Belt;

/**
 * Changes the speed of the ball-holding conveyor belt.
 */
public class SetBeltSpeed extends InstantCommand {
    
    private double m_speed;
    private Belt m_belt;

    /**
     * Creates a new SetBeltSpeed.
     *
     * @param belt The conveyor belt between intake and shooter.
     * @param speed The target motor percent of maximum speed, between -1.0 and 1.0.
     * @see SetBeltSpeed
     */
    public SetBeltSpeed(Belt belt, double speed) {
        super();

        m_belt = belt;
        m_speed = speed;
        addRequirements(belt);
    }
    /**
     * Sets the target speed within a cycle.
     * 
     * @see edu.wpi.first.wpilibj2.command.InstantCommand
     */
    @Override
    public void execute() {
        m_belt.run_belt(m_speed);
    }
}
