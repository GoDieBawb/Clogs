/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bob
 */
public class InteractionManager extends AbstractAppState implements ActionListener {

  public  SimpleApplication app;
  public  AppStateManager   stateManager;
  public  InputManager      inputManager;
  private Player            player;
  private int               resetDelay;
  public  boolean           left = false;
  public  boolean           right = false;
    
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.inputManager = this.app.getInputManager();
    player            = stateManager.getState(PlayerManager.class).player;
    inputManager.setSimulateMouse(true);
    setUpKeys();
    }
  
  //Sets left the keys
  public void setUpKeys(){
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Click");
    }
  
  //Listens for keys
  public void onAction(String binding, boolean isPressed, float tpf) {
    
  if (!player.isDead)  {
      
    if ((binding.equals("Left"))){
      left = isPressed;
      }
    
    else if(binding.equals("Right")){
      right = isPressed;
      }

    if (binding.equals("Click")) {
      Vector2f clickSpot = inputManager.getCursorPosition();
      float xSpot = clickSpot.getX();
      float ySpot = clickSpot.getY();
      Screen screen = stateManager.getState(GuiManager.class).screen;
      
      
      if (xSpot > screen.getWidth()/2)
        right = isPressed;
      
      else
        left  = isPressed;
      
      }
    
      }
  
  //When the player had died run this on action
  else {
    
    if (resetDelay > 3) {  
      player.rotate(0,0,-89.99999f);
      player.isDead = false;
      player.score  = 0;
      resetDelay    = 0;
      stateManager.getState(GuiManager.class).highscoreDisplay.hide();
      }
    
    else {
      resetDelay++;
      }
    
      } 
    
    }
  
  @Override
  public void update(float tpf) {
    
    if (left) {
      player.left  = true;
      player.right = false;
      }
    
    else if (right) {
      player.right = true;
      player.left  = false;
      }
    
    else {
      player.left  = false;
      player.right = false;
      }
      
    }
  
  }
