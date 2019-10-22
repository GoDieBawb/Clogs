/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.system.JmeSystem;
import com.jme3.texture.Texture;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bob
 */
public class Player extends Node {
  
  public  boolean     left;
  public  boolean     right;
  public  Node        model;
  private AnimControl animControl;
  public  AnimChannel armChannel;
  public  AnimChannel legChannel;
  public  int         score;
  public  int         highScore;
  public  boolean     isDead;
  
  public Player(AppStateManager stateManager) {
    model          = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Person/Person.j3o");
    animControl    = model.getChild("Person").getControl(AnimControl.class);
    armChannel     = animControl.createChannel();
    legChannel     = animControl.createChannel();
    highScore      = readScore(stateManager);
    Material mat   = new Material(stateManager.getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key = new TextureKey("Models/Person/Person.png", true);
    Texture tex    = stateManager.getApplication().getAssetManager().loadTexture(key);
    Material mat1  = stateManager.getApplication().getAssetManager().loadMaterial("Materials/shoes.j3m");
    
    ((Node) ((Node) model.getChild("Person")).getChild("RightLeg")).getChild("Clog").setMaterial(mat1);
    ((Node) ((Node) model.getChild("Person")).getChild("LeftLeg")).getChild("Clog").setMaterial(mat1);
    
    mat.setTexture("ColorMap", tex);    
    armChannel.addFromRootBone("TopSpine");
    legChannel.addFromRootBone("BottomSpine");
    attachChild(model);
    model.scale(.2f);
    armChannel.setAnim("ArmRun");
    legChannel.setAnim("LegRun");
    ((Node) model.getChild("Person")).getChild("PersonGeom").setMaterial(mat);
    }
  
  public void saveScore(int newScore, AppStateManager stateManager) {
    
    String filePath;  
      
    try {  
      filePath         = stateManager.getState(AndroidManager.class).filePath;
      }
    
    catch (NullPointerException e){
      filePath = JmeSystem.getStorageFolder().toString();
      }
    
    BinaryExporter exporter = BinaryExporter.getInstance();
    Node score              = new Node();
    score.setUserData("Name", "Hope");
    score.setUserData("Score", newScore);
    File file               = new File(filePath + "/score.j3o");
    
    System.out.println("Saving Score");
    
    try {
        
      exporter.save(score, file);  
      System.out.println("Score saved to: " + filePath);
        
      }
    
    catch (IOException e) {
        
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error: Failed to save game!", e);  
        System.out.println("Failure");
      }
    
      System.out.println("score completion");
    
    }
  
  public int readScore(AppStateManager stateManager) {
      
    String filePath;  
      
    try {  
      filePath         = stateManager.getState(AndroidManager.class).filePath;
      }
    
    catch (NullPointerException e){
      filePath = JmeSystem.getStorageFolder().toString();
      }
    
     AssetManager assetManager = stateManager.getApplication().getAssetManager();
     
     assetManager.registerLocator(filePath, FileLocator.class);
     
     Node newNode;
     int  score;
     
     try {
       newNode = (Node) assetManager.loadModel("score.j3o");
       score   = newNode.getUserData("Score");
       }
     
     catch (AssetNotFoundException ex) {
       saveScore(0, stateManager);
       score = 0;    
       }
     
     catch (IllegalArgumentException e) {
       saveScore(0, stateManager);
       score = 0;
       }
     
     
     System.out.println("You've loaded: " + score);
     return score;
     }
  
  public void die(AppStateManager stateManager) {
    rotate(0,0,89.99999f);
    isDead = true;
    left   = false;
    right  = false;
    stateManager.getState(WindmillManager.class).resetMill();
    stateManager.getState(InteractionManager.class).left  = false;
    stateManager.getState(InteractionManager.class).right = false;
    
    boolean isHigh = false;
    
    if (score > highScore) {
      highScore = score;
      isHigh    = true;
      saveScore(highScore, stateManager);
      }
    
    stateManager.getState(GuiManager.class).displayFinalScore(isHigh);
    }
  

  public void run(){
      
    if (!armChannel.getAnimationName().equals("ArmRun")){
      armChannel.setAnim("ArmRun");
      }
    
    if (!legChannel.getAnimationName().equals("LegRun")){
      legChannel.setAnim("LegRun");
      }
    
    }
  
  public void idle(){

    if (!armChannel.getAnimationName().equals("ArmIdle")){
      armChannel.setAnim("ArmIdle");
      }
    
    if (!legChannel.getAnimationName().equals("LegsIdle")){
      legChannel.setAnim("LegsIdle");
      }
    
    }
    
  }
