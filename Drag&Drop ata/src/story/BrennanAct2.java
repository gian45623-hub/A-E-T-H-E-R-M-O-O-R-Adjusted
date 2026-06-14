package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class BrennanAct2 {

    private Knight brennan;
    private StoryManager story;

    public BrennanAct2(Knight brennan, StoryManager story) {
        this.brennan = brennan;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneValdenmere();
        sceneCampfire();
        sceneSummit();
        sceneConfrontation();
        sceneArrest();
        sceneActEnd();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT II — THE RECKONING  |  Brennan Ashvane");
        Printer.slowPrint("Valdenmere. The capital city. The beating heart of Thaalisia.");
        Printer.slowPrint("The Iron Vow Summit has brought every Marshal in the kingdom here.");
        Printer.slowPrint("Including Veyran.");
        Printer.pause(400);
        Printer.slowPrint("The streets are crowded with merchants, refugees, and hundreds of soldiers.");
        Printer.slowPrint("Banners of the Iron Vow snap in the wind — a silver sword over a crescent moon.");
        Printer.slowPrint("To the public, it's a symbol of safety. To you, it's a symbol of betrayal.");
        InputHandler.waitForEnter();
    }

    private void sceneValdenmere() {
        Printer.printDivider();
        Printer.slowPrint("Your face is on three wanted boards before you even reach the market district.");
        Printer.slowPrint("Guards are patrolling in heavily armed pairs. You need to get off the streets before someone recognizes you.");
        System.out.println("  Where do you lie low?");
        System.out.println("  1. The Ashen Tap (a rough, underground mercenary tavern)");
        System.out.println("  2. The refugee camp just outside the city walls");
        System.out.println("  3. Your old contact Ser Dovin's safehouse in the merchant quarter");
        
        int c = InputHandler.getInt(1, 3);
        switch (c) {
            case 1 -> {
                Printer.slowPrint("You slip into the dim, smoke-filled tavern. You blend in with the sellswords.");
                Printer.slowPrint("You overhear two drunk city guards complaining about the Summit's tight schedule.");
                Printer.slowPrint("\"Veyran speaks at high noon in the Grand Hall,\" one slurs. \"Complete lockdown.\"");
                story.setFlag("brennan_knows_summit_schedule", true);
            }
            case 2 -> {
                Printer.slowPrint("You wrap your cloak tight and enter the muddy sprawling tents of the refugees.");
                Printer.slowPrint("No one asks questions here. Everyone is running from something.");
                Printer.slowPrint("You rest briefly, recovering your strength amongst the forgotten.");
                brennan.heal(30);
            }
            case 3 -> {
                Printer.slowPrint("You knock on Dovin's door in a specific rhythm. He opens it, his face draining of color.");
                Printer.slowPrint("\"Brennan... are you insane?\" he hisses, pulling you inside.");
                Printer.slowPrint("Dovin looks terrified. \"Veyran is planning a city-wide purge tonight. Anyone suspected of treason.\"");
                story.setFlag("brennan_knows_purge_incoming", true);
            }
        }
        
        Printer.pause(400);
        Printer.slowPrint("Eventually, you have to move again. A patrol spots you cutting through an alley.");
        Printer.slowPrint("A guard shouts, \"Hey! You there! Show your face!\"");
        Printer.slowPrint("You don't stop. They draw steel and charge.");
        
        if (!CombatSystem.startCombat(brennan, Enemy.ashenSoldier())) { handleGameOver(); return; }
        brennan.heal(20);
        Printer.slowPrint("You drag the unconscious guard into the shadows. That was too close.");
        InputHandler.waitForEnter();
    }

    private void sceneCampfire() {
        Printer.printDivider();
        Printer.slowPrint("You escape the patrol and slip into an abandoned, half-burned warehouse.");
        Printer.slowPrint("You aren't alone. At a small, hidden campfire sits a woman in tattered robes.");
        Printer.slowPrint("Her staff hums with raw, crackling magic. She looks up, unbothered by your arrival.");
        Printer.slowPrint("\"You have the look of a hunted man,\" she says, offering a flask. \"I'm Eryn.\"");
        Printer.pause(400);
        
        System.out.println("  She's an exiled mage. You can sense the power rolling off her.");
        System.out.println("  1. Share the truth about Veyran and the cult connection.");
        System.out.println("  2. Stay vague. Trust no one.");
        System.out.println("  3. Ask about her target first. Why is she hiding?");
        
        int choice = InputHandler.getInt(1, 3);
        if (choice == 1) {
            Printer.slowPrint("You tell her about the village, the crates, and the letter.");
            Printer.slowPrint("Eryn's eyes narrow. \"Your Veyran and my Valdros. They're working together.\"");
            Printer.slowPrint("\"They are building something in the east. They call it Stage Two.\"");
            Printer.slowPrint("She tells you of a massive magical conduit designed to weaponize the Greying.");
            story.setFlag("brennan_knows_about_valdros", true);
        } else if (choice == 3) {
            Printer.slowPrint("\"I'm hunting a ghost,\" she says softly. \"A man named Valdros. He built the Greying.\"");
            Printer.slowPrint("You exchange stories. Two exiles hunting their former masters.");
            story.setFlag("brennan_knows_about_valdros", true);
        } else {
            Printer.slowPrint("\"Keep your secrets then,\" she shrugs. \"We all have them.\"");
        }
        
        story.setFlag("brennan_met_eryn", true);
        Printer.slowPrint("You rest by the fire until dawn breaks. It's time.");
        brennan.heal(50);
        brennan.restoreMana(30);
        InputHandler.waitForEnter();
    }

    private void sceneSummit() {
        Printer.printDivider();
        Printer.slowPrint("You infiltrate the Iron Hall through the servant's entrance, knocking out a guard and taking his tabard.");
        Printer.slowPrint("The Grand Hall is massive, filled with hundreds of knights, nobles, and marshals.");
        Printer.pause(400);
        Printer.slowPrint("Lord Marshal Veyran stands at the podium, polished and certain in his gleaming armor.");
        Printer.slowPrint("\"...and so, necessary sacrifices must be made!\" his voice booms.");
        Printer.slowPrint("\"The Ashen Hand threatens our borders! We must expand our military authority to crush them!\"");
        Printer.pause(400);
        Printer.slowPrint("He's using the cult he funds to justify taking total control of the kingdom.");
        
        Printer.slowPrint("A guard in the corridor recognizes your face beneath the helm.");
        Printer.slowPrint("\"It's him! The traitor Ashvane!\"");
        
        if (!CombatSystem.startCombat(brennan, Enemy.ashenBrute())) { handleGameOver(); return; }
        brennan.heal(15);
        Printer.slowPrint("You kick the dying brute aside. The doors to the Grand Hall are right in front of you.");
        InputHandler.waitForEnter();
    }

    private void sceneConfrontation() {
        Printer.printDivider();
        Printer.slowPrint("You kick the heavy oak doors open. They crash against the stone walls.");
        Printer.slowPrint("Silence falls over the entire Summit. Hundreds of faces turn to look at you.");
        Printer.pause(400);
        Printer.slowPrint("\"Ashvane,\" Veyran says from the podium, his voice echoing. \"I wondered when you'd show up.\"");
        
        System.out.println("  The room is waiting. What do you do?");
        System.out.println("  1. Shout about the innocent village you saw burn to fund his war.");
        System.out.println("  2. Throw the evidence (the letter and the key) at his feet.");
        System.out.println("  3. Draw your sword in absolute silence and walk toward him.");
        
        int c = InputHandler.getInt(1, 3);
        if (c == 2 && story.getFlag("brennan_has_veyran_letter")) {
            Printer.slowPrint("You pull the sealed letter from your cloak and hurl it onto the marble floor.");
            Printer.slowPrint("\"Read it!\" you roar to the crowd. \"Read his orders to the Ashen Hand!\"");
            Printer.slowPrint("Whispers break out among the Marshals. Doubt ripples through the room.");
            story.setFlag("brennan_confronted_with_crates", true);
        } else if (c == 1) {
            Printer.slowPrint("\"You slaughtered Millhaven!\" you yell. \"You burn our people to hold onto power!\"");
        } else {
            Printer.slowPrint("The ring of steel fills the hall as you draw your blade. Veyran simply smiles.");
        }
        
        Printer.pause(400);
        Printer.slowPrint("\"The Greying was a wound,\" Veyran says to the hall, ignoring your accusations.");
        Printer.slowPrint("\"And a wound needs cauterizing! We must burn out the rot to save Thaalisia!\"");
        Printer.slowPrint("\"If you lack the stomach for what must be done, you do not deserve this kingdom!\"");
        InputHandler.waitForEnter();
    }

    private void sceneArrest() {
        Printer.printDivider();
        Printer.slowPrint("Six elite royal guards surround you, halberds lowered.");
        Printer.slowPrint("Veyran steps behind them, looking down at you from the dais.");
        Printer.slowPrint("\"Arrest the traitor,\" he commands. \"Execution at dawn.\"");
        
        System.out.println("  1. Fight free and run. You can't save them if you're dead.");
        System.out.println("  2. Surrender peacefully to face a public trial.");
        System.out.println("  3. Shout the truth to the hall as they drag you away.");
        
        int c = InputHandler.getInt(1, 3);
        if (c == 1) {
            Printer.slowPrint("You parry a halberd, crack a guard's jaw with your pommel, and sprint for the stained glass window.");
            Printer.slowPrint("You shatter it, falling three stories into the moat below.");
            story.setFlag("brennan_fought_free", true);
        } else {
            Printer.slowPrint("You drop your blade. It clatters on the stone. The truth must be heard in the light.");
            story.setFlag("brennan_was_arrested", true);
            if (c == 3) {
                Printer.slowPrint("\"HE IS THE ENEMY! HE IS FUNDING THE ASHEN HAND!\" you roar as they drag you away in chains.");
                story.setFlag("brennan_made_public_accusation", true);
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneActEnd() {
        Printer.printDivider();
        if (story.getFlag("brennan_was_arrested")) {
            Printer.slowPrint("You sit in a cold, damp cell in the dungeon beneath the Iron Hall.");
            Printer.slowPrint("Footsteps approach. A young knight stands at the bars, looking around nervously.");
            Printer.slowPrint("\"Ser Ashvane,\" he whispers. \"We're not all like him. Some of us remember the vows.\"");
        } else {
            Printer.slowPrint("You are a fugitive again, bleeding in the gutters of Valdenmere.");
            Printer.slowPrint("But Veyran is exposed. The seed of doubt has been planted in the Iron Vow.");
        }
        
        Printer.printDivider();
        Printer.printBox("ACT II COMPLETE — THE RECKONING");
        if (story.getFlag("brennan_confronted_with_crates")) {
            Printer.printBox("★ You presented hard evidence. Veyran's grip on the Marshals is slipping.");
        }
        if (story.getFlag("brennan_met_eryn")) {
            Printer.printBox("★ You forged an alliance with Eryn Voss. The bigger picture becomes clear.");
        }
        Printer.printDivider();
        story.advanceAct();
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("You fall before justice can be served. Thaalisia is doomed.");
        System.exit(0);
    }
}
