/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class PlayerManager extends AbstractAppState {
  
  private AppStateManager   stateManager;
  private SimpleApplication app;    
  public  Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = stateManager;
    player            = new Player(stateManager);
    this.app.getRootNode().attachChild(player);
    player.rotate(0,89,0);
    player.setLocalTranslation(-1,5,0);
    }
  
  @Override
  public void update(float tpf) {
    
    Node blades = (Node) ((Node) stateManager.getState(WindmillManager.class).windmillNode.getChild(0)).getChild("Blades");
    
    CollisionResults results = new CollisionResults();
    
    blades.collideWith(((Node) player.model.getChild("Collider")).getWorldBound(), results);
    
    if (results.size() > 1) {
      player.die(stateManager);
      }
      
    if (!player.left && !player.right) {
      player.idle();
      }
    
    else {
      player.run();
      }
      
    }
    
  }
