// added Relay 11/18/23, but it only drives a 5v relay.  
// selected camera pipeline 1 = Bucket.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DigitalOutput;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.subsystems.DriveTrain;

public class Drive2Bucket extends CommandBase {
 
  private final DriveTrain m_driveTrain;

  PhotonCamera camera = new PhotonCamera("usb1"); // %rod
  double turnLimit = 0.7;
  private double m_fwdLimit;
  private DigitalOutput LEDRing;


  public Drive2Bucket(DriveTrain subsystem, double fwdLimit) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveTrain = subsystem;
    addRequirements(m_driveTrain);

    m_fwdLimit = fwdLimit;
    LEDRing = new DigitalOutput(9);
  }

  double fwdDrive;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  //  camera.setDriverMode(false);
    camera.setPipelineIndex(0); // pipeline 0=bucket, 1=tag targeting
    LEDRing.set(true);
  //  fwdDrive = m_fwdLimit; // start at selected speed to start.
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnDrive = 0;
    fwdDrive = 0.1;  // used to show command is active
    var result = camera.getLatestResult();

    PhotonTrackedTarget target;

    if (result.hasTargets()) {
      target = result.getBestTarget();
      double yaw = target.getYaw();
      // the yaw can vary from -25 to +25 degrees.
      // make turnDrive vary from -2.5 to 2.5
      turnDrive = yaw / 10;
      if (turnDrive > turnLimit)// limit the drive to +/- 0.5
        turnDrive = turnLimit;
      else if (turnDrive < -turnLimit)
        turnDrive = -turnLimit;
      // if (Math.abs(turnDrive) < 0.1) // if drive is less than 0.1 do nothing
      // turnDrive = 0;

      // the vertical position will indicate how close we are to target. range for .1 to 40.
      double pitch = target.getPitch(); // check up/down position

      // the pitch can vary from -20 to +20 degrees.
      // make Drive vary from -2.0 to 2.0
      fwdDrive = pitch / 10;
 
      if (fwdDrive > m_fwdLimit) // limit fwd drive
        fwdDrive = m_fwdLimit;
      else if (fwdDrive < -m_fwdLimit)  // limit back drive
      fwdDrive = -m_fwdLimit;
      // if pitch is close to 2 degrees +/- 3  
     // if (Math.abs(pitch - 2) < 3)  
     //   fwdDrive = 0.0; // stop

    }
    m_driveTrain.drive(fwdDrive, turnDrive);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    LEDRing.set(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
