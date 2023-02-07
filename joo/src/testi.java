import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;

@ScriptManifest(author = "mkz", name = "testi walker", version = 0.1, description = "mennään kävelylle", category = Category.UTILITY)

public class testi extends AbstractScript {

    public static final int ADAMANT_AXE = 1357;

    private static final Tile BANK_TILE = new Tile(3183, 3437);
    private static final Tile TREE_TILE = new Tile(3165, 3420);

    @Override
    public int onLoop() {
        getWalking().walk(BANK_TILE);
        if (getPlayers().equals(BANK_TILE)) {
            GameObject bank = getGameObjects().closest("Bank booth");
            bank.interact("Bank");
            sleep(300, 500);
        }
        return 300;
    }
}