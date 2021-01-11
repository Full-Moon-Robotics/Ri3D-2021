package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

/**
 * Controls the flywheel shooter in order to score Power Cells.
 */
public class Shoot extends CommandBase {

    private Shooter m_shooter;

    /**
     * Creates a new Shoot.
     *
     * @param shooter The flywheel subsystem instance.
     * @see Shoot
     */
    public Shoot(Shooter shooter) {
        super();
        m_shooter = shooter;
        addRequirements(shooter);
    }

    /**
     * Marks the flywheel as enabled.
     * Called when the command is intially scheduled.
     */
    @Override
    public void initialize() {
        m_shooter.setFlywheelEnabled(true);
    }

    /**
     *  Adjusts the shooter toward the target rotations per minute.
     */
    @Override
    public void execute() {
        m_shooter.setTargetRpm(5600);
    }

    /**
     * Marks the flywheel as disabled.
     * Cancellation and interruption have the same behavior.
     */
    @Override
    public void end(boolean interrupted) {
        m_shooter.setFlywheelEnabled(false);
    }
    
    /**
     * Default behavior from {@link
     * edu.wpi.first.wpilibj2.command.Command#isFinished}
     */
    @Override
    public boolean isFinished() {
        return false;
    }
}
