/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapFont.Align;
import com.jme3.font.BitmapFont.VAlign;
import com.jme3.font.LineWrapMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.text.TextElement;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bob
 */
public class GuiManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  public  Screen            screen;
  private TextElement       scoreDisplay;
  public  TextElement       highscoreDisplay;
  private Player            player;
  private BitmapFont        font;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player            = stateManager.getState(PlayerManager.class).player;
    font              = this.assetManager.loadFont("Interface/Impact.fnt");
    screen            = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
    screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
    screen.setUseMultiTouch(true);
    this.app.getGuiNode().addControl(screen);
    this.app.getInputManager().setSimulateMouse(true);
    initScoreDisplay();
    initHighScoreDisplay();
    }
  
  //Sets up the score display
  private void initHighScoreDisplay(){
    highscoreDisplay = new TextElement(screen, "HighScoreDisplay", Vector2f.ZERO, new Vector2f(300,50), font) {
    @Override
    public void onUpdate(float tpf) { }
    @Override
    public void onEffectStart() { }
    @Override
    public void onEffectStop() { }
    };
    
    //Sets up the details of the score display
    highscoreDisplay.setIsResizable(false);
    highscoreDisplay.setIsMovable(false);
    highscoreDisplay.setTextWrap(LineWrapMode.NoWrap);
    highscoreDisplay.setTextVAlign(VAlign.Center);
    highscoreDisplay.setTextAlign(Align.Center);
    highscoreDisplay.setFontSize(screen.getHeight()/20);
 
    //Add the score display
    screen.addElement(highscoreDisplay);
    highscoreDisplay.setFontColor(ColorRGBA.Red);
    
    highscoreDisplay.setText("High Score: " + player.score);
    highscoreDisplay.setPosition(screen.getWidth()/2 - highscoreDisplay.getWidth()/2, screen.getHeight()/2 - highscoreDisplay.getHeight()/2 + screen.getHeight()/10);
    highscoreDisplay.hide();
    }
  

  //Sets up the score display
  private void initScoreDisplay(){
    scoreDisplay = new TextElement(screen, "ScoreDisplay", Vector2f.ZERO, new Vector2f(300,50), font) {
    @Override
    public void onUpdate(float tpf) { }
    @Override
    public void onEffectStart() { }
    @Override
    public void onEffectStop() { }
    };
    
    //Sets up the details of the score display
    scoreDisplay.setIsResizable(false);
    scoreDisplay.setIsMovable(false);
    scoreDisplay.setTextWrap(LineWrapMode.NoWrap);
    scoreDisplay.setTextVAlign(VAlign.Center);
    scoreDisplay.setTextAlign(Align.Center);
    scoreDisplay.setFontSize(screen.getHeight()/20);
 
    //Add the score display
    screen.addElement(scoreDisplay);
    scoreDisplay.setFontColor(ColorRGBA.Red);
    
    scoreDisplay.setText("Windmills Passed: " + player.score);
    scoreDisplay.setPosition(screen.getWidth() / 1.1f - scoreDisplay.getWidth()/2f, screen.getHeight() / 1.05f - scoreDisplay.getHeight()/2);
    }

  public void displayFinalScore(boolean isHigh) {
    highscoreDisplay.show();
    
    if (isHigh) {
      highscoreDisplay.setText("New HighScore: " + player.score);
      highscoreDisplay.setFontColor(ColorRGBA.Yellow);
      }
    
    else {
      highscoreDisplay.setText("HighScore: " + player.highScore);
      highscoreDisplay.setFontColor(ColorRGBA.Red);
      }
    
    scoreDisplay.setPosition(screen.getWidth()/2 - scoreDisplay.getWidth()/2, screen.getHeight()/2 - scoreDisplay.getHeight()/2 + screen.getHeight()/5);
    }
  
  private void scoreUpdate() {
    scoreDisplay.setText("Windmills Passed: " + player.score);
    scoreDisplay.setPosition(screen.getWidth() / 1.1f - scoreDisplay.getWidth()/1.5f, screen.getHeight() / 1.05f - scoreDisplay.getHeight()/2);
    }
  
  @Override
  public void update(float tpf){
    if (!player.isDead)  
    scoreUpdate();
    }
  
  }
