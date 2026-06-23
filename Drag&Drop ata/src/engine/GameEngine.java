package engine;

import characters.*;
import characters.Character;
import story.*;
import util.InputHandler;
import util.Printer;

public class GameEngine {
    // dto nagaganap lahat ng story, desisyon, at endings ng laro
    private Character player;
    private StoryManager storyManager;

    public GameEngine(Character player, StoryManager storyManager) {
        this.player = player;
        this.storyManager = storyManager;
    }

    public void start() {
        try {
            showIntro();
            runStory(); // start ng game/story
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("SKIP_ENDING:")) {
                String endingKey = e.getMessage().substring("SKIP_ENDING:".length());
                Thread.interrupted(); // clear interrupted status
                showForcedEnding(endingKey);
            } else {
                throw e;
            }
        }
        
        Printer.printDivider();
        Printer.slowPrint("The journey concludes. Thank you for playing Aethermoor.");
        InputHandler.waitForEnter();
    }

    private void showIntro() {
        Printer.printDivider();
        Printer.slowPrint("═══════════════════════════════════════════════════");
        Printer.slowPrint("Kingdom of Thaalisia — ten years after the Greying.");
        Printer.slowPrint("Your journey begins. One wound. One conspiracy.");
        Printer.slowPrint("═══════════════════════════════════════════════════");
        Printer.printDivider();
        InputHandler.waitForEnter(); // Yung hint about sa buong story nung laro,
        // kung saan umiikot yung buong story
    }

    private void runStory() {
        if (player == null)
            return;
        Character leader = player; // dto nakalagay yung story nung bawat character
        // kung sino man ang pinili nung user sa ui, yun yung magsisimula ng story
        // dto rin nagaganap yung mga desisyon ng user sa laro
        // dto rin nagaganap yung mga ending ng laro

        if (leader instanceof Eryn || leader instanceof Mage) {
            runMageStory((Mage) leader);
        } else if (leader instanceof Brennan || leader instanceof Knight) {
            runKnightStory((Knight) leader);
        } else if (leader instanceof Solia || leader instanceof Priest) {
            runPriestStory((Priest) leader);
        } else if (leader instanceof Mira) {
            runMiraStory((Mira) leader);
        } else if (leader instanceof Sera) {
            runSeraStory((Sera) leader);
        } else {
            runKnightStory((Knight) leader);
        }
    }

    private void runMageStory(Mage mage) {
        new ErynAct1(mage, storyManager).play();
        new ErynAct2(mage, storyManager).play();
        new ErynAct3(mage, storyManager).play();
        showMageEnding(mage); // eryn voss story
    }

    private void runKnightStory(Knight knight) {
        new BrennanAct1(knight, storyManager).play();
        new BrennanAct2(knight, storyManager).play();
        new BrennanAct3(knight, storyManager).play();
        showKnightEnding(knight); // brennan ashvane story
    }

    private void runPriestStory(Priest priest) {
        new SoliaAct1(priest, storyManager).play();
        new SoliaAct2(priest, storyManager).play();
        new SoliaAct3(priest, storyManager).play();
        showPriestEnding(priest); // solia ren story
    }

    private void runMiraStory(Mira mira) {
        new MiraAct1(mira, storyManager).play();
        new MiraAct2(mira, storyManager).play();
        new MiraAct3(mira, storyManager).play();
        showMiraEnding(mira); // mira caen story
    }

    private void runSeraStory(Sera sera) {
        new SeraAct1(sera, storyManager).play();
        new SeraAct2(sera, storyManager).play();
        new SeraAct3(sera, storyManager).play();
        showSeraEnding(sera); // sera vesryn story
    }

    private void showForcedEnding(String endingKey) {
        if (endingKey.startsWith("MAGE_")) {
            showMageEnding(null, endingKey);
        } else if (endingKey.startsWith("KNIGHT_")) {
            showKnightEnding(null, endingKey);
        } else if (endingKey.startsWith("PRIEST_")) {
            showPriestEnding(null, endingKey);
        } else if (endingKey.startsWith("MIRA_")) {
            showMiraEnding(null, endingKey);
        } else if (endingKey.startsWith("SERA_")) {
            showSeraEnding(null, endingKey);
        }
    }

    private void showMageEnding(Mage mage) {
        showMageEnding(mage, null);
    }

    private void showMageEnding(Mage mage, String forcedEnding) {
        Printer.printTitle("EPILOGUE — ERYN VOSS");
        String ending = forcedEnding != null ? forcedEnding : storyManager.determineMageEnding();
        if (ending.equals("BLIND") || ending.equals("MAGE_BLIND")) {
            Printer.slowPrint("Eryn defeats Valdros. The raw power tears through her.");
            Printer.slowPrint("She wakes in darkness. Permanent. She will never cast again.");
            Printer.slowPrint("She writes down everything she knows. Every truth. Every name.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE BLIND SCHOLAR");
        } else {
            Printer.slowPrint("Eryn defeats Valdros — not alone, but with those who believed her.");
            Printer.slowPrint("She declines High Arcanist. She becomes Keeper of Accountability.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE REFORMER");
        } // possible good/bad endings, depende sa desisyon ng user sa buong game
    }

    private void showKnightEnding(Knight knight) {
        showKnightEnding(knight, null);
    }

    private void showKnightEnding(Knight knight, String forcedEnding) {
        Printer.printTitle("EPILOGUE — BRENNAN ASHVANE");
        String ending = forcedEnding != null ? forcedEnding : storyManager.determineKnightEnding();
        if (ending.equals("OUTCAST") || ending.equals("KNIGHT_OUTCAST")) {
            Printer.slowPrint("Brennan kills Veyran with his own hands in the great hall.");
            Printer.slowPrint("The Iron Vow brands him a criminal. He walks out at dawn. Free, and exiled.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE FREE MAN");
        } else {
            Printer.slowPrint("Veyran is arrested. Tried publicly. Found guilty.");
            Printer.slowPrint("Brennan refuses his rank back. He becomes the Vow's watchdog — their conscience.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE ARBITER");
        } // possible good/bad endings, depende sa desisyon ng user sa buong game
    }

    private void showPriestEnding(Priest priest) {
        showPriestEnding(priest, null);
    }

    private void showPriestEnding(Priest priest, String forcedEnding) {
        Printer.printTitle("EPILOGUE — SOLIA REN");
        String ending = forcedEnding != null ? forcedEnding : storyManager.determinePriestEnding();
        if (ending.equals("ASHES") || ending.equals("PRIEST_ASHES")) {
            Printer.slowPrint("Solia destroys the Pyre Conduit. And the Vault with it.");
            Printer.slowPrint("She walks out into the ash and begins again. From nothing.");
            Printer.pause(500);
            Printer.printBox("ENDING: FROM ASHES");
        } else {
            Printer.slowPrint("Solia purifies the Conduit. Aldran is imprisoned. The order is hers to lead.");
            Printer.slowPrint("First line of new doctrine: 'Faith is not certainty. Faith is showing up anyway.'");
            Printer.pause(500);
            Printer.printBox("ENDING: THE NEW FLAME");
        } // possible good/bad endings, depende sa desisyon ng user sa buong game
    }

    private void showMiraEnding(Mira mira) {
        showMiraEnding(mira, null);
    }

    private void showMiraEnding(Mira mira, String forcedEnding) {
        Printer.printTitle("EPILOGUE — MIRA CAEL");
        String ending = forcedEnding != null ? forcedEnding : storyManager.getCharacterEnding(mira);
        if (ending.equals("GHOST") || ending.equals("MIRA_GHOST")) {
            Printer.slowPrint("Mira vanishes with Lena into clean country air. The ledgers burn unread.");
            Printer.printBox("ENDING: THE GHOST");
        } else if (ending.equals("MERCHANT") || ending.equals("MIRA_MERCHANT")) {
            Printer.slowPrint("The proof sells to the highest bidder. Lena lives. The machine keeps running.");
            Printer.printBox("ENDING: THE PRICE PAID");
        } else {
            Printer.slowPrint("The cover-up breaks in open court. Lena breathes. The lower city learns why.");
            Printer.printBox("ENDING: TRUTH IN THE OPEN");
        } // possible good/bad endings, depende sa desisyon ng user sa buong game
    }

    private void showSeraEnding(Sera sera) {
        showSeraEnding(sera, null);
    }

    private void showSeraEnding(Sera sera, String forcedEnding) {
        Printer.printTitle("EPILOGUE — SERA CALDWELL");
        String ending = forcedEnding != null ? forcedEnding : storyManager.getCharacterEnding(sera);
        if (ending.equals("LONE_ROAD") || ending.equals("SERA_LONE_ROAD")) {
            Printer.slowPrint("Sera walks the eastern roads alone. No commission. No crown. Only the compass.");
            Printer.printBox("ENDING: THE LONE ROAD");
        } else if (ending.equals("SILENCE") || ending.equals("SERA_SILENCE")) {
            Printer.slowPrint("She takes the payment. Corvin stays a footnote. The war continues.");
            Printer.printBox("ENDING: THE QUIET BETRAYAL");
        } else {
            Printer.slowPrint("Her testimony stops the summit. The engineered war stutters. Integrity survives.");
            Printer.printBox("ENDING: THE WITNESS");
        } // possible good/bad endings, depende sa desisyon ng user sa buong game
    }
}
