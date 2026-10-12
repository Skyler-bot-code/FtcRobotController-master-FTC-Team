package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Auto: Forward → SpinUp → Shoot → Right", group = "Test")
public class AutoRed extends LinearOpMode {




    private DcMotor frontLeft, frontRight, backLeft, backRight;




    private DcMotor intakeMotor1, intakeMotor2;
    private DcMotor shooterMotor1, shooterMotor2;


    static final double DRIVE_SPEED = 0.7;
    static final double TICKS_PER_INCH = 45.0;


    static final double INTAKE_POWER = 1.0;
    static final double SHOOTER_POWER = 0.60;


    @Override
    public void runOpMode() {




        frontLeft  = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRight = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeft   = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRight  = hardwareMap.get(DcMotor.class, "back_right_drive");


        intakeMotor1 = hardwareMap.get(DcMotor.class, "intake");
        intakeMotor2 = hardwareMap.get(DcMotor.class, "intake2");


        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooter1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooter2");




        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);


        intakeMotor1.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor2.setDirection(DcMotor.Direction.REVERSE);


        shooterMotor1.setDirection(DcMotor.Direction.FORWARD);
        shooterMotor2.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addLine("Auto Ready");
        telemetry.update();


        waitForStart();




        resetEncoders();


        int forwardTicks = (int)(40 * TICKS_PER_INCH);


        frontLeft.setTargetPosition(forwardTicks);
        frontRight.setTargetPosition(forwardTicks);
        backLeft.setTargetPosition(forwardTicks);
        backRight.setTargetPosition(forwardTicks);


        runToPosition(DRIVE_SPEED);
        waitForMotors();
        stopDriveMotors();




        shooterMotor1.setPower(SHOOTER_POWER);
        shooterMotor2.setPower(SHOOTER_POWER);




        sleep(5000);




        intakeMotor1.setPower(INTAKE_POWER);
        intakeMotor2.setPower(INTAKE_POWER);




        sleep(5000);




        shooterMotor1.setPower(0);
        shooterMotor2.setPower(0);
        intakeMotor1.setPower(0);
        intakeMotor2.setPower(0);




        resetEncoders();


        int strafeTicks = (int)(30 * TICKS_PER_INCH);


        frontLeft.setTargetPosition(strafeTicks);
        frontRight.setTargetPosition(-strafeTicks);
        backLeft.setTargetPosition(-strafeTicks);
        backRight.setTargetPosition(strafeTicks);


        runToPosition(DRIVE_SPEED);
        waitForMotors();
        stopDriveMotors();
    }




    private void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    private void runToPosition(double power) {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }


    private void waitForMotors() {
        while (opModeIsActive() &&
                (frontLeft.isBusy() || frontRight.isBusy()
                        || backLeft.isBusy() || backRight.isBusy())) {
            idle();
        }
    }


    private void stopDriveMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}

