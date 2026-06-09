package characters;

import combat.Enemy;
import combat.Skill;

public class Mira extends Character {
    private int shadowCharges;
    public static final int MAX_SHADOW_CHARGES = 4;
    private boolean isInShadows;

    public Mira() {
        super("Mira Cael", "The Ghost", 90, 35, 8, 20);
        this.shadowCharges = MAX_SHADOW_CHARGES;
        this.isInShadows = false;

        skills.add(new Skill("Quick Strike", 32, 10, "A fast, precise attack."));
        skills.add(new Skill("Backstab", 50, 15, "Deals extra damage from the shadows."));
        skills.add(new Skill("Smokescreen", 0, 12, "Vanish into shadow — dodge next attack."));
        skills.add(new Skill("Poison Blade", 28, 20, "Coat your blade with poison for lingering damage."));
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (shadowCharges > 0) {
            isInShadows = true;
            System.out.println("\n  Mira fades into shadow.");
            System.out.println("  She becomes a ghost in the darkness.");
            int damage = (int)(Math.random() * 45) + 35; // 35-80 from shadows
            System.out.println("  A blade flashes from nowhere — (" + damage + " damage!)");
            enemy.takeDamage(damage);
            shadowCharges--;
            isInShadows = false;
            System.out.println("  [Shadow charges: " + shadowCharges + "/" + MAX_SHADOW_CHARGES + "]");
        } else {
            System.out.println("\n  Mira tries to meld with the shadows. But she's already used them all.");
        }
    }

    @Override
    public String getSpecialAbilityName() {
        return "Shadow Strike [" + shadowCharges + "/" + MAX_SHADOW_CHARGES + "]";
    }

    @Override
    public String getClassDescription() {
        return "Mira Cael — Master Thief. High attack and speed, low defense.\n" +
               "Special: Shadow Strike — vanish and strike from darkness (4 uses).";
    }

    public int getShadowCharges() { return shadowCharges; }
    public boolean isInShadows() { return isInShadows; }
}
