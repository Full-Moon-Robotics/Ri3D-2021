package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {

    private Shooter m_shooter;
    private int m_rpm;

    public Shoot(Shooter shooter, int rpm) {
        super();
        m_shooter = shooter;
        m_rpm = rpm;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        m_shooter.setFlywheelEnabled(true);
    }

    @Override
    public void execute() {
        m_shooter.setTargetRpm(m_rpm);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.setFlywheelEnabled(false);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
}
