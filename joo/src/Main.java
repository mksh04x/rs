import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.methods.Calculations.random;

@ScriptManifest(author = "mkz", name = "puunhakkaaja pro", version = 0.1, description = " puita alas", category = Category.WOODCUTTING)

public class Main extends AbstractScript {

    @Override
    public void onStart() {
        log("Starting!");
    }

    @Override
    public int onLoop() {
        if (getInventory().isFull()) {
            getInventory().dropAll("Willow logs");
            sleep(300, 500);
        } else {
            GameObject tree = getGameObjects().closest("Willow");
            if (tree != null) {
                tree.interact("Chop down");
                sleep(1000, 3000);
                try {
                   antiban();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sleep(500, 600);
                sleepUntil(() -> !getLocalPlayer().isAnimating(), 15000);
            }
        }
        return Calculations.random(200,600);
    }

    @Override
    public void onExit () {
        log("Stopping!");
    }


    private void antiban () throws InterruptedException {
        if (getLocalPlayer().isAnimating()) {
            getTabs().open(Tab.SKILLS);
            sleep(100 + random(300, 700));
            getSkills().hoverSkill(Skill.WOODCUTTING);
            sleep(100 + random(3000, 4000));
            getTabs().open(Tab.INVENTORY);
            sleep(100 + random(500, 700));
            getMouse().move();
        }
    }
}