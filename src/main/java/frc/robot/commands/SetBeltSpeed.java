package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PowerCell;

public class SetBeltSpeed extends InstantCommand {
    
    private double m_speed;
    private PowerCell m_powerCell;

    public SetBeltSpeed(PowerCell pcell, double speed) {
        super();

        m_powerCell = pcell;
        m_speed = speed;
        addRequirements(pcell);
    }

    @Override
    public void execute() {
        m_powerCell.run_belt(m_speed);
    }
}
