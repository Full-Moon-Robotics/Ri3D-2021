/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
//import sun.awt.image.PixelConverter.ByteGray;
import frc.robot.subsystems.ColorDisplay.ControlPanelWedge;

import java.util.Arrays;


import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;


public class Vision {

    private ColorDisplay colorDisplay;
    private CvSink video;
    private UsbCamera camera;
    private CameraServer server;
    private VideoSource source;

    public Integer[] color;

    public Vision(ColorDisplay colorDisplay){


        camera = new UsbCamera("USB Camera 0", 0);
        camera.setWhiteBalanceManual(6500);
        camera.close();
        
        server = CameraServer.getInstance();
        source = server.startAutomaticCapture();


        video = new CvSink("USB Camera 0");
        video.setSource(source);

        this.colorDisplay = colorDisplay;
       
    }
    
    public Integer[] getColor(){
        Mat image = new Mat(video.getSource().getVideoMode().height, video.getSource().getVideoMode().width, 0);
        video.grabFrame(image);
        double[] currColor = image.get(image.rows()/2, image.cols()/2);
        
        System.out.println(Arrays.toString(currColor));
        if(currColor.length < 3){
            return null;
        }
        int r = (int)currColor[2];
        int g = (int)currColor[1];
        int b = (int)currColor[0];
        
        
        Integer guess = null;
        if (r > 128 && g < 128 && b < 90){
            guess = ControlPanelWedge.RED.ordinal(); //RED
        }
        if (r > 128 && g > 128 && b < 100){
            guess = ControlPanelWedge.YELLOW.ordinal(); //Yellow
        }
        if (r < 128 && g > 128 && b < 128){
            guess = ControlPanelWedge.GREEN.ordinal(); //GREEN
        }
        if (r < 128 && g > 100 && b > 128){
            guess = ControlPanelWedge.BLUE.ordinal(); //CYAN
        }
        color = new Integer[]{r,g,b,guess};
        System.out.println(Arrays.toString(color));
        colorDisplay.updateDetection(color);
        return color;
        //0123-null
        //BGRY-none
    }

}