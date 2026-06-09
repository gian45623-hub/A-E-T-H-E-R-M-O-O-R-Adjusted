package engine;

import characters.Character;
import java.util.ArrayList;
import java.util.List;

public class Party {
    private List<Character> members;
    private Character activeCharacter;
    private int healthPotions;
    private int manaPotions;

    public Party() {
        this.members = new ArrayList<>();
        this.healthPotions = 5; // Default starting items
        this.manaPotions = 3;
    }

    public void addMember(Character character) {
        if (members.size() < 5) {
            members.add(character);
            if (activeCharacter == null) {
                activeCharacter = character;
            }
        }
    }

    public void removeMember(Character character) {
        if (members.remove(character)) {
            if (activeCharacter == character) {
                if (!members.isEmpty()) {
                    activeCharacter = members.get(0);
                } else {
                    activeCharacter = null;
                }
            }
        }
    }

    public void setActiveCharacter(int index) {
        if (index >= 0 && index < members.size()) {
            activeCharacter = members.get(index);
        }
    }

    public void setActiveCharacter(Character character) {
        if (members.contains(character)) {
            activeCharacter = character;
        }
    }

    public Character getActiveCharacter() {
        return activeCharacter;
    }

    public List<Character> getMembers() {
        return new ArrayList<>(members);
    }

    public int getPartySize() {
        return members.size();
    }

    public boolean isPartyAlive() {
        for (Character member : members) {
            if (member.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public int getAliveCount() {
        int count = 0;
        for (Character member : members) {
            if (member.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public void restAllMembers(int hpAmount) {
        for (Character member : members) {
            member.heal(hpAmount);
        }
    }

    public void restoreManaAllMembers(int manaAmount) {
        for (Character member : members) {
            member.restoreMana(manaAmount);
        }
    }

    public String getPartyStatus() {
        StringBuilder status = new StringBuilder();
        status.append("═══════ PARTY STATUS ═══════\n");
        for (int i = 0; i < members.size(); i++) {
            Character member = members.get(i);
            String marker = (member == activeCharacter) ? "► " : "  ";
            String alive = member.isAlive() ? "✓" : "✗";
            status.append(marker).append(alive).append(" ")
                  .append(member.getName()).append(" (").append(member.getTitle()).append(")\n")
                  .append("    HP: ").append(member.getHp()).append("/").append(member.getMaxHp())
                  .append(" | Mana: ").append(member.getMana()).append("/").append(member.getMaxMana()).append("\n");
        }
        status.append("    Inventory: Health Potions [x").append(healthPotions)
              .append("] | Mana Potions [x").append(manaPotions).append("]\n");
        status.append("═══════════════════════════\n");
        return status.toString();
    }

    // Item System Methods
    public int getHealthPotions() { return healthPotions; }
    public int getManaPotions() { return manaPotions; }
    public void useHealthPotion() { if (healthPotions > 0) healthPotions--; }
    public void useManaPotion() { if (manaPotions > 0) manaPotions--; }
    public void addHealthPotions(int count) { healthPotions += count; }
    public void addManaPotions(int count) { manaPotions += count; }
}
