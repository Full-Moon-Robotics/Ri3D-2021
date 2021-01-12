package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Provides an interface for the flywheel, which launches Power Cells for scoring.
 */
public class Shooter extends SubsystemBase {

    private CANSparkMax m_flywheelMotor;
    private CANSparkMax m_indexMotor;
    
    private CANEncoder m_flywheelEncoder;

    private CANPIDController m_flywheelPID;
    private SimpleMotorFeedforward m_flywheelFF;

    private boolean flywheelEnabled;
    private double targetRpm;

    /**
     * Creates a new Shooter, preparing the flywheel motor and PID
     * 
     * @see Shooter
     */
    public Shooter() {
        super();

        m_flywheelMotor = new CANSparkMax(Constants.FLYWHEEL_MOTOR_ID, MotorType.kBrushless);
        m_flywheelMotor.restoreFactoryDefaults();
        m_flywheelMotor.setIdleMode(IdleMode.kCoast);
        m_flywheelMotor.setInverted(true);
        m_flywheelMotor.setSmartCurrentLimit(40);
        
        m_indexMotor = new CANSparkMax(Constants.INDEX_MOTOR_ID, MotorType.kBrushed);
        m_indexMotor.restoreFactoryDefaults();
        m_indexMotor.setIdleMode(IdleMode.kBrake);

        m_flywheelEncoder = m_flywheelMotor.getEncoder();
        m_flywheelEncoder.setVelocityConversionFactor(1.375);

        m_flywheelPID = m_flywheelMotor.getPIDController();
        m_flywheelPID.setP(0.000558);
        
        m_flywheelFF = new SimpleMotorFeedforward(Constants.FLYWHEEL_KS, Constants.FLYWHEEL_KV);
        
        flywheelEnabled = false;
        targetRpm = 0;


        m_indexMotor.restoreFactoryDefaults();
        m_indexMotor.setInverted(true);
        m_indexMotor.setSmartCurrentLimit(20);
        m_indexMotor.setIdleMode(IdleMode.kBrake);
    }

    public boolean isFlywheelEnabled() {
        return flywheelEnabled;
    }

    public void setFlywheelEnabled(boolean flywheelEnabled) {
        this.flywheelEnabled = flywheelEnabled;
    }

    public double getTargetRpm() {
        return targetRpm;
    }

    public void setTargetRpm(double targetRpm) {
        this.targetRpm = targetRpm;
    }

    /**
     * Checks if the current flywheel speed is within an acceptance range to shoot consistently.
     *
     * @return if the flywheel is at the right RPM to shoot
     */
    public boolean isReadyToShoot() {
        return flywheelEnabled && Math.abs(m_flywheelEncoder.getVelocity() - targetRpm) < Constants.FLYWHEEL_TOLERANCE;
    }
    /**
     * Sets a new angular velocity to run the indexing motor at.
     * Ranges from -1.0 to 1.0, with 0 being no rotation.
     * 
     * @param speed the intended direction (sign) and percent of maximum motor speed (0 to 1) 
     */
    public void setIndexer(double speed) {
        m_indexMotor.set(speed);
    }

    /**
     * Every period, updates the flywheel feed forward values for the controller and updates statistics.
     * Stops the motor if the flywheel is disabled.
     */
    @Override
    public void periodic() {
        if(flywheelEnabled) {
            double ff = m_flywheelFF.calculate(targetRpm/60.0);
            m_flywheelPID.setReference(targetRpm, ControlType.kVelocity, 0, ff, CANPIDController.ArbFFUnits.kVoltage);
        } else {
            m_flywheelMotor.stopMotor();
        }

        SmartDashboard.putNumber("shooter_rpm", m_flywheelEncoder.getVelocity());
        SmartDashboard.putNumber("shooter_err", m_flywheelEncoder.getVelocity() - targetRpm);
    }

}
