package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.sound.midi.InvalidMidiDataException;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.awt.Toolkit;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import util.AssetLoader;
import util.InputHandler;

public class GamePanel extends JPanel {
    private enum SceneType {
        TITLE, DUSKWALL_MARKET, ROAD, FOREST, WAYSTATION, VILLAGE, TAVERN, VALDENMERE, SPIRE, FORTRESS, REFUGEE_CAMP,
        SACRED_FLAME, VAULT, COMBAT
    }

    private static GamePanel instance;
    private JTextArea textArea;
    private JScrollPane textScrollPane;
    private JPanel actionPanel;
    private JPanel uiTray;
    private GameCanvas canvas;
    private JLabel timerLabel;
    private JLabel sceneLabel;
    private JButton musicToggleButton;
    private Timer countdownTimer;
    private Sequencer musicSequencer;
    private int secondsRemaining = 90;
    private int timerMinChoice;
    private int timerMaxChoice;
    private boolean musicPlaying;
    private SceneType currentScene = SceneType.TITLE;
    private String currentSceneTitle = "A E T H E R M O O R";
    private String storyContext = "";

    // Darker, Grittier Palette na design/bacground na ginagamit sa buong laro
    private final Color bgColor = new Color(10, 12, 15);
    private final Color fgColor = new Color(220, 215, 200);
    private final Color accentColor = new Color(180, 150, 100);
    private final Color trayColor = new Color(15, 18, 22, 240);
    private final Color trayBorder = new Color(45, 42, 38);
    private final Color buttonBg = new Color(28, 32, 36);
    private final Color buttonBgHover = new Color(40, 45, 52);

    // Fonts
    private final Font logFont = new Font("Monospaced", Font.PLAIN, 15);
    private final Font uiFont = new Font("SansSerif", Font.BOLD, 13);
    private final Font titleFont = new Font("Serif", Font.BOLD, 22);

    public GamePanel() {
        setLayout(null);
        setBackground(bgColor);
        setSize(800, 600);
        initialize();
        instance = this;
        if (!java.beans.Beans.isDesignTime()) {
            redirectSystemOut();
        }
    }

    private void initialize() {
        setLayout(null);
        setBackground(bgColor);
        setSize(1366, 768);

        canvas = new GameCanvas();
        canvas.setLayout(null);
        canvas.setBounds(0, 0, 1366, 528);
        add(canvas);

        uiTray = new JPanel(null);
        uiTray.setOpaque(true);
        uiTray.setBackground(trayColor);
        uiTray.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, trayBorder));
        uiTray.setBounds(0, 528, 1366, 240);
        add(uiTray);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(fgColor);
        textArea.setBackground(trayColor);
        textArea.setFont(logFont);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(10, 15, 10, 15));

        textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, trayBorder));
        textScrollPane.getViewport().setOpaque(false);
        textScrollPane.setOpaque(false);
        textScrollPane.getVerticalScrollBar().setUI(createScrollBarUI());
        textScrollPane.setBounds(0, 0, 866, 240);
        uiTray.add(textScrollPane);

        actionPanel = new JPanel();
        actionPanel.setLayout(new javax.swing.BoxLayout(actionPanel, javax.swing.BoxLayout.Y_AXIS));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        actionPanel.setBounds(866, 0, 500, 240);
        uiTray.add(actionPanel);

        sceneLabel = new JLabel(currentSceneTitle);
        sceneLabel.setFont(titleFont);
        sceneLabel.setForeground(accentColor);
        sceneLabel.setOpaque(true);
        sceneLabel.setBackground(new Color(10, 12, 15, 180));
        sceneLabel.setHorizontalAlignment(JLabel.CENTER);
        sceneLabel.setBorder(BorderFactory.createLineBorder(trayBorder, 1));
        sceneLabel.setBounds(483, 20, 400, 40);
        canvas.add(sceneLabel);

        timerLabel = new JLabel();
        timerLabel.setFont(uiFont.deriveFont(15f));
        timerLabel.setForeground(fgColor);
        timerLabel.setBackground(new Color(10, 12, 15, 180));
        timerLabel.setOpaque(true);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setBorder(BorderFactory.createLineBorder(trayBorder, 1));
        timerLabel.setVisible(false);
        timerLabel.setBounds(20, 20, 200, 30);
        canvas.add(timerLabel);

        musicToggleButton = createStyledButton("Music: Off");
        musicToggleButton.setFont(uiFont.deriveFont(11f));
        musicToggleButton.addActionListener(e -> toggleMusic());
        musicToggleButton.setBounds(1216, 20, 130, 30);
        canvas.add(musicToggleButton);
    }

    public void cleanup() {
        stopChoiceTimer();
        if (musicSequencer != null) {
            musicSequencer.stop();
            musicSequencer.close();
        }
    }

    // yung mismong game screen
    private class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (java.beans.Beans.isDesignTime()) {
                try {
                    java.awt.Image bg = new javax.swing.ImageIcon("src/assets/images/scene_title.png").getImage();
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                    java.awt.Image chr = new javax.swing.ImageIcon("src/assets/images/char_knight.png").getImage();
                    int charH = (int) (getHeight() * 0.5);
                    int charW = (int) (charH * ((double) chr.getWidth(null) / chr.getHeight(null)));
                    g.drawImage(chr, (getWidth() - charW) / 2, (int) (getHeight() * 0.9) - charH, charW, charH, null);
                } catch (Exception e) {
                }
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintSceneBackground(g2, getWidth(), getHeight());

            int groundY = (int) (getHeight() * 0.9);
            int charH = (int) (getHeight() * 0.5);
            String selected = System.getProperty("aethermoor.selectedCharacter");

            // Pixel sprites: no antialiasing or bilinear scaling (prevents ghost fringes).
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            if (selected == null) {
                drawCharacter(g2, "Mage", (int) (getWidth() * 0.2), groundY, charH - 40);
                drawCharacter(g2, "Knight", (int) (getWidth() * 0.5), groundY, charH);
                drawCharacter(g2, "Priest", (int) (getWidth() * 0.8), groundY, charH - 20);
            } else {
                drawCharacter(g2, selected, (int) (getWidth() * 0.5), groundY, charH + 20);
            }
            g2.dispose();
        }
    }

    // para sa pag lagay ng background images ng scene para to sa aesthetic HAHAH
    private void paintSceneBackground(Graphics2D g2, int width, int height) {
        String sceneName = currentScene.name().toLowerCase();
        String resourcePath = "/assets/images/scene_" + sceneName + ".png";
        if (!cache.containsKey(resourcePath)) {
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                cache.put(resourcePath, new javax.swing.ImageIcon(imgURL).getImage());
            } else {
                cache.put(resourcePath, null);
            }
        }
        java.awt.Image bg = cache.get(resourcePath);
        if (bg != null) {
            double aspect = (double) bg.getWidth(null) / bg.getHeight(null);
            int dw = width, dh = height;
            if ((double) width / height > aspect)
                dh = (int) (width / aspect);
            else
                dw = (int) (height * aspect);
            g2.drawImage(bg, (width - dw) / 2, (height - dh) / 2, dw, dh, null);
        } else {
            g2.setPaint(new java.awt.GradientPaint(0, 0, new Color(20, 25, 30), 0, height, Color.BLACK));
            g2.fillRect(0, 0, width, height);
        }
    }

    // ginagamit yung character image tapos nilalagay sa screen pero dapat kasya
    // sila sa screen yung character nilagay na din na ginagamit base sa pinili mong
    // character para di mag ka iba yung character na lumalabas
    private String resolveCharacterAssetKey(String charClass) {
        if (charClass == null)
            return "knight";
        String key = charClass.toLowerCase();
        if (key.equals("eryn"))
            return "mage";
        if (key.equals("brennan"))
            return "knight";
        if (key.equals("solia"))
            return "priest";
        return key;
    }

    private void drawCharacter(Graphics2D g2, String charClass, int x, int gy, int h) {
        String assetKey = resolveCharacterAssetKey(charClass);
        BufferedImage img = loadCharacterImage(assetKey);

        if (img != null) {
            int imgW = img.getWidth();
            int imgH = img.getHeight();
            double aspect = (double) imgW / imgH;
            int dw = (int) (h * aspect);
            java.awt.Composite old = g2.getComposite();
            g2.setComposite(AlphaComposite.SrcOver);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(img, x - dw / 2, gy - h, dw, h, null);
            g2.setComposite(old);
        } else {
            g2.setColor(accentColor);
            g2.drawRect(x - 20, gy - h, 40, h);
        }
    }

    private BufferedImage loadCharacterImage(String assetKey) {
        return AssetLoader.loadCharacterSprite(assetKey);
    }

    // cache para malaman kung nasaan yung image tapos nilalagay sa screen
    private java.util.Map<String, java.awt.Image> cache = new java.util.HashMap<>();

    // update scene from text dapat yung title ng scene, pag sinabi kasing combat
    // dapat combat yung scene, tapos yung scene context dapat yung pinagsama na
    // title + story context para malaman yung scene
    private void updateSceneFromText(String t) {
        storyContext = (storyContext + " " + t).toLowerCase();
        if (storyContext.length() > 4000)
            storyContext = storyContext.substring(storyContext.length() - 4000);
        SceneType ns = currentScene;
        String nt = currentSceneTitle;
        if (containsAny(t, "ACT I", "ACT II", "ACT III"))
            nt = t.trim();
        if (containsAny(storyContext, "combat:", "attacks!", "confronts you", "guard", "battle")) {
            ns = SceneType.COMBAT;
            nt = "Combat";
        } else if (containsAny(storyContext, "vault", "flame")) {
            ns = SceneType.VAULT;
            nt = "The Vault";
        } else if (containsAny(storyContext, "duskwall")) {
            ns = SceneType.DUSKWALL_MARKET;
            nt = "Duskwall";
        } else if (containsAny(storyContext, "forest")) {
            ns = SceneType.FOREST;
            nt = "The Forest";
        } else if (containsAny(storyContext, "valdenmere")) {
            ns = SceneType.VALDENMERE;
            nt = "Valdenmere";
        }

        // para mas maganda yung pag load ng scene
        // ang ginagawa nito ay kung mag kaiba ba yung scene na niload o hindi
        // nilalagay na din yung title ng scene na sinasabi sa ibabaw ng game screen
        // para malaman kung ano yung scene na niloload
        boolean sceneChanged = (currentScene != ns);
        currentScene = ns;
        currentSceneTitle = nt;
        sceneLabel.setText(currentSceneTitle);

        if (sceneChanged) {
            canvas.repaint();
        }
    }

    // ginagamit to sa update scene from text para alam kung alin yung scene
    private boolean containsAny(String s, String... n) {
        for (String x : n)
            if (s.toLowerCase().contains(x))
                return true;
        return false;
    }

    // para ginagamit sa transparency ng image na ginagamit sa scene
    private void redirectSystemOut() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                appendOutput(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                appendOutput(new String(b, off, len));
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    // para makuha yung instance ng game panel
    public static GamePanel getInstance() {
        return instance;
    }

    // para sa pag add ng text sa game panel
    public void appendOutput(String t) {
        SwingUtilities.invokeLater(() -> {
            updateSceneFromText(t);
            textArea.append(t);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    // para sa pag add ng number choices sa game panel pero dapat kasya sila sa
    // screen
    public void showNumberChoices(int min, int max) {
        SwingUtilities.invokeLater(() -> {
            actionPanel.removeAll();
            if (max - min + 1 >= 3)
                startChoiceTimer(min, max);
            else
                stopChoiceTimer();
            for (int i = min; i <= max; i++) {
                JButton b = createStyledButton(String.valueOf(i));
                final int choice = i;
                b.addActionListener(e -> {
                    stopChoiceTimer();
                    clearButtons();
                    InputHandler.submitInput(String.valueOf(choice));
                });
                actionPanel.add(b);
                actionPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            }
            actionPanel.revalidate();
            actionPanel.repaint();
        });
    }

    // para sa pag add ng continue button sa game panel pag walang pinipili
    public void showContinueButton() {
        SwingUtilities.invokeLater(() -> {
            actionPanel.removeAll();
            stopChoiceTimer();
            JButton b = createStyledButton("Continue");
            b.addActionListener(e -> {
                clearButtons();
                InputHandler.submitInput("");
            });
            actionPanel.add(b);
            actionPanel.revalidate();
            actionPanel.repaint();
        });
    }

    // para sa pag clear ng buttons sa game panel pagkatapos pumili
    public void clearButtons() {
        SwingUtilities.invokeLater(() -> {
            actionPanel.removeAll();
            actionPanel.revalidate();
            actionPanel.repaint();
        });
    }

    // para sa pag create ng styled button sa game panel yun lng AHAHA
    private JButton createStyledButton(String t) {
        JButton b = new JButton(t);
        b.setFont(uiFont);
        b.setForeground(fgColor);
        b.setBackground(buttonBg);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(trayBorder, 1));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addChangeListener(e -> b.setBackground(b.getModel().isRollover() ? buttonBgHover : buttonBg));
        return b;
    }

    // para sa pag start ng choice timer sa game panel pag may time limit at 90
    // seconds yung time limit tapos pag naubos yung time pipili sya ng random
    // number tas submit nya yun
    public void startChoiceTimer(int min, int max) {
        timerMinChoice = min;
        timerMaxChoice = max;
        secondsRemaining = 90;
        timerLabel.setVisible(true);
        updateTimerLabel();
        if (countdownTimer != null)
            countdownTimer.stop();
        countdownTimer = new Timer(1000, e -> {
            if (secondsRemaining > 0) {
                secondsRemaining--;
                updateTimerLabel();
            } else {
                countdownTimer.stop();
                int r = ThreadLocalRandom.current().nextInt(timerMinChoice, timerMaxChoice + 1);
                appendOutput("\n[Time Up: Random choice " + r + "]\n");
                InputHandler.submitInput(String.valueOf(r));
            }
        });
        countdownTimer.start();
    }

    // para sa pag stop ng choice timer sa game panel makapili if hindi na mana need
    // ng timer sa scene
    public void stopChoiceTimer() {
        if (countdownTimer != null)
            countdownTimer.stop();
        timerLabel.setVisible(false);
    }

    // para sa pag update ng timer label sa game panel at mag change ng color
    // depende sa time remaining
    private void updateTimerLabel() {
        timerLabel.setText(String.format("%d:%02d", secondsRemaining / 60, secondsRemaining % 60));
        timerLabel.setForeground(secondsRemaining <= 10 ? Color.RED : fgColor);
    }

    // para sa pag toggle ng music sa game panel
    private void toggleMusic() {
        if (musicPlaying)
            stopMusic();
        else
            startMusic();
    }

    // para sa pag start ng music sa game panel
    private void startMusic() {
        try {
            if (musicSequencer == null) {
                musicSequencer = MidiSystem.getSequencer();
                musicSequencer.open();
                musicSequencer.setSequence(createBackgroundMusic());
                musicSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            }
            musicSequencer.start();
            musicPlaying = true;
            musicToggleButton.setText("Music: On");
        } catch (Exception e) {
        }
    }

    // para sa pag stop ng music sa game panel
    private void stopMusic() {
        if (musicSequencer != null)
            musicSequencer.stop();
        musicPlaying = false;
        musicToggleButton.setText("Music: Off");
    }

    // para sa pag create ng background music
    private Sequence createBackgroundMusic() throws InvalidMidiDataException {
        Sequence seq = new Sequence(Sequence.PPQ, 24);
        Track t = seq.createTrack();
        ShortMessage m = new ShortMessage();
        m.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 48, 0);
        t.add(new MidiEvent(m, 0));
        int[] mel = { 57, 60, 64, 60, 55, 59, 62, 59 };
        long tick = 0;
        for (int x : mel) {
            ShortMessage on = new ShortMessage();
            on.setMessage(ShortMessage.NOTE_ON, 0, x, 50);
            t.add(new MidiEvent(on, tick));
            ShortMessage off = new ShortMessage();
            off.setMessage(ShortMessage.NOTE_OFF, 0, x, 0);
            t.add(new MidiEvent(off, tick + 24));
            tick += 24;
        }
        // ang function neto code nato ay gumagawa ng background music parang sa game
        // tapos
        // ginagawa nya to para di nakaka boring habang naglalaro tas pag end na yung
        // music mag sisimula ulit tapos pag patay na yung music di sya gagana at yung
        // music toggle button ay magiging off
        return seq;
    }

    // para sa pag create ng scroll bar ui na parang sa game yung design pero pwede
    // din tanggalin to HAHAHAHA
    private class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createScrollButton();
            // ang function neto code nato ay gumagawa ng scroll bar ui na para sa game

        }

        @Override
        protected void configureScrollBarColors() {
            thumbColor = accentColor;
            trackColor = trayColor;
            // design ng scroll bar

        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createScrollButton();
            // ang function neto code nato ay gumagawa ng scroll bar ui na para sa game

        }

        private JButton createScrollButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder());
            return button;
            // eto naman yung ginagawa yung button ng scroll bar para di masama sa display
            // HAHAHAHA
        }
    }

    private BasicScrollBarUI createScrollBarUI() {
        return new CustomScrollBarUI();
        // then eto naman yung pinapasa yung custom scroll bar ui para sa game
    }
}
