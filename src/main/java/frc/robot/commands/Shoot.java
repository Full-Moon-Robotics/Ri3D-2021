package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

/**
 * Controls the flywheel shooter in order to score Power Cells.
 */
public class Shoot extends CommandBase {

    private Shooter m_shooter;
    private Timer m_spinupTimer;

    /**
     * Creates a new Shoot.
     *
     * @param shooter The flywheel subsystem instance.
     * @see Shoot
     */
    public Shoot(Shooter shooter) {
        super();
        m_shooter = shooter;
        m_spinupTimer = new Timer();
        addRequirements(shooter);
    }

    /**
     * Marks the flywheel as enabled.
     * Called when the command is intially scheduled.
     */
    @Override
    public void initialize() {
        m_shooter.setFlywheelEnabled(true);
        m_spinupTimer.reset();
        m_spinupTimer.start();
    }

    /**
     *  Adjusts the shooter toward the target rotations per minute.
     *  Indexes if the flywheel is ready to shoot; otherwise waits until next cycle.
     */
    @Override
    public void execute() {
        m_shooter.setTargetRpm(SmartDashboard.getNumber("shooter_target_rpm", 0));
        if(m_spinupTimer.get() > 1.0) {
            // index
            m_shooter.setIndexer(0.5);
        } else {
            // stop indexing
            m_shooter.setIndexer(0);
        }
    }

    /**
     * Marks the flywheel as disabled.
     * Cancellation and interruption have the same behavior.
     */
    @Override
    public void end(boolean interrupted) {
        m_shooter.setFlywheelEnabled(false);
        m_shooter.setIndexer(-0.2);
        m_spinupTimer.stop();
        m_spinupTimer.reset();
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
