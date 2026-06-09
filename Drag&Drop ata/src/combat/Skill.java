package combat;

public class Skill {
    private String name;
    private int power;       // damage dealt (negative = healing)
    private int manaCost;
    private String description;

    public Skill(String name, int power, int manaCost, String description) {
        this.name = name;
        this.power = power;
        this.manaCost = manaCost;
        this.description = description;
    }

    public String getName() { return name; }
    public int getPower() { return power; }
    public int getManaCost() { return manaCost; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        String type = power < 0 ? "Heals " + Math.abs(power) + " HP" : "Power: " + power;
        return name + " [" + type + " | Mana: " + manaCost + "] - " + description;
    }
}
