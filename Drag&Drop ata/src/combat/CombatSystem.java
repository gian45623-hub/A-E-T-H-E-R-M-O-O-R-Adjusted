package combat;

import characters.Character;
import characters.Knight;
import characters.Mage;
import util.InputHandler;
import util.Printer;

public class CombatSystem {

    public static boolean startCombat(Character player, Enemy enemy) {

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

            // Tick buffs and debuffs
            player.tickBuffs();
            enemy.tickDebuffs();
        }

        if (player.isAlive()) {
            Printer.printDivider();
            Printer.slowPrint("✅  " + enemy.getName() + " has been defeated.");
            if (Math.random() < 0.30) {
                if (Math.random() < 0.5) {
                    player.addHealthPotion();
                    Printer.slowPrint("✨  You found a Health Potion!");
                } else {
                    player.addManaPotion();
                    Printer.slowPrint("✨  You found a Mana Potion!");
                }
            }
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
        boolean turnTaken = false;
        while (!turnTaken) {
            Printer.printDivider();
            System.out.println("  " + player.getStatusBar());
            System.out.println("  " + enemy.getName() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
            Printer.printDivider();
            System.out.println("  Choose your action:");
            System.out.println("  1. Basic Attack");
            System.out.println("  2. Use Skill");
            System.out.println("  3. " + player.getSpecialAbilityName());
            System.out.println("  4. Use Item");

            int choice = InputHandler.getInt(1, 4);

            switch (choice) {
                case 1 -> { basicAttack(player, enemy); turnTaken = true; }
                case 2 -> turnTaken = useSkill(player, enemy);
                case 3 -> { player.useSpecialAbility(enemy); turnTaken = true; }
                case 4 -> turnTaken = useItem(player);
            }
        }
    }

    private static boolean useItem(Character player) {
        System.out.println("\n  Choose an item:");
        System.out.println("  1. Health Potion (Heals 50 HP) [x" + player.getHealthPotions() + "]");
        System.out.println("  2. Mana Potion (Restores 30 Mana) [x" + player.getManaPotions() + "]");
        System.out.println("  3. Cancel");

        int choice = InputHandler.getInt(1, 3);
        if (choice == 3) {
            return false;
        }

        if (choice == 1 && player.getHealthPotions() <= 0) {
            System.out.println("  No Health Potions left!");
            return false;
        } else if (choice == 2 && player.getManaPotions() <= 0) {
            System.out.println("  No Mana Potions left!");
            return false;
        }

        if (choice == 1) {
            player.useHealthPotion();
            System.out.println("\n  " + player.getName() + " uses a Health Potion!");
            player.heal(50);
        } else {
            player.useManaPotion();
            System.out.println("\n  " + player.getName() + " uses a Mana Potion!");
            player.restoreMana(30);
        }
        return true;
    }

    private static void basicAttack(Character player, Enemy enemy) {
        int damage = player.getAttackPower() + (int)(Math.random() * 10);
        System.out.println("\n  " + player.getName() + " attacks!");
        boolean isCrit = Math.random() < 0.15;
        if (isCrit) {
            System.out.println("  *** CRITICAL HIT! ***");
            damage = (int)(damage * 1.5);
        }
        enemy.takeDamage(damage);
    }

    private static boolean useSkill(Character player, Enemy enemy) {
        System.out.println("\n  Choose a skill:");
        var skills = player.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            System.out.println("  " + (i+1) + ". " + skills.get(i));
        }
        System.out.println("  " + (skills.size()+1) + ". Cancel");

        int choice = InputHandler.getInt(1, skills.size() + 1);
        if (choice == skills.size() + 1) {
            return false;
        }

        var chosen = skills.get(choice - 1);

        // Special case: Mage's Mana Siphon
        if (chosen.getName().equals("Mana Siphon") && player instanceof Mage m) {
            m.useManaSkill();
            return true;
        }

        if (player.getMana() < chosen.getManaCost()) {
            System.out.println("  Not enough mana! (" + player.getMana() + "/" + chosen.getManaCost() + " needed)");
            return false;
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
            int damage = chosen.getPower();
            boolean isCrit = Math.random() < 0.15;
            if (isCrit) {
                System.out.println("  *** CRITICAL HIT! ***");
                damage = (int)(damage * 1.5);
            }
            enemy.takeDamage(damage);
        }
        return true;
    }

    private static void applyUtilityEffect(Character player, Enemy enemy, String skillName) {
        switch (skillName) {
            case "Intimidate" -> {
                System.out.println("\n  " + player.getName() + " lets out a thunderous shout!");
                System.out.println("  " + enemy.getName() + " flinches — attack reduced for 2 turns.");
                enemy.applyAttackDebuff(-10, 2);
            }
            case "War Cry" -> {
                if (player instanceof Knight k) {
                    k.applyWarCry();
                } else {
                    int boost = 15;
                    player.applyAttackBuff(boost, 3);
                    System.out.println("\n  " + player.getName() + " roars a battle cry!");
                    System.out.println("  Attack +" + boost + " for 3 turns.");
                }
            }
            case "Purify" -> {
                System.out.println("\n  " + player.getName() + " channels purifying light!");
                System.out.println("  The corruption within " + enemy.getName() + " writhes in pain.");
                int damage = 20;
                boolean isCrit = Math.random() < 0.15;
                if (isCrit) {
                    System.out.println("  *** CRITICAL HIT! ***");
                    damage = (int)(damage * 1.5);
                }
                enemy.takeDamage(damage);
            }
            case "Sacred Shield" -> {
                int defBoost = 15;
                player.applyDefenseBuff(defBoost, 2);
                System.out.println("\n  A golden shield of light forms around " + player.getName() + ".");
                System.out.println("  Defense +" + defBoost + " for 2 turns.");
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
