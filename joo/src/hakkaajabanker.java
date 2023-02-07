import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.methods.Calculations.random;

@ScriptManifest(author = "mkz", name = "puunhakkaaja pro", version = 0.1, description = "puita alas ja puut pankkiin", category = Category.WOODCUTTING)

public class hakkaajabanker extends AbstractScript {

    public static final int ADAMANT_AXE = 1357;

    private static final Tile BANK_TILE = new Tile(3183, 3437);
    private static final Tile TREE_TILE = new Tile(3165, 3420);

    @Override
    public void onStart() {
        log("Starting!");
    }

    @Override
    public int onLoop() {
        if (getInventory().isFull()) {
            bank();
            sleep(1000, 2500);
        } else if (getBank().isOpen() && getInventory().isFull()) {
            kamatPankkii();
            sleep(1000, 2500);
        } else if (getBank().isOpen() && !getInventory().isFull() && TREE_TILE.distance() > 7) {
            poisPankista();
            sleep(1000, 2500);
        } else {
            GameObject tree = getGameObjects().closest("Oak");
            if (tree != null) {
                tree.interact("Chop down");
                sleep(300, 500);
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
    public void onExit() {
        log("Stopping!");
    }


    private void antiban() throws InterruptedException {
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

    private void bank() { // toimii, vaiha tile paikkaa
        getWalking().walk(BANK_TILE);
        getBank().open(BankLocation.VARROCK_WEST);
        sleep(300, 500);
    }

    private void kamatPankkii() { // ei toimi
        if (getInventory().contains("Oak logs")) {
            getBank().depositAllExcept(ADAMANT_AXE);
            getBank().close();
            getWalking().walk(TREE_TILE);
        }
    }

    private void poisPankista() { // emt toimiiko
        getBank().close();
        getWalking().walk(TREE_TILE);
    }
}