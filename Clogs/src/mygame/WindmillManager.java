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
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class WindmillManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  public  Node              windmillNode;
  private Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    windmillNode      = new Node();
    player            = stateManager.getState(PlayerManager.class).player;
    this.app.getRootNode().attachChild(windmillNode);
    windmillNode.setLocalTranslation(1,6,-1.1f);
    initWindmills();
    }
  
  private void initWindmills() {
    Node windmill      = (Node) assetManager.loadModel("Models/Windmill.j3o");
    Material roofMat   = stateManager.getApplication().getAssetManager().loadMaterial("Materials/roof.j3m");
    Material buildMat  = stateManager.getApplication().getAssetManager().loadMaterial("Materials/stone.j3m");
    Material bladeMat  = stateManager.getApplication().getAssetManager().loadMaterial("Materials/shoes.j3m");
    ((Node) windmill.getChild("Building")).getChild("Building").setMaterial(buildMat);
    ((Node) windmill.getChild("Building")).getChild("Roof").setMaterial(roofMat);
    ((Node) windmill.getChild("Blades")).setMaterial(bladeMat);
    windmillNode.attachChild(windmill);
    resetMill();
    }
  
  public void resetMill(){
    windmillNode.getChild(0).setLocalTranslation(windmillNode.getChild(0).getLocalTranslation().addLocal(4.9f,0,0));  
    }
  
  @Override
  public void update(float tpf) {
    
    float difficulty = player.score*.25f;
    
    if (difficulty < 1) {
      difficulty = 1;
      }
    
    if (difficulty > 3) {
      difficulty = 3;
      }
      
    ((Node) windmillNode.getChild(0)).getChild("Blades").rotate(0,0,difficulty*tpf);
    
    if (player.left) {
      windmillNode.getChild(0).setLocalTranslation(windmillNode.getChild(0).getLocalTranslation().addLocal(4f*tpf,0,0));  
      }
    
    if (player.right) {
      windmillNode.getChild(0).setLocalTranslation(windmillNode.getChild(0).getLocalTranslation().addLocal(-4f*tpf,0,0));   
      }
    
    if (windmillNode.getChild(0).getLocalTranslation().x < -7) {
      windmillNode.getChild(0).setLocalTranslation(5,0,0);
      player.score++;
      }
    
    if (windmillNode.getChild(0).getLocalTranslation().x > 5) {
      windmillNode.getChild(0).setLocalTranslation(-5,0,0);
      player.score--;
      }
    
    }
  
  }
