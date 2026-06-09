package story;

import characters.Mira;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class MiraAct1 {

    private Mira mira;
    private StoryManager story;

    public MiraAct1(Mira mira, StoryManager story) {
        this.mira = mira;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneGala();
        sceneDiscovery();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT I — ONE MORE JOB  |  Mira Cael");
        Printer.slowPrint("Caldenmere's lower city. Lena is eleven. The Blight-Cough has her lungs for two years.");
        Printer.slowPrint("Treatment exists. The money does not — unless tonight's job pays.");
        Printer.slowPrint("The Blight-Cough is Greying runoff. Those in power know. They manage it quietly.");
        InputHandler.waitForEnter();
    }

    private void sceneGala() {
        Printer.slowPrint("Lord Cassian Vael's Autumn Gala. You enter as Lady Corren of Ashfield.");
        Printer.slowPrint("Three hundred guests. One safe. One window of noise.");
        System.out.println("  1. Take the relic and leave  2. Search the study first  3. Plant a distraction");
        int c = InputHandler.getInt(1, 3);
        if (c >= 2) {
            Printer.slowPrint("Behind the study door: a map of the eastern territory — marked for the kingdom's war.");
            story.setFlag("mira_found_eastern_map", true);
        }
        if (!CombatSystem.startCombat(mira, Enemy.rogueArcanist())) { handleGameOver(); return; }
        InputHandler.waitForEnter();
    }

    private void sceneDiscovery() {
        Printer.slowPrint("The safe holds more than gold — a crystal pulsing with Greying energy.");
        Printer.slowPrint("A note: \"Conduit survey proceeds. Stage Two requires eastern access.\"");
        Printer.slowPrint("This map wasn't stolen for coin. It was stolen to hide where the wound began.");
        System.out.println("  1. Keep the map for Lena's cure  2. Copy it and return the original");
        if (InputHandler.getInt(1, 2) == 2) story.setFlag("mira_copied_map", true);
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printBox("ACT I COMPLETE — ONE MORE JOB");
        Printer.slowPrint("You have enough for Lena's passage — and a name: the Architect's eastern route.");
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The gala ends with you in chains. Lena waits without an answer.");
        System.exit(0);
    }
}
