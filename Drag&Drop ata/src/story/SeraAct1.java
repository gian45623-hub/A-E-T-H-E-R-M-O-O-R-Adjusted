package story;

import characters.Sera;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class SeraAct1 {

    private Sera sera;
    private StoryManager story;

    public SeraAct1(Sera sera, StoryManager story) {
        this.sera = sera;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        scenePatrol();
        sceneAmbush();
        sceneAftermath();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT I — THE VOSS COMPASS  |  Sera Caldwell");
        Printer.slowPrint("Seven years on Thaalisia's eastern patrol taught you to hear the land before it moves.");
        Printer.slowPrint("Three years of contract work kept the skill sharp — and the guilt sharper.");
        Printer.slowPrint("You know these hills. You know the exact way the pine needles smell before it rains.");
        Printer.slowPrint("You know how the Greying makes the shadows stretch too far at twilight.");
        Printer.pause(400);
        Printer.slowPrint("You have been trying not to remember this place.");
        Printer.slowPrint("But the coin from the Valdenmere Surveyor's Guild was too good to pass up.");
        InputHandler.waitForEnter();
    }

    private void scenePatrol() {
        Printer.printDivider();
        Printer.slowPrint("You guide a survey party of five scholars toward the ruins in the eastern scar.");
        Printer.slowPrint("They are loud. Too loud for this far east. They treat the Greying like a museum exhibit.");
        Printer.slowPrint("Pell, a junior researcher, is constantly tripping over roots.");
        Printer.pause(400);
        Printer.slowPrint("He stumbles again. But this time, it's not a root.");
        Printer.slowPrint("Your bow is up and drawn, a steel-tipped arrow nocked, before he even hits the ground.");
        Printer.slowPrint("You stare into the brush. Nothing moves.");
        Printer.pause(400);
        Printer.slowPrint("You lower the bow slightly and inspect what he tripped on. A wire.");
        Printer.slowPrint("An old trap design. But the wire itself... it catches the sunlight. Unrusted.");
        Printer.slowPrint("Someone set this up recently. And they are watching right now.");
        story.setFlag("sera_noticed_fresh_trap", true);

        System.out.println("  1. \"Everyone, stay completely quiet. We're being hunted.\"");
        System.out.println("  2. \"Everyone, move back, slowly. Keep your eyes on the trees.\"");
        System.out.println("  3. Say nothing. Keep your bow trained on the treeline.");
        int choice = InputHandler.getInt(1, 3);

        Printer.slowPrint("The silence stretches. Then, a rustle of leaves.");
        if (choice == 1) {
            Printer.slowPrint(
                    "Your warning saves Pell's life. A crossbow bolt thunks into the tree where his head just was.");
            Printer.slowPrint(
                    "Pell jumps, eyes wide, clutching his throat. 'By the Saints... I felt that whizz past my ear!'");
            story.setFlag("sera_saved_pell", true);
        } else if (choice == 2) {
            Printer.slowPrint("The scholars scramble back. A bolt strikes the mud at their feet.");
            Printer.slowPrint("Pell trips on a root. You hear a sickening crunch as he falls.");
            Printer.slowPrint("He screams in pain as his leg buckles beneath him.");
            sera.heal(-10);
        } else {
            Printer.slowPrint("A bolt grazes Pell's shoulder. He screams.");
            Printer.slowPrint("Blood wells around the shallow wound, soaking his tunic.");
            sera.heal(-5);
        }

        Printer.slowPrint("A scout drops from the branches, drawing a shortsword.");
        Printer.slowPrint("'Traitor!' the scout snarls, spittle flying. 'Vrakkas claims this land. You die with it.'");
        if (!CombatSystem.startCombat(sera, Enemy.ashenHandBandit())) {
            handleGameOver();
            return;
        }
        sera.heal(15);
        Printer.slowPrint("You retrieve your arrow from the scout's chest. They know you're here.");
        InputHandler.waitForEnter();
    }

    private void sceneAmbush() {
        Printer.printDivider();
        Printer.slowPrint("You push the party forward, seeking cover. The bend in the trail opens east.");
        Printer.slowPrint("It's the road you stopped wanting to look at.");
        Printer.pause(400);
        Printer.slowPrint("Memory flashes: Corvin Ash, peace emissary to the border kingdoms. Your friend.");
        Printer.slowPrint("You heard the attack before you saw it. You arrived after. The blood on the white stones.");
        Printer.slowPrint("The official report said Vrakkas bandits wanted war. Corvin died for nothing.");
        Printer.pause(400);

        Printer.slowPrint("A metallic twang breaks the quiet. Then another. Crossbow bolts hail from the ridge.");
        Printer.slowPrint("Not bandits — these shots are disciplined, timed. A military formation.");

        System.out.println("  1. Shield the party — hold the line in the open to draw their fire.");
        System.out.println("  2. [Stealth] Flank the ridge through the heavy brush. Leave the party hidden.");
        System.out.println("  3. Order a fighting retreat to the ruins.");

        int c = InputHandler.getInt(1, 3);
        if (c == 2) {
            if (util.Dice.performSkillCheck("Stealth", 14)) {
                Printer.slowPrint("You slip into the undergrowth, becoming a ghost. You circle up the ridge.");
                Printer.slowPrint("You drop behind their line, your daggers flashing.");
                story.setFlag("sera_flanked_ridge", true);
            } else {
                Printer.slowPrint("You try to flank them, but the dense brush rustles loudly.");
                Printer.slowPrint("They spot your movement and turn their crossbows on you.");
                sera.heal(-15);
            }
        } else if (c == 1) {
            Printer.slowPrint("You draw aggro, deflecting a bolt with your bracer and returning fire.");
        } else {
            Printer.slowPrint("You cover the scholars as they scramble back toward the ancient stone walls.");
        }

        Printer.slowPrint("Two heavily armored soldiers break from the ridge, charging your position.");
        if (!CombatSystem.startCombat(sera, Enemy.ashenSoldier())) {
            handleGameOver();
            return;
        }
        sera.heal(20);

        Printer.slowPrint("Another soldier charges, swinging a heavy broadsword.");
        if (!CombatSystem.startCombat(sera, Enemy.ashenSoldier())) {
            handleGameOver();
            return;
        }
        sera.heal(20);
        InputHandler.waitForEnter();
    }

    private void sceneAftermath() {
        Printer.printDivider();
        Printer.slowPrint("The fighting stops. The woods fall eerily silent.");
        Printer.slowPrint("The scholars are shaken, huddling together, but they are alive.");
        Printer.pause(400);
        Printer.slowPrint("You kneel and turn over one of the dead attackers.");
        Printer.slowPrint("They wear Vrakkas colors. Red and gold tabards.");
        Printer.slowPrint("But as you pull the tabard aside to retrieve your arrow, you see it.");
        Printer.pause(400);
        Printer.slowPrint("Beneath the false colors, stitched into their heavy leather gambesons... a silver seal.");
        Printer.slowPrint("A crescent moon over a sword. The Iron Vow.");
        Printer.slowPrint("This wasn't a random attack. It was engineered.");

        System.out.println("  1. Take the seal as proof.");
        System.out.println("  2. Say nothing to the scholars. Keep it to yourself.");

        if (InputHandler.getInt(1, 2) == 1) {
            Printer.slowPrint("You cut the seal out with your dagger and pocket it.");
            story.setFlag("sera_found_engineered_ambush", true);
            story.setFlag("sera_has_seal", true);
        } else {
            Printer.slowPrint("You cover the body back up. Knowledge is dangerous in the east.");
            story.setFlag("sera_found_engineered_ambush", true);
        }
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You escort the terrified survey party back to the border outpost.");
        Printer.slowPrint("As they pay you, your mind is racing.");
        Printer.pause(400);
        Printer.slowPrint("Corvin's death was not bad luck. It wasn't Vrakkas bandits.");
        Printer.slowPrint("Someone in the Crown needed the eastern road open for war, and Corvin was in the way.");
        Printer.slowPrint("And whoever they are, they are still operating here.");

        Printer.printDivider();
        Printer.printBox("ACT I COMPLETE — THE VOSS COMPASS");
        if (story.getFlag("sera_has_seal")) {
            Printer.printBox("★ You took the Iron Vow seal. Hard proof is better than a hunter's word.");
        }
        Printer.printDivider();
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("The eastern road claims another guide. Your body is never found.");
        System.exit(0);
    }
}
