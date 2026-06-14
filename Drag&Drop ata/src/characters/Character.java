package characters;

import combat.Enemy;
import combat.Skill;
import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    protected String name;
    protected String title;
    protected int hp;
    protected int maxHp;
    protected int attackPower;
    protected int defense;
    protected int mana;
    protected int maxMana;
    protected int healthPotions;
    protected int manaPotions;
    protected int tempAttackBoost;
    protected int attackBoostDuration;
    protected int tempDefenseBoost;
    protected int defenseBoostDuration;
    protected List<Skill> skills;

    public Character(String name, String title, int hp, int attack, int defense, int mana) {
        this.name = name;
        this.title = title;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attack;
        this.defense = defense;
        this.mana = mana;
        this.maxMana = mana;
        this.healthPotions = 3;
        this.manaPotions = 3;
        this.skills = new ArrayList<>();
    }

    public abstract void useSpecialAbility(Enemy enemy);
    public abstract String getSpecialAbilityName();
    public abstract String getClassDescription();

    public void takeDamage(int damage) {
        int reduced = Math.max(1, damage - getDefense());
        this.hp = Math.max(0, this.hp - reduced);
        System.out.println("  " + name + " takes " + reduced + " damage! HP: " + hp + "/" + maxHp);
    }

    public void heal(int amount) {
        int before = hp;
        hp = Math.min(maxHp, hp + amount);
        System.out.println("  " + name + " heals " + (hp - before) + " HP! HP: " + hp + "/" + maxHp);
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    public boolean isAlive() { return hp > 0; }

    public String getStatusBar() {
        String buffs = "";
        if (attackBoostDuration > 0) buffs += "[ATK UP] ";
        if (defenseBoostDuration > 0) buffs += "[DEF UP] ";
        return "❤  HP: " + hp + "/" + maxHp + "   💧 Mana: " + mana + "/" + maxMana + "   " + buffs;
    }

    public void tickBuffs() {
        if (attackBoostDuration > 0) {
            attackBoostDuration--;
            if (attackBoostDuration == 0) tempAttackBoost = 0;
        }
        if (defenseBoostDuration > 0) {
            defenseBoostDuration--;
            if (defenseBoostDuration == 0) tempDefenseBoost = 0;
        }
    }

    public void applyAttackBuff(int amount, int duration) {
        this.tempAttackBoost = amount;
        this.attackBoostDuration = duration;
    }

    public void applyDefenseBuff(int amount, int duration) {
        this.tempDefenseBoost = amount;
        this.defenseBoostDuration = duration;
    }

    // Getters
    public String getName() { return name; }
    public String getTitle() { return title; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower + tempAttackBoost; }
    public int getDefense() { return defense + tempDefenseBoost; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getHealthPotions() { return healthPotions; }
    public int getManaPotions() { return manaPotions; }
    public List<Skill> getSkills() { return skills; }

    // Setters
    public void setDefense(int defense) { this.defense = defense; }
    public void setMana(int mana) { this.mana = mana; }
    public void setAttackPower(int attackPower) { this.attackPower = attackPower; }
    public void useHealthPotion() { if (healthPotions > 0) healthPotions--; }
    public void useManaPotion() { if (manaPotions > 0) manaPotions--; }
    public void addHealthPotion() { healthPotions++; }
    public void addManaPotion() { manaPotions++; }
}
