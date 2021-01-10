package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Belt extends SubsystemBase{
    
    private CANSparkMax beltMotor;
    
    public Belt(){
        super();

        beltMotor = new CANSparkMax(Constants.BELT_MOTOR_ID, MotorType.kBrushed);
        beltMotor.restoreFactoryDefaults();
        beltMotor.setInverted(true);
    }

    public void run_belt(double speed) {
        beltMotor.set(speed);
    }
    
}
