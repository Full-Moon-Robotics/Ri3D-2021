package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

public class ResetDrivePose extends InstantCommand {
    
    private Drivetrain m_drivetrain;

    public ResetDrivePose(Drivetrain dt) {
        super();
        m_drivetrain = dt;
    }

    @Override
    public void execute() {
        m_drivetrain.resetPose();
    }

}
