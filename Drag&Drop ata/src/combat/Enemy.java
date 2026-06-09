package combat;

public class Enemy {
    private String name;
    private String description;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private String specialMove;
    private int specialMoveCooldown;
    private int turnsSinceSpecial;

    public Enemy(String name, String description, int hp, int attack, int defense, String specialMove, int cooldown) {
        this.name = name;
        this.description = description;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialMove = specialMove;
        this.specialMoveCooldown = cooldown;
        this.turnsSinceSpecial = cooldown; // ready from the start
    }

    public void takeDamage(int damage) {
        int reduced = Math.max(1, damage - defense);
        this.hp -= reduced;
        System.out.println("  " + name + " takes " + reduced + " damage! HP: " + Math.max(0, hp) + "/" + maxHp);
    }

    public int attackPlayer() {
        turnsSinceSpecial++;
        if (turnsSinceSpecial >= specialMoveCooldown) {
            turnsSinceSpecial = 0;
            System.out.println("  ⚠️  " + name + " uses " + specialMove + "!");
            return attack + 15; // special hits harder
        }
        return attack + (int)(Math.random() * 8);
    }

    public boolean isAlive() { return hp > 0; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getAttack() { return attack; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }

    // ─── Pre-built enemies for each character's story ───────────────────────

    public static Enemy ashenSoldier() {
        return new Enemy(
            "Ashen Soldier",
            "A hollow-eyed corpse in rusted armor. It moves with unnerving purpose.",
            60, 18, 3,
            "Greying Grasp", 3
        );
    }

    public static Enemy corruptedConstruct() {
        return new Enemy(
            "Corrupted Construct",
            "A magical automaton gone wrong — crackling with dark arcane energy.",
            90, 22, 8,
            "Arcane Overload", 4
        );
    }

    public static Enemy ashenHandBandit() {
        return new Enemy(
            "Ashen Hand Cultist",
            "A human fanatic with wild eyes, wearing robes marked with the Greying symbol.",
            55, 20, 5,
            "Fanatical Strike", 3
        );
    }

    public static Enemy ashenBrute() {
        return new Enemy(
            "Ashen Brute",
            "A massive undead creature, its body swollen with Greying corruption.",
            120, 28, 10,
            "Slam", 2
        );
    }

    public static Enemy corruptedPriest() {
        return new Enemy(
            "Corrupted Priest",
            "A former Sacred Flame priest, now twisted by Aldran's dark relics.",
            80, 20, 7,
            "Dark Blessing", 3
        );
    }

    public static Enemy rogueArcanist() {
        return new Enemy(
            "Rogue Arcanist",
            "A mage who has given himself to the Greying's power. His eyes glow pitch black.",
            75, 30, 4,
            "Void Burst", 3
        );
    }

    // ─── Boss Enemies ────────────────────────────────────────────────────────

    public static Enemy bossSevik() {
        return new Enemy(
            "Sevik — The Broker's Betrayal",
            "The information broker drops his friendly mask. He was Valdros's spy all along.",
            150, 25, 8,
            "Poisoned Blade", 3
        );
    }

    public static Enemy bossValdros() {
        return new Enemy(
            "Valdros — The Architect",
            "Eryn's master. The mind behind the Greying. He floats above the ground, wrapped in swirling dark energy.",
            220, 38, 12,
            "Greying Nova", 3
        );
    }

    /** @deprecated use bossValdros() */
    public static Enemy bossDael() {
        return bossValdros();
    }

    public static Enemy bossVeyran() {
        return new Enemy(
            "Lord Marshal Veyran",
            "A towering man in blackened plate armor. His eyes show no remorse — only certainty.",
            250, 35, 18,
            "Iron Verdict", 3
        );
    }

    public static Enemy bossAldran() {
        return new Enemy(
            "High Priest Aldran",
            "Your mentor. He looks at you with pity, not malice. That somehow makes it worse.",
            200, 28, 10,
            "Divine Wrath", 3
        );
    }
}
