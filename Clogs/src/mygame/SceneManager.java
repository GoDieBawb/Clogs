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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Bob
 */
public class SceneManager extends AbstractAppState {
  
  private AppStateManager       stateManager;
  private SimpleApplication     app;
  private Node                  floorNode;
  private Node                  sceneNode;
  private Player                player;
  private AssetManager          assetManager;
  private float                 boxLength;
  private boolean               isAndroid;
    
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = stateManager;
    assetManager      = app.getAssetManager();
    sceneNode         = new Node();
    player            = stateManager.getState(PlayerManager.class).player;
    isAndroid         = "Dalvik".equals(System.getProperty("java.vm.name"));
    this.app.getRootNode().attachChild(sceneNode);
    initGround();
    initSky();
    }
  
  private void initGround() {
    Box box         = new Box(15,5,1);
    Geometry floor1 = new Geometry("Floor One", box);
    Geometry floor2 = new Geometry("Floor Two", box);
    Material mat    = assetManager.loadMaterial("Materials/Grass.j3m");
    floorNode       = new Node();
    boxLength       = box.xExtent;
    floorNode.attachChild(floor1);
    floorNode.attachChild(floor2);
    floor1.setMaterial(mat);
    floor2.setMaterial(mat);
    floor1.getMesh().scaleTextureCoordinates(new Vector2f(3,2f));
    floor2.getMesh().scaleTextureCoordinates(new Vector2f(3,2f));
    sceneNode.attachChild(floorNode);
    floor2.setLocalTranslation(boxLength*2,0,0);
    }
  
  public void initSky(){
    Box box      = new Box(5,5f,1);
    Geometry sky = new Geometry("Floor One", box);
    sky.setMaterial(assetManager.loadMaterial("Materials/Sky.j3m"));
    app.getRootNode().attachChild(sky);
    sky.setLocalTranslation(0,5,-2);
    }
  
  @Override
  public void update(float tpf) {
      
    if (player.left) {
      floorNode.getChild("Floor One").setLocalTranslation(floorNode.getChild("Floor One").getLocalTranslation().addLocal(4f*tpf,0,0));
      floorNode.getChild("Floor Two").setLocalTranslation(floorNode.getChild("Floor Two").getLocalTranslation().addLocal(4f*tpf,0,0));  
      }
    
    else if (player.right) {
      floorNode.getChild("Floor One").setLocalTranslation(floorNode.getChild("Floor One").getLocalTranslation().addLocal(-4f*tpf,0,0));
      floorNode.getChild("Floor Two").setLocalTranslation(floorNode.getChild("Floor Two").getLocalTranslation().addLocal(-4f*tpf,0,0));        
      }
    
    if (floorNode.getChild("Floor One").getLocalTranslation().x < (-boxLength*2)) {
      floorNode.getChild("Floor One").setLocalTranslation(boxLength*2,0,0);
      }

    if (floorNode.getChild("Floor Two").getLocalTranslation().x < (-boxLength*2)) {
      floorNode.getChild("Floor Two").setLocalTranslation(boxLength*2,0,0);
      }

    if (floorNode.getChild("Floor One").getLocalTranslation().x > (boxLength*2)) {
      floorNode.getChild("Floor One").setLocalTranslation(-boxLength*2,0,0);
      }

    if (floorNode.getChild("Floor Two").getLocalTranslation().x > (boxLength*2)) {
      floorNode.getChild("Floor Two").setLocalTranslation(-boxLength*2,0,0);
      }
      
    }
    
  }
