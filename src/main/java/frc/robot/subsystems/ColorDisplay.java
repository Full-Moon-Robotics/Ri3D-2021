/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorDisplay extends SubsystemBase {

  public enum ControlPanelWedge{
    BLUE("cyan"), GREEN, RED, YELLOW;
    
    char fmsCode = '\0';
    String colorString;
    private static Map<Character, ControlPanelWedge> map = new HashMap<>();

    static{
      for(ControlPanelWedge color : values()){
        map.put(color.fmsCode, color);
      }
    }

    ControlPanelWedge(){
      fmsCode = this.toString().charAt(0);
      colorString = this.toString().toLowerCase();
    }

    ControlPanelWedge(String colorString){
      this();
      this.colorString = colorString;
    }

    public ControlPanelWedge previous(){
      return values()[(this.ordinal()+values().length-1) % values().length];
    }

    public ControlPanelWedge next(){
      return values()[(this.ordinal()+1) % values().length];
    }

    String getDisplayName(){
      return colorString;
    }

    static ControlPanelWedge getFromFMSCode(char code){
      return map.get(code);
    }
    static ControlPanelWedge getFromFMSCode(String code){
      if(code == null || code.isEmpty()){
        return null;
      }
      return getFromFMSCode(code.charAt(0));
    }
  }

  SimpleWidget fmsColor;
  SimpleWidget detectedColor;
  SimpleWidget scoredColor;
  SimpleWidget rawPixel;
  private ControlPanelWedge fmsColorCache;
  /**
   * Creates a new ColorDisplay.
   */
  public ColorDisplay() {
    fmsColor = Shuffleboard.getTab("Tab 1").add("FMS Color", false).withWidget("Boolean Box");
    fmsColor.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "black"));
    fmsColor.getEntry().setBoolean(false);

    detectedColor = Shuffleboard.getTab("Tab 1").add("Detected Color", false).withWidget("Boolean Box");
    detectedColor.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "black"));
    detectedColor.getEntry().setBoolean(false);

    rawPixel = Shuffleboard.getTab("Tab 1").add("Raw Pixel", false).withWidget("Boolean Box");
    rawPixel.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "black"));
    rawPixel.getEntry().setBoolean(false);

    scoredColor = Shuffleboard.getTab("Tab 1").add("Scored Color", false).withWidget("Boolean Box");
    scoredColor.withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "black"));
    scoredColor.getEntry().setBoolean(false);

  }

  private void setWidget(SimpleWidget w, ControlPanelWedge color){
    if(color != null){
      w.withProperties(Map.of("colorWhenTrue", color.getDisplayName(), "colorWhenFalse", "black"));
    }
    w.getEntry().setBoolean(color != null);
  }

  private void setWidget(SimpleWidget w, String hex){
    w.withProperties(Map.of("colorWhenTrue", hex, "colorWhenFalse", "black"));
    w.getEntry().setBoolean(true);
  }

  public ControlPanelWedge getFMSColor(){
    return fmsColorCache;
  }

  public ControlPanelWedge fetchFMSColor(){
    ControlPanelWedge wedge = ControlPanelWedge.getFromFMSCode(DriverStation.getInstance().getGameSpecificMessage());
    setWidget(fmsColor, wedge);
    fmsColorCache = wedge;
    return wedge;
  }

  public void setDetectedColor(ControlPanelWedge color){
    setWidget(detectedColor, color);
  }

  public void setRawPixel(String color){
    setWidget(rawPixel, color);
  }

  public void setScoredColor(ControlPanelWedge color) {
    setWidget(scoredColor, color);
  }

  public void updateDetection(Integer[] colorsAndGuess){
    String hex = "#";
    for(int i = 0; i < 3; i++){
      hex += Integer.toHexString(colorsAndGuess[i]);
    }
    setRawPixel(hex);
    ControlPanelWedge detected = colorsAndGuess[3] == null ? null : ControlPanelWedge.values()[colorsAndGuess[3]];
    setDetectedColor(detected);
    setScoredColor(detected == null ? null: detected.next().next());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
