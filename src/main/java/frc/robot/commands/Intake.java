package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PowerCell;

public class Intake extends CommandBase {

    private PowerCell m_pPowerCell;

    public Intake(PowerCell pcell) {
        super();
        m_pPowerCell = pcell;
        addRequirements(pcell);
    }

    @Override
    public void initialize() {
       m_pPowerCell.run_intake(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        m_pPowerCell.run_intake(0.0);
    }

  
    @Override
    public boolean isFinished() {
        return false;
    }

}
