import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

@ScriptManifest(author = "mkz", name = "kalastaja pro", version = 0.1, description = " kaloja naamaa", category = Category.WOODCUTTING)

public class kalastaja extends AbstractScript {

    @Override
    public void onStart() {
        log("Starting!");
    }

    @Override
    public int onLoop() {
        if (getInventory().isFull()) {
            getInventory().dropAll("Shrimp");
            sleep(300, 500);
        } else {
            NPC spot = getNpcs().closest("Fishing spot");
                spot.interact("Small Net");
                sleep(500, 600);
                sleepUntil(() -> !getLocalPlayer().isAnimating(), 15000);
        }
        return 300;
    }

    @Override
    public void onExit() {
        log("Stopping!");
    }
}