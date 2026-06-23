import characters.*;
import characters.Character;
import engine.*;
import util.*;

public class Main {
    public static Thread gameThread;

    public static void main(String[] args) {
        // init UI on event dispatch thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            ui.MainFrame app = ui.MainFrame.getInstance();
            app.addPanel(new ui.MainMenuPanel(), "MENU");
            app.addPanel(new ui.CreditsPanel(), "CREDITS");
            app.addPanel(new ui.CharacterSelectPanel(), "SELECT");
            app.addPanel(new ui.GamePanel(), "GAME");
            app.showPanel("MENU");
            app.setVisible(true);
        });

        // para sa game logic para hindi mag freeze ang UI
        gameThread = new Thread(() -> {
            util.InputHandler.setGameThread(Thread.currentThread());
            boolean skipMenu = false;
            while (true) {
                try {
                    Character player = null;
                    while (player == null) {
                        if (!skipMenu) {
                            showMainMenu();
                        }
                        skipMenu = false;
                        player = chooseCharacter();
                    }

                    // para lumipat ng panel
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        ui.MainFrame.getInstance().showPanel("GAME");
                    });

                    StoryManager storyManager = new StoryManager(player);
                    GameEngine engine = new GameEngine(player, storyManager);
                    engine.start();
                } catch (RuntimeException e) {
                    if ("RESTART_GAME".equals(e.getMessage())) {
                        // Game was interrupted to return to Character Select
                        // Clear the interrupted status
                        Thread.interrupted();
                        skipMenu = true;
                        continue;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        });
        gameThread.start();
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

    private static Character chooseCharacter() {
        // para makita character select panel
        javax.swing.SwingUtilities.invokeLater(() -> {
            ui.MainFrame.getInstance().showPanel("SELECT");
        });

        // dito iniintay ang input ng user pra makapili na ng character HAHA
        String choice = "";
        while (!choice.equals("START") && !choice.equals("RETURN")) {
            choice = InputHandler.getString();
        }

        if (choice.equals("RETURN")) {
            return null;
        }

        // dito nilalagay sa system properties kung sino yung first character sa party
        // para malaman ng game engine kung sino yung selected character
        if (ui.CharacterSelectPanel.selectedCharacter != null) {
            Character first = ui.CharacterSelectPanel.selectedCharacter;
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
        // dito ko nilagay para bumalik ng character select pag na click yung start
        javax.swing.SwingUtilities.invokeLater(() -> {
            ui.MainFrame.getInstance().showPanel("SELECT");
        });
        // di to na papansin pag may bagong update sa code HAHAHAHHAHAHAHAHAH
        // pero sana ma notice to
        return ui.CharacterSelectPanel.selectedCharacter;
    }
}