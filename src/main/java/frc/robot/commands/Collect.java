package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

/**
 * Controls the intake that is used to collect Power Cells.
 * <p>
 * See game manual section 7.2.2 POWER CELL Interaction for Power Cell control.
 */
public class Collect extends CommandBase {

    private Intake m_intake;

    /**
     * Creates a new Collect.
     * 
     * @param intake The subsystem for intake motors.
     * @see Collect
     */
    public Collect(Intake intake) {
        super();
        m_intake = intake;
        addRequirements(m_intake);
    }

    /**
     * Sets the intake to run continuously.
     * Called when the command is initially scheduled.
     */
    @Override
    public void initialize() {
       m_intake.run_intake(0.8);
    }

    /**
     * Sets the intake to stop spinning.
     * Cancellation and interruption have the same behavior.
     */
    @Override
    public void end(boolean interrupted) {
        m_intake.run_intake(0.0);
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
