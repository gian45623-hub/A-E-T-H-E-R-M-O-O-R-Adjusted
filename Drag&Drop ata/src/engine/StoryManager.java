package engine;

import characters.*;
import characters.Character;
import java.util.HashMap;
import java.util.Map;

public class StoryManager {
    private Character player;
    private int currentAct;
    private Map<String, Boolean> flags;

    public StoryManager(Character player) {
        this.player = player;
        this.currentAct = 1;
        this.flags = new HashMap<>();
    }

    public void setFlag(String flag, boolean value) {
        flags.put(flag, value);
    }

    public boolean getFlag(String flag) {
        return flags.getOrDefault(flag, false);
    }

    public void advanceAct() {
        currentAct++;
    }

    public int getCurrentAct() { return currentAct; }
    public Character getPlayer() { return player; }

    public String determineMageEnding() {
        return getCharacterEnding(player);
    }

    public String determineKnightEnding() {
        return getCharacterEnding(player);
    }

    public String determinePriestEnding() {
        return getCharacterEnding(player);
    }

    public String getCharacterEnding(Character character) {
        if (character instanceof Eryn eryn) {
            if (eryn.getRawPowerCharges() == 0 || getFlag("eryn_used_raw_power_on_valdros")
                    || getFlag("eryn_used_raw_power_on_dael")) {
                return "BLIND";
            }
            return "REFORM";
        }
        if (character instanceof Brennan || character instanceof Knight) {
            if (getFlag("brennan_killed_veyran_personally") || getFlag("caden_killed_veyran_personally")) {
                return "OUTCAST";
            }
            return "ARBITER";
        }
        if (character instanceof Solia || character instanceof Priest) {
            if (getFlag("solia_destroyed_conduit")) {
                return "ASHES";
            }
            return "REBORN";
        }
        if (character instanceof Mira) {
            if (getFlag("mira_chose_lena")) return "GHOST";
            if (getFlag("mira_sold_proof")) return "MERCHANT";
            return "EXPOSED";
        }
        if (character instanceof Sera) {
            if (getFlag("sera_lone_road")) return "LONE_ROAD";
            if (getFlag("sera_took_silence")) return "SILENCE";
            return "WITNESS";
        }
        return "SURVIVOR";
    }
}
