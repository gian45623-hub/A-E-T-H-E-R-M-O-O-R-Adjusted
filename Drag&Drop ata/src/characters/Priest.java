package characters;

import combat.Enemy;
import combat.Skill;

public class Priest extends Character {
    protected int faithPoints;
    public static final int MAX_FAITH = 10;

    protected Priest(String name, String title, int hp, int attack, int defense, int mana) {
        super(name, title, hp, attack, defense, mana);
        this.faithPoints = MAX_FAITH;

        skills.add(new Skill("Holy Light", -35, 20, "Channel divine energy to heal yourself."));
        skills.add(new Skill("Smite", 32, 25, "Strike with a burst of sacred flame."));
        skills.add(new Skill("Purify", 0, 30, "Cleanse corruption — weakens undead enemies."));
        skills.add(new Skill("Sacred Shield", 0, 35, "Increase defense by 15 until next turn."));
    }

    public Priest() {
        this("Solia Ren", "The Faithless Healer", 110, 20, 15, 80);
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (faithPoints > 0) {
            System.out.println("\n  " + name + " closes her eyes. She doesn't know if anyone is listening.");
            System.out.println("  She prays anyway.");
            heal(40);
            this.defense += 8;
            faithPoints--;
            System.out.println("  [Faith Points: " + faithPoints + "/" + MAX_FAITH + "]");
        } else {
            System.out.println("\n  " + name + " reaches for faith. The well is dry.");
        }
    }

    public void loseFaith(int amount) {
        faithPoints = Math.max(0, faithPoints - amount);
        System.out.println("  [Faith -" + amount + " | Remaining: " + faithPoints + "/" + MAX_FAITH + "]");
    }

    @Override
    public String getSpecialAbilityName() {
        return "Divine Prayer [" + faithPoints + "/" + MAX_FAITH + " faith]";
    }

    @Override
    public String getClassDescription() {
        return name + " — Wandering healer. Balanced stats, strong healing.\n" +
                "Special: Divine Prayer — heals and buffs defense (faith depletes through story).";
    }

    public int getFaithPoints() {
        return faithPoints;
    }
}
