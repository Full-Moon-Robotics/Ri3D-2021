package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class Collect extends CommandBase {

    private Intake m_intake;

    public Collect(Intake intake) {
        super();
        m_intake = intake;
        addRequirements(m_intake);
    }

    @Override
    public void initialize() {
       m_intake.run_intake(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.run_intake(0.0);
    }

  
    @Override
    public boolean isFinished() {
        return false;
    }

}
