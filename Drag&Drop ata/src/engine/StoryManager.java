package engine;

import characters.*;
import characters.Character;
import java.util.HashMap;
import java.util.Map;

public class StoryManager { // stroy manager ng buong characters and laro
    private Character player; // player character na pipiliin sa ui
    private int currentAct; // current act
    private Map<String, Boolean> flags; // para to sa mga desisyon ng user sa laro

    public StoryManager(Character player) { // kung ang ang pinili na character ni user yun ang lalabas sa laro at 1 ang
                                            // una act dito
        this.player = player; // player character na pipiliin sa ui
        this.currentAct = 1; // current act
        this.flags = new HashMap<>(); // para to sa mga desisyon ng user sa laro
    }

    public void setFlag(String flag, boolean value) { // para to sa mga desisyon ng user sa laro
        flags.put(flag, value);
    }

    public boolean getFlag(String flag) { // para to sa mga desisyon ng user sa laro
        return flags.getOrDefault(flag, false);
    }

    public void advanceAct() { // para to sa paglipat ng act
        currentAct++;
    }

    public int getCurrentAct() {
        return currentAct;
    } // para to sa paglipat ng act

    public Character getPlayer() {
        return player;
    } // player character na pipiliin sa ui

    public String determineMageEnding() { // para to sa ending ng mage
        return getCharacterEnding(player);
    }

    public String determineKnightEnding() { // para to sa ending ng knight
        return getCharacterEnding(player);
    }

    public String determinePriestEnding() { // para to sa ending ng priest
        return getCharacterEnding(player);
    }

    public String getCharacterEnding(Character character) { // para to sa ending ng character mga charcters
        if (character instanceof Eryn eryn) {
            if (eryn.getRawPowerCharges() == 0 || getFlag("eryn_used_raw_power_on_valdros")
                    || getFlag("eryn_used_raw_power_on_dael")) {
                return "BLIND"; // bad ending
            }
            return "REFORM"; // good ending
        }
        if (character instanceof Brennan || character instanceof Knight) {
            if (getFlag("brennan_killed_veyran_personally") || getFlag("caden_killed_veyran_personally")) {
                return "OUTCAST"; // bad ending
            }
            return "ARBITER"; // good ending
        }
        if (character instanceof Solia || character instanceof Priest) {
            if (getFlag("solia_destroyed_conduit")) {
                return "ASHES"; // bad ending
            }
            return "REBORN"; // good ending
        }
        if (character instanceof Mira) {
            if (getFlag("mira_chose_lena"))
                return "GHOST";
            if (getFlag("mira_sold_proof"))
                return "MERCHANT";
            return "EXPOSED"; // bad ending
        }
        if (character instanceof Sera) {
            if (getFlag("sera_lone_road"))
                return "LONE_ROAD";
            if (getFlag("sera_took_silence"))
                return "SILENCE";
            return "WITNESS"; // bad ending
        }
        return "SURVIVOR"; // good ending
    }
}
