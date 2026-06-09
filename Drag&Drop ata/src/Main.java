import characters.*;
import characters.Character;
import engine.*;
import util.*;

public class Main {

    public static void main(String[] args) {
        // para sa game logic para hindi mag freeze ang UI
        new Thread(() -> {
            // ni initailize ang main frame para sa game
            javax.swing.SwingUtilities.invokeLater(() -> {
                ui.MainFrame app = ui.MainFrame.getInstance();
                app.addPanel(new ui.MainMenuPanel(), "MENU");
                app.addPanel(new ui.CreditsPanel(), "CREDITS");
                app.addPanel(new ui.CharacterSelectPanel(), "SELECT");
                app.addPanel(new ui.GamePanel(), "GAME");
                app.showPanel("MENU");
                app.setVisible(true);
            });

            showMainMenu();
            Party party = chooseParty();

            // para lumipat ng panel
            javax.swing.SwingUtilities.invokeLater(() -> {
                ui.MainFrame.getInstance().showPanel("GAME");
            });

            StoryManager storyManager = new StoryManager(party);
            GameEngine engine = new GameEngine(party, storyManager);
            engine.start();
        }).start();
    }

    private static void showMainMenu() {
        boolean startGame = false;
        while (!startGame) {
            // para makita menu panel
            javax.swing.SwingUtilities.invokeLater(() -> {
                ui.MainFrame.getInstance().showPanel("MENU");
            });

            // dito iniintay ang input ng user pra malaro na HAHA
            int choice = InputHandler.getInt(1, 4);
            switch (choice) {
                case 1 -> startGame = true;
                case 3 -> {
                    // Switch to Credits panel in UI 
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        ui.MainFrame.getInstance().showPanel("CREDITS");
                    });
                    
                    // Wait for the user to press "Back"
                    InputHandler.waitForEnter();
                    
                    // Switch back to Menu panel
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        ui.MainFrame.getInstance().showPanel("MENU");
                    });
                }
                case 4 -> System.exit(0);
            }
        }
    }

    private static Party chooseParty() {
        // para makita character select panel
        javax.swing.SwingUtilities.invokeLater(() -> {
            ui.MainFrame.getInstance().showPanel("SELECT");
        });

        // dito iniintay ang input ng user pra makapili na ng character HAHA
        String choice = "";
        while (!choice.equals("START")) {
            choice = InputHandler.getString();
        }
        
        // dito nilalagay sa system properties kung sino yung first character sa party 
        // para malaman ng game engine kung sino yung selected character
        if (ui.CharacterSelectPanel.selectedParty.getPartySize() > 0) {
            Character first = ui.CharacterSelectPanel.selectedParty.getMembers().get(0);
            if (first instanceof Mage || first instanceof Eryn) {
                System.setProperty("aethermoor.selectedCharacter", "Mage");
            } else if (first instanceof Knight || first instanceof Brennan) {
                System.setProperty("aethermoor.selectedCharacter", "Knight");
            } else if (first instanceof Priest || first instanceof Solia) {
                System.setProperty("aethermoor.selectedCharacter", "Priest");
            } else if (first instanceof Mira) {
                System.setProperty("aethermoor.selectedCharacter", "Mira");
            } else if (first instanceof Sera) {
                System.setProperty("aethermoor.selectedCharacter", "Sera");
            } else {
                System.setProperty("aethermoor.selectedCharacter", "Knight");
            }
        }
        return ui.CharacterSelectPanel.selectedParty;
    }
}
