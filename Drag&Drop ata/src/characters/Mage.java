package characters;

import combat.Enemy;
import combat.Skill;

public class Mage extends Character {
    protected int rawPowerCharges;
    public static final int MAX_RAW_CHARGES = 3;

    protected Mage(String name, String title, int hp, int attack, int defense, int mana) {
        super(name, title, hp, attack, defense, mana);
        this.rawPowerCharges = MAX_RAW_CHARGES;

        skills.add(new Skill("Arcane Bolt", 25, 15, "A focused beam of arcane energy."));
        skills.add(new Skill("Frost Nova", 40, 30, "Unleash a burst of freezing cold."));
        skills.add(new Skill("Mana Siphon", -20, 0, "Drain your own HP to restore 30 mana."));
        skills.add(new Skill("Void Lance", 55, 45, "A powerful but draining arcane spear."));
    }

    public Mage() {
        this("Eryn Voss", "The Exiled Scholar", 85, 40, 15, 100);
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (rawPowerCharges > 0) {
            int damage = (int) (Math.random() * 61) + 40;
            System.out.println("\n  " + name + " squeezes her eyes shut. Raw, uncontrolled magic ERUPTS!");
            System.out.println("  (" + damage + " damage!)");
            enemy.takeDamage(damage);
            rawPowerCharges--;
            System.out.println("  [Raw Power charges: " + rawPowerCharges + "/" + MAX_RAW_CHARGES + "]");
        } else {
            System.out.println("\n  " + name + " reaches for that dark place inside. There's nothing left.");
        }
    }

    @Override
    public String getSpecialAbilityName() {
        return "Raw Power [" + rawPowerCharges + "/" + MAX_RAW_CHARGES + "]";
    }

    @Override
    public String getClassDescription() {
        return name + " — Exiled mage. High magic damage, low defense.\n" +
                "Special: Raw Power — massive uncontrolled burst (3 uses, affects ending).";
    }

    public int getRawPowerCharges() {
        return rawPowerCharges;
    }

    public void useManaSkill() {
        int hpCost = 20;
        int manaGain = 30;
        this.hp = Math.max(1, this.hp - hpCost);
        this.mana = Math.min(this.maxMana, this.mana + manaGain);
        System.out.println("  " + name + " sacrifices " + hpCost + " HP to restore " + manaGain + " mana.");
    }
}
