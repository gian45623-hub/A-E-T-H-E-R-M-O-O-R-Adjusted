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
        this.skills = new ArrayList<>();
    }

    public abstract void useSpecialAbility(Enemy enemy);
    public abstract String getSpecialAbilityName();
    public abstract String getClassDescription();

    public void takeDamage(int damage) {
        int reduced = Math.max(1, damage - defense);
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
        return "❤  HP: " + hp + "/" + maxHp + "   💧 Mana: " + mana + "/" + maxMana;
    }

    // Getters
    public String getName() { return name; }
    public String getTitle() { return title; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public List<Skill> getSkills() { return skills; }

    // Setters
    public void setDefense(int defense) { this.defense = defense; }
    public void setMana(int mana) { this.mana = mana; }
    public void setAttackPower(int attackPower) { this.attackPower = attackPower; }
}
