package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
    getCamera().setLocation(new Vector3f(0,5f,5));
    flyCam.setEnabled(false);
    this.setShowSettings(false);
    this.setDisplayFps(false);
    this.setDisplayStatView(false);
    stateManager.attach(new PlayerManager());
    stateManager.attach(new SceneManager());
    stateManager.attach(new GuiManager());
    stateManager.attach(new InteractionManager());
    stateManager.attach(new WindmillManager());
    stateManager.attach(new AudioManager());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
