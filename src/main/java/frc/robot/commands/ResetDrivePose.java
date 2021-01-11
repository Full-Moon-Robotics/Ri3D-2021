package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * Sets drivetrain sensors to a default state.
 * <p>
 * Executes once before robot autonomous movement.
 */
public class ResetDrivePose extends InstantCommand {
    
    private Drivetrain m_drivetrain;

    /**
     * Creates a new ResetDrivePose.
     * @param dt The robot's drivetrain instance to be reset.
     * @see ResetDrivePose
     */
    public ResetDrivePose(Drivetrain dt) {
        super();
        m_drivetrain = dt;
    }

    /**
     * Executes the reset exactly once and within a single cycle.
     * 
     * @see edu.wpi.first.wpilibj2.command.InstantCommand
     */
    @Override
    public void execute() {
        m_drivetrain.resetPose();
    }

}
