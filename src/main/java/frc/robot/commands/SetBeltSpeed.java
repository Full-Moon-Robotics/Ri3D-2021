package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Belt;

public class SetBeltSpeed extends InstantCommand {
    
    private double m_speed;
    private Belt m_belt;

    public SetBeltSpeed(Belt belt, double speed) {
        super();

        m_belt = belt;
        m_speed = speed;
        addRequirements(belt);
    }

    @Override
    public void execute() {
        m_belt.run_belt(m_speed);
    }
}
