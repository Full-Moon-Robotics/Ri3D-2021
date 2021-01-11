package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase{
    
    private CANSparkMax intakeMotor;
    
    public Intake(){

        super();
        intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR_ID, MotorType.kBrushed);
        intakeMotor.restoreFactoryDefaults();

        intakeMotor.setInverted(true);

    }

    public void run_intake(double speed) {
        if (intakeMotor != null) {
            intakeMotor.set(-speed);
        }
    }

}
