package characters;

import combat.Enemy;
import combat.Skill;

public class Knight extends Character {
    private boolean isRaging;
    private int rageTurnsLeft;
    private int warCryTurnsLeft;
    private int warCryBoost = 0;

    protected Knight(String name, String title, int hp, int attack, int defense, int mana) {
        super(name, title, hp, attack, defense, mana);
        this.isRaging = false;
        this.rageTurnsLeft = 0;
        this.warCryTurnsLeft = 0;

        skills.add(new Skill("Shield Bash", 28, 10, "Slam your shield into the enemy."));
        skills.add(new Skill("Precise Strike", 45, 15, "A calculated blow at weak points."));
        skills.add(new Skill("Intimidate", 0, 5, "Shout at the enemy — reduces their next attack."));
        skills.add(new Skill("War Cry", 0, 20, "Boost your attack by 12 for 2 turns."));
    }

    public Knight() {
        this("Brennan Ashvane", "The Oathbreaker", 140, 30, 20, 30);
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (!isRaging) {
            System.out.println("\n  Something snaps inside " + name + ".");
            System.out.println("  He stops thinking. He just FIGHTS.");
            
            int roll = util.Dice.rollD20();
            System.out.println("  [D20 Roll: " + roll + "]");
            
            if (roll == 1) {
                System.out.println("  *** CRITICAL FAILURE! ***");
                System.out.println("  He tries to enter a rage but just gets a headache instead!");
            } else {
                isRaging = true;
                rageTurnsLeft = 3;
                int boost = 20;
                
                if (roll <= 9) {
                    System.out.println("  It's a weak rage.");
                    boost = 10;
                } else if (roll == 20) {
                    System.out.println("  *** CRITICAL SUCCESS! ***");
                    System.out.println("  An unparalleled bloodlust takes over!");
                    boost = 30;
                }
                this.attackPower += boost;
                System.out.println("  [RAGE activated! Attack +" + boost + " for " + rageTurnsLeft + " turns.]");
            }
        } else {
            System.out.println("\n  " + name + " is already raging. His vision is red.");
        }
    }

    public void tickRage() {
        if (isRaging) {
            rageTurnsLeft--;
            if (rageTurnsLeft <= 0) {
                isRaging = false;
                this.attackPower -= 20;
                System.out.println("  " + name + "'s rage fades. He feels hollow.");
            }
        }

        if (warCryTurnsLeft > 0) {
            warCryTurnsLeft--;
            if (warCryTurnsLeft <= 0) {
                this.attackPower -= warCryBoost;
                warCryBoost = 0;
                System.out.println("  " + name + "'s battle cry wears off.");
            }
        }
    }

    public void applyWarCry(int roll) {
        int boost = 12;
        if (roll <= 9) {
            System.out.println("  A weak battle cry.");
            boost = 6;
        } else if (roll == 20) {
            System.out.println("  *** CRITICAL SUCCESS! ***");
            System.out.println("  An inspiring roar!");
            boost = 18;
        }
        this.warCryBoost = boost;
        this.warCryTurnsLeft = 2;
        this.attackPower += boost;
        System.out.println("\n  " + name + " roars a battle cry!");
        System.out.println("  Attack +" + boost + " for 2 turns.");
    }

    @Override
    public String getSpecialAbilityName() {
        return isRaging ? "RAGING [" + rageTurnsLeft + " turns]" : "Rage Mode";
    }

    @Override
    public String getClassDescription() {
        return name + " — Deserter knight. High HP and defense, moderate attack.\n" +
                "Special: Rage Mode — boosts attack for 3 turns.";
    }

    public boolean isRaging() {
        return isRaging;
    }
}
