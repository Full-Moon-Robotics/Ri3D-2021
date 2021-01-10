package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    /**
     * Outtake.
     */

    private CANSparkMax flywheelMotor;
    //TODO set correct binding
    private final static int FLYWHEEL_BINDING = -1; 

    public Shooter(){
        super();

        flywheelMotor = new CANSparkMax(FLYWHEEL_BINDING, MotorType.kBrushed);
        flywheelMotor.restoreFactoryDefaults();

    }

}
