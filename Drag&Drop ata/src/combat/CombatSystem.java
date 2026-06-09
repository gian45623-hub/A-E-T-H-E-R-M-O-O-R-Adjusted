package combat;

import characters.Character;
import characters.Knight;
import characters.Mage;
import util.InputHandler;
import util.Printer;

public class CombatSystem {

    public static boolean startCombat(Character player, Enemy enemy) {
        // Delegate to PartyBattle using the globally selected party
        engine.Party party = ui.CharacterSelectPanel.selectedParty;
        if (party != null && party.getPartySize() > 0) {
            return PartyBattle.startPartyBattle(party, enemy);
        }

        Printer.printDivider();
        Printer.slowPrint("⚔️   " + player.getName() + "  vs  " + enemy.getName());
        Printer.printDivider();
        Printer.slowPrint("    " + enemy.getDescription());
        Printer.printDivider();
        InputHandler.waitForEnter();

        while (player.isAlive() && enemy.isAlive()) {
            playerTurn(player, enemy);
            if (!enemy.isAlive()) break;
            System.out.println();
            enemyTurn(player, enemy);
            System.out.println();

            // Tick rage for Knight
            if (player instanceof Knight k) {
                k.tickRage();
            }
        }

        if (player.isAlive()) {
            Printer.printDivider();
            Printer.slowPrint("✅  " + enemy.getName() + " has been defeated.");
            Printer.printDivider();
            InputHandler.waitForEnter();
            return true; // player won
        } else {
            Printer.printDivider();
            Printer.slowPrint("💀  " + player.getName() + " has fallen...");
            Printer.slowPrint("    The story of Aethermoor goes unfinished.");
            Printer.printDivider();
            return false; // player lost
        }
    }

    private static void playerTurn(Character player, Enemy enemy) {
        Printer.printDivider();
        System.out.println("  " + player.getStatusBar());
        System.out.println("  " + enemy.getName() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
        Printer.printDivider();
        System.out.println("  Choose your action:");
        System.out.println("  1. Basic Attack");
        System.out.println("  2. Use Skill");
        System.out.println("  3. " + player.getSpecialAbilityName());

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> basicAttack(player, enemy);
            case 2 -> useSkill(player, enemy);
            case 3 -> player.useSpecialAbility(enemy);
        }
    }

    private static void basicAttack(Character player, Enemy enemy) {
        int damage = player.getAttackPower() + (int)(Math.random() * 10);
        System.out.println("\n  " + player.getName() + " attacks!");
        enemy.takeDamage(damage);
    }

    private static void useSkill(Character player, Enemy enemy) {
        System.out.println("\n  Choose a skill:");
        var skills = player.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            System.out.println("  " + (i+1) + ". " + skills.get(i));
        }
        System.out.println("  " + (skills.size()+1) + ". Cancel");

        int choice = InputHandler.getInt(1, skills.size() + 1);
        if (choice == skills.size() + 1) {
            System.out.println("  You hesitate and do nothing.");
            return;
        }

        var chosen = skills.get(choice - 1);

        // Special case: Mage's Mana Siphon
        if (chosen.getName().equals("Mana Siphon") && player instanceof Mage m) {
            m.useManaSkill();
            return;
        }

        if (player.getMana() < chosen.getManaCost()) {
            System.out.println("  Not enough mana! (" + player.getMana() + "/" + chosen.getManaCost() + " needed)");
            return;
        }

        player.setMana(player.getMana() - chosen.getManaCost());

        if (chosen.getPower() < 0) {
            // Healing skill
            player.heal(Math.abs(chosen.getPower()));
        } else if (chosen.getPower() == 0) {
            // Utility skill
            applyUtilityEffect(player, enemy, chosen.getName());
        } else {
            // Damage skill
            System.out.println("\n  " + player.getName() + " uses " + chosen.getName() + "!");
            enemy.takeDamage(chosen.getPower());
        }
    }

    private static void applyUtilityEffect(Character player, Enemy enemy, String skillName) {
        switch (skillName) {
            case "Intimidate" -> {
                System.out.println("\n  " + player.getName() + " lets out a thunderous shout!");
                System.out.println("  " + enemy.getName() + " flinches — attack reduced for 1 turn.");
                // Simple implementation: deal minor damage representing disruption
                enemy.takeDamage(10);
            }
            case "War Cry" -> {
                if (player instanceof Knight k) {
                    k.applyWarCry();
                } else {
                    int boost = 12;
                    player.setAttackPower(player.getAttackPower() + boost);
                    System.out.println("\n  " + player.getName() + " roars a battle cry!");
                    System.out.println("  Attack +" + boost + " for 2 turns.");
                }
            }
            case "Purify" -> {
                System.out.println("\n  " + player.getName() + " channels purifying light!");
                System.out.println("  The corruption within " + enemy.getName() + " writhes in pain.");
                enemy.takeDamage(20);
            }
            case "Sacred Shield" -> {
                int defBoost = 15;
                player.setDefense(player.getDefense() + defBoost);
                System.out.println("\n  A golden shield of light forms around " + player.getName() + ".");
                System.out.println("  Defense +" + defBoost + " until next turn.");
            }
            default -> System.out.println("  Nothing happened.");
        }
    }

    private static void enemyTurn(Character player, Enemy enemy) {
        int damage = enemy.attackPlayer();
        System.out.println("  " + enemy.getName() + " attacks " + player.getName() + "!");
        player.takeDamage(damage);
    }
}
