package util;

// ginamit para ma keep yung value ng character na napili :D
public class VisualState {
    private static String selectedCharacter;

    public static synchronized void setSelectedCharacter(String characterClass) {
        selectedCharacter = characterClass;
    }

    public static synchronized String getSelectedCharacter() {
        return selectedCharacter;
    }
}
