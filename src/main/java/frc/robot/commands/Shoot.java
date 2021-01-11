package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        m_shooter.setTargetRpm(SmartDashboard.getNumber("shooter_target_rpm", 0));
        if(m_shooter.isReadyToShoot()) {
            // index
        } else {
            // stop indexing
        }
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
