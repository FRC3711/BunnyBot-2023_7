// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.List;

import javax.net.ssl.CertPathTrustManagerParameters;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.subsystems.DriveTrain;

public class Drive2Tag extends CommandBase {
  /** Creates a new Drive2Tag. */
  private final DriveTrain m_driveTrain;

  PhotonCamera camera = new PhotonCamera("usb2"); // %rod
  double turnLimit = 0.5;
  private double m_fwdLimit;

  public Drive2Tag(DriveTrain subsystem, double fwdLimit) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveTrain = subsystem;
    addRequirements(m_driveTrain);

    m_fwdLimit = fwdLimit;

  }

  double fwdDrive;
  int fidNumber;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
//    camera.setDriverMode(false);
    camera.setPipelineIndex(0); // pipeline 0=bucket
    fwdDrive = m_fwdLimit; // start at selected speed to start.
    fidNumber = (int)SmartDashboard.getNumber( "Fiducial #", 11);  // %r6
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

          // the area will indicate how close we are to target. range for .1 to 40.
          double area = target.getArea(); // check up/down position

          // the following is a kludge, but it may work.
          area /= 4; // 15>>10 %r5; // this will reduce drive to 0 at xxx area
          fwdDrive = 1 - area; // reduce drive to 1/2 if area = 15 / 30
          if (fwdDrive > m_fwdLimit) // limit the drive to +/- 0.5
            fwdDrive = m_fwdLimit;
          else if (fwdDrive < 0)  // %r6
            fwdDrive = 0; // do not go backwards
          // if (Math.abs(fwdDrive) < 0.35) // if drive is less than 0.1 do nothing
          //   fwdDrive = 0.35; // creep forward

         }
      }
    }
    m_driveTrain.drive(fwdDrive, turnDrive);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
