package combat;

import characters.Character;
import characters.Brennan;
import engine.Party;
import util.InputHandler;
import util.Printer;

public class PartyBattle {

    public static boolean startPartyBattle(Party party, Enemy enemy) {
        Printer.printDivider();
        Printer.slowPrint("⚔️   PARTY BATTLE");
        Printer.slowPrint("    " + getPartyNames(party) + "  vs  " + enemy.getName());
        Printer.slowPrint("    " + enemy.getDescription());
        Printer.printDivider();
        InputHandler.waitForEnter();

        while (party.isPartyAlive() && enemy.isAlive()) {
            System.out.println(party.getPartyStatus());
            System.out.println("  " + enemy.getName() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
            Printer.printDivider();

            partyTurn(party, enemy);
            if (!enemy.isAlive()) break;

            System.out.println();
            enemyTurn(party, enemy);
            System.out.println();

            // Tick down enemy cooldowns and status effects
            tickCombatEffects(party);
        }

        if (party.isPartyAlive()) {
            Printer.printDivider();
            Printer.slowPrint("✅  " + enemy.getName() + " has been defeated!");
            Printer.slowPrint("    Party gained experience!");
            Printer.printDivider();
            InputHandler.waitForEnter();
            return true;
        } else {
            Printer.printDivider();
            Printer.slowPrint("💀  Your party has fallen...");
            Printer.slowPrint("    The adventure ends here.");
            Printer.printDivider();
            return false;
        }
    }

    private static void partyTurn(Party party, Enemy enemy) {
        System.out.println("  SELECT ACTIVE CHARACTER:");
        for (int i = 0; i < party.getMembers().size(); i++) {
            Character member = party.getMembers().get(i);
            String status = member.isAlive() ? "✓" : "✗";
            System.out.println("  " + (i+1) + ". [" + status + "] " + member.getName() + 
                             " (" + member.getTitle() + ")");
        }

        int charChoice = InputHandler.getInt(1, party.getMembers().size()) - 1;
        Character actor = party.getMembers().get(charChoice);
        party.setActiveCharacter(actor);

        if (!actor.isAlive()) {
            System.out.println("  " + actor.getName() + " is defeated and cannot act.");
            return;
        }

        System.out.println();
        System.out.println("  " + actor.getName() + "'s Turn:");
        System.out.println("  1. Basic Attack");
        System.out.println("  2. Use Skill");
        System.out.println("  3. " + actor.getSpecialAbilityName());
        System.out.println("  4. Use Item");

        int action = InputHandler.getInt(1, 4);

        switch (action) {
            case 1 -> basicAttack(actor, enemy);
            case 2 -> useSkill(actor, enemy);
            case 3 -> actor.useSpecialAbility(enemy);
            case 4 -> useItem(party, actor);
        }
    }

    private static void useItem(Party party, Character actor) {
        System.out.println("\n  Choose an item:");
        System.out.println("  1. Health Potion (Heals 50 HP) [x" + party.getHealthPotions() + "]");
        System.out.println("  2. Mana Potion (Restores 30 Mana) [x" + party.getManaPotions() + "]");
        System.out.println("  3. Cancel");

        int choice = InputHandler.getInt(1, 3);
        if (choice == 3) {
            System.out.println("  " + actor.getName() + " hesitates and does nothing.");
            return;
        }

        if (choice == 1 && party.getHealthPotions() <= 0) {
            System.out.println("  No Health Potions left! Turn wasted.");
            return;
        } else if (choice == 2 && party.getManaPotions() <= 0) {
            System.out.println("  No Mana Potions left! Turn wasted.");
            return;
        }

        System.out.println("\n  Select target:");
        var members = party.getMembers();
        for (int i = 0; i < members.size(); i++) {
            Character member = members.get(i);
            System.out.println("  " + (i + 1) + ". " + member.getName() + " (HP: " + member.getHp() + "/" + member.getMaxHp() + " | Mana: " + member.getMana() + "/" + member.getMaxMana() + ")");
        }
        System.out.println("  " + (members.size() + 1) + ". Cancel");

        int targetChoice = InputHandler.getInt(1, members.size() + 1);
        if (targetChoice == members.size() + 1) {
            System.out.println("  " + actor.getName() + " hesitates and does nothing.");
            return;
        }

        Character target = members.get(targetChoice - 1);

        if (choice == 1) {
            party.useHealthPotion();
            System.out.println("\n  " + actor.getName() + " uses a Health Potion on " + target.getName() + "!");
            target.heal(50);
        } else {
            party.useManaPotion();
            System.out.println("\n  " + actor.getName() + " uses a Mana Potion on " + target.getName() + "!");
            target.restoreMana(30);
        }
    }

    private static void basicAttack(Character actor, Enemy enemy) {
        int damage = actor.getAttackPower() + (int)(Math.random() * 10);
        System.out.println("\n  " + actor.getName() + " attacks!");
        enemy.takeDamage(damage);
    }

    private static void useSkill(Character actor, Enemy enemy) {
        System.out.println("\n  Choose a skill:");
        var skills = actor.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            System.out.println("  " + (i+1) + ". " + skills.get(i));
        }
        System.out.println("  " + (skills.size()+1) + ". Cancel");

        int choice = InputHandler.getInt(1, skills.size() + 1);
        if (choice == skills.size() + 1) return;

        var chosen = skills.get(choice - 1);

        if (actor.getMana() < chosen.getManaCost()) {
            System.out.println("  Not enough mana!");
            return;
        }

        actor.setMana(actor.getMana() - chosen.getManaCost());

        if (chosen.getPower() < 0) {
            actor.heal(Math.abs(chosen.getPower()));
        } else if (chosen.getPower() == 0) {
            applyUtilityEffect(actor, enemy, chosen.getName());
        } else {
            System.out.println("\n  " + actor.getName() + " uses " + chosen.getName() + "!");
            enemy.takeDamage(chosen.getPower());
        }
    }

    private static void applyUtilityEffect(Character actor, Enemy enemy, String skillName) {
        switch (skillName) {
            case "Intimidate" -> {
                System.out.println("\n  " + actor.getName() + " shouts at " + enemy.getName() + "!");
                enemy.takeDamage(10);
            }
            case "War Cry" -> {
                actor.setAttackPower(actor.getAttackPower() + 12);
                System.out.println("\n  " + actor.getName() + " roars a battle cry!");
                System.out.println("  Attack +12 for 2 turns.");
            }
            case "Track" -> {
                System.out.println("\n  " + actor.getName() + " studies " + enemy.getName() + "'s movements!");
                System.out.println("  Next hit guaranteed to connect!");
            }
            case "Purify" -> {
                System.out.println("\n  " + actor.getName() + " channels purifying light!");
                enemy.takeDamage(20);
            }
            case "Sacred Shield" -> {
                actor.setDefense(actor.getDefense() + 15);
                System.out.println("\n  A golden shield forms around " + actor.getName() + "!");
            }
            default -> System.out.println("  " + skillName + " activated.");
        }
    }

    private static void enemyTurn(Party party, Enemy enemy) {
        // Find a random alive party member to target
        Character target = null;
        for (Character member : party.getMembers()) {
            if (member.isAlive()) {
                target = member;
                break;
            }
        }

        if (target == null) return;

        int damage = enemy.attackPlayer();
        System.out.println("  " + enemy.getName() + " attacks " + target.getName() + "!");
        target.takeDamage(damage);
    }

    private static void tickCombatEffects(Party party) {
        for (Character member : party.getMembers()) {
            if (member instanceof Brennan b) {
                b.tickRage();
            }
        }
    }

    private static String getPartyNames(Party party) {
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < party.getMembers().size(); i++) {
            if (i > 0) names.append(", ");
            names.append(party.getMembers().get(i).getName());
        }
        return names.toString();
    }
}
