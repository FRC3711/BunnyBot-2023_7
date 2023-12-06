// Drive2Tag uses PhotonVision camera usb2 to locate the AprilTag.
// It start driveing at the commanded speed (drive value).
// when it captures tag it steers toward tag and slows down when out
// about 4 feet.  When it slows to the impact speed, it will continue
// for 2 seconds.  Hopefully colliding into tote and catching a bunny

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.subsystems.DriveTrain;

public class Drive2Tag extends CommandBase {
  /** Creates a new Drive2Tag. */
  private final DriveTrain m_driveTrain;

  PhotonCamera camera = new PhotonCamera("usb2"); 
  double turnLimit = 0.5;
  private double m_fwdLimit;
  double tagArea;

  public Drive2Tag(DriveTrain subsystem, double fwdLimit) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveTrain = subsystem;
    addRequirements(m_driveTrain);

    m_fwdLimit = fwdLimit;
  }

  double fwdDrive;
  int fidNumber;
 // double impactDrive = 0.35;  // set this to the desired tote impact speed.

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tagArea = 0;
    camera.setPipelineIndex(0); // pipeline 0=bucket
    fwdDrive = m_fwdLimit; // start at selected speed to start.
    fidNumber = (int)SmartDashboard.getNumber( "Fiducial #", 2);  // %r6
 //   SmartDashboard.pu.putNumber("fid1", fidNumber);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnDrive = 0;
    var result = camera.getLatestResult();
  
    // implement this code after other tests complete.
    PhotonTrackedTarget target;
    //SmartDashboard.putNumber("fid2", fidNumber);
    // the following is a test to find fiducial 11 if multiple fiducials detected.
    if (result.hasTargets()) {
      List<PhotonTrackedTarget> targets = result.getTargets();
      for (int idx = 0; idx < targets.size(); idx++) {
        if (targets.get(idx).getFiducialId() == fidNumber) { // %r6
          target = targets.get(idx);
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

          // the area will indicate how close we are to target. range about .1 to 40.
          // for HD-3000 camera the area is 10% at 2 feet
          tagArea = target.getArea(); // check size of tag
        }
      }
    }
    m_driveTrain.drive(fwdDrive, turnDrive);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveTrain.drive(0,0);  // stop
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (tagArea > 5)
       return true;  // stop when close to tag

    return false;
  }
}
