package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.Drivetrain;

public class DrivePath extends RamseteCommand {

    public DrivePath(Trajectory trajectory, Drivetrain drivetrain) {
        super(trajectory, drivetrain::getPose, new RamseteController(), drivetrain.getKinematics(),
                (leftSpeed, rightSpeed) -> {
                    drivetrain.driveClosedLoop(new DifferentialDriveWheelSpeeds(leftSpeed, rightSpeed));
                }, drivetrain);
    }

}
