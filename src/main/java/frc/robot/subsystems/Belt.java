package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Provides an interface for the conveyor belt, which holds balls and loads the flywheel.
 */
public class Belt extends SubsystemBase{
    
    private CANSparkMax beltMotor;
    
    /**
     * Creates a new Belt and sets up the belt motor.
     * 
     * @see Belt
     */
    public Belt(){
        super();

        beltMotor = new CANSparkMax(Constants.BELT_MOTOR_ID, MotorType.kBrushed);
        beltMotor.restoreFactoryDefaults();
        beltMotor.setInverted(true);
    }

    /**
     * Sets a new angular velocity to run the motor at.
     * Ranges from -1.0 to 1.0, with 0 being no rotation.
     * @param speed the intended direction (sign) and percent of maximum motor speed (0 to 1) 
     */
    public void run_belt(double speed) {
        beltMotor.set(speed);
    }
    
}
