package characters;

import combat.Enemy;
import combat.Skill;

public class Sera extends Character {
    private int focusCharges;
    public static final int MAX_FOCUS_CHARGES = 3;
    private boolean isFocused;

    public Sera() {
        super("Sera Caldwell", "The Voss Compass", 95, 32, 12, 25);
        this.focusCharges = MAX_FOCUS_CHARGES;
        this.isFocused = false;

        skills.add(new Skill("Arrow Shot", 28, 8, "A well-aimed arrow attack."));
        skills.add(new Skill("Multi-Shot", 38, 18, "Fire multiple arrows at once."));
        skills.add(new Skill("Track", 0, 10, "Study the enemy's movements — next hit guaranteed."));
        skills.add(new Skill("Piercing Shot", 45, 22, "An arrow that pierces through armor."));
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (focusCharges > 0) {
            isFocused = true;
            System.out.println("\n  Sera takes a deep breath. Her vision narrows.");
            System.out.println("  Everything else falls away. There is only the target.");
            int damage = (int)(Math.random() * 50) + 40; // 40-90 focused shot
            System.out.println("  Her arrow flies true — (" + damage + " damage!)");
            enemy.takeDamage(damage);
            focusCharges--;
            isFocused = false;
            System.out.println("  [Focus charges: " + focusCharges + "/" + MAX_FOCUS_CHARGES + "]");
        } else {
            System.out.println("\n  Sera tries to find her focus. But the tension won't break.");
        }
    }

    @Override
    public String getSpecialAbilityName() {
        return "Focused Shot [" + focusCharges + "/" + MAX_FOCUS_CHARGES + "]";
    }

    @Override
    public String getClassDescription() {
        return "Sera Caldwell — Eastern Scout. Balanced stats, ranged damage specialist.\n" +
               "Special: Focused Shot — precise, devastating accuracy (3 uses).";
    }

    public int getFocusCharges() { return focusCharges; }
    public boolean isFocused() { return isFocused; }
}
