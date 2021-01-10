package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {

    private Shooter m_shooter;

    public Shoot(Shooter shooter) {
        super();
        m_shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        m_shooter.setFlywheelEnabled(true);
    }

    @Override
    public void execute() {
        m_shooter.setTargetRpm(5600);
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
