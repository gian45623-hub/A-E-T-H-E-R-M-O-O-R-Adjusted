package ui;

import characters.*;
import characters.Character;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import util.InputHandler;

public class CharacterSelectPanel extends JPanel { // dito nilalagay yung design ng character select panel
    private final Color bgColor = new Color(15, 18, 22); // design ng background color ng character select panel
    private final Color accentColor = new Color(180, 150, 100); // design ng accent color ng character select panel
    private final Color fgColor = new Color(220, 215, 200); // design ng foreground color ng character select panel

    public static Character selectedCharacter = null; // dito nilalagay yung selected character
    private JLabel partyStatusLabel; // design ng party status label
    private JButton startAdventureBtn; // design ng start adventure button
    private java.util.List<JButton> selectionButtons = new java.util.ArrayList<>(); // design ng selection buttons

    public CharacterSelectPanel() {
        initialize(); // para ma load yung design ng character select panel
    }

    private void initialize() { // dito nilalagay yung design ng character select panel
        setLayout(null); // design ng layout ng character select panel
        setBackground(bgColor); // design ng background color ng character select panel
        setSize(1366, 768); // design ng size ng character select panel

        JLabel header = new JLabel("SELECT YOUR CHARACTER", SwingConstants.CENTER); // design ng header text ng
                                                                                    // character select panel
        header.setFont(new Font("Serif", Font.BOLD, 48)); // design ng font ng header text ng character select panel
        header.setForeground(accentColor); // design ng color ng header text ng character select panel
        header.setBounds(0, 30, 1366, 50); // design ng position ng header text ng character select panel
        add(header); // eto naman yung pag add ng header text ng character select panel

        partyStatusLabel = new JLabel("Selected: None", SwingConstants.CENTER); // design ng party status label
        partyStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        partyStatusLabel.setForeground(fgColor);
        partyStatusLabel.setBounds(0, 80, 1366, 30);
        add(partyStatusLabel);

        JButton returnBtn = new JButton("RETURN");
        returnBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        returnBtn.setForeground(fgColor);
        returnBtn.setBackground(new Color(30, 35, 45));
        returnBtn.setFocusPainted(false);
        returnBtn.setBounds(20, 20, 100, 40);
        returnBtn.addActionListener(e -> {
            InputHandler.submitInput("RETURN");
        });
        add(returnBtn);

        // Character Grid (Absolute Layout inside ScrollPane)
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(null);
        gridPanel.setOpaque(false);
        gridPanel.setPreferredSize(new Dimension(1366, 900));

        JPanel erynCard = createCharacterCard("ERYN VOSS", "The Exiled Scholar",
                "\"I was right. I was always right. Now I'll prove it.\"",
                "High damage. Low defense. Raw power mechanic.", new Eryn(), "char_mage");
        erynCard.setBounds(303, 20, 250, 420);
        gridPanel.add(erynCard);

        JPanel brennanCard = createCharacterCard("BRENNAN ASHVANE", "The Oathbreaker",
                "\"I did something I can never undo. That doesn't mean I stop.\"",
                "High HP. Strong defense. Rage mechanic.", new Brennan(), "char_knight");
        brennanCard.setBounds(573, 20, 250, 420);
        gridPanel.add(brennanCard);

        JPanel soliaCard = createCharacterCard("SOLIA REN", "The Faithless Healer",
                "\"I don't know if anyone is listening. I'll pray anyway.\"",
                "Balanced. Healer. Faith mechanic.", new Solia(), "char_priest");
        soliaCard.setBounds(843, 20, 250, 420);
        gridPanel.add(soliaCard);

        JPanel miraCard = createCharacterCard("MIRA CAEL", "The Ghost",
                "\"One minute for Lena. Then I go to work.\"",
                "High speed. Stealth. Shadow strike.", new Mira(), "char_mira");
        miraCard.setBounds(438, 460, 250, 420);
        gridPanel.add(miraCard);

        JPanel seraCard = createCharacterCard("SERA CALDWELL", "The Eastern Scout",
                "\"I mentioned the wire. Watch the ground specifically.\"",
                "Ranged specialist. Focused shot.", new Sera(), "char_sera");
        seraCard.setBounds(708, 460, 250, 420);
        gridPanel.add(seraCard);

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 130, 1366, 450);
        add(scrollPane);

        startAdventureBtn = new JButton("START ADVENTURE");
        startAdventureBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        startAdventureBtn.setForeground(Color.BLACK);
        startAdventureBtn.setBackground(accentColor);
        startAdventureBtn.setFocusPainted(false);
        startAdventureBtn.setBounds(533, 600, 300, 50);
        startAdventureBtn.setEnabled(false);
        startAdventureBtn.addActionListener(e -> {
            InputHandler.submitInput("START");
        });
        add(startAdventureBtn);
    }

    private JPanel createCharacterCard(String name, String sub, String quote, String desc, Character charObj,
            String baseImgName) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(25, 28, 35));
        card.setBorder(BorderFactory.createLineBorder(new Color(45, 48, 55), 2));
        card.setPreferredSize(new Dimension(280, 420));

        JPanel portraitContainer = new JPanel(new BorderLayout());
        portraitContainer.setOpaque(false);
        portraitContainer.setPreferredSize(new Dimension(280, 200));
        portraitContainer.setMaximumSize(new Dimension(280, 200));

        if (java.beans.Beans.isDesignTime()) {
            Image rawImg = new ImageIcon("src/assets/images/" + baseImgName + ".png").getImage();
            int targetH = 190;
            int iw = rawImg.getWidth(null);
            int ih = rawImg.getHeight(null);
            int targetW = 200;
            if (iw > 0 && ih > 0) {
                double aspect = (double) iw / ih;
                targetW = (int) (targetH * aspect);
                if (targetW > 270) {
                    targetW = 270;
                    targetH = (int) (targetW / aspect);
                }
            }
            Image scaled = rawImg.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
            JLabel portrait = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
            portraitContainer.add(portrait, BorderLayout.CENTER);
        } else {
            // 1. Load the image safely using the class loader (works everywhere, including
            // JARs)
            java.net.URL imgURL = getClass().getResource("/assets/images/" + baseImgName + ".png");

            if (imgURL != null) {
                Image rawImg = new ImageIcon(imgURL).getImage();
                int targetH = 190;
                int iw = rawImg.getWidth(null);
                int ih = rawImg.getHeight(null);
                int targetW = 200;

                if (iw > 0 && ih > 0) {
                    double aspect = (double) iw / ih;
                    targetW = (int) (targetH * aspect);
                    if (targetW > 270) {
                        targetW = 270;
                        targetH = (int) (targetW / aspect);
                    }
                }

                Image scaled = rawImg.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
                JLabel portrait = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
                portraitContainer.add(portrait, BorderLayout.CENTER);
            } else {
                System.err.println("Could not find file: /assets/images/" + baseImgName + ".png");
                JLabel missing = new JLabel("[Portrait Missing]", SwingConstants.CENTER);
                missing.setForeground(Color.GRAY);
                portraitContainer.add(missing, BorderLayout.CENTER);
            }
        }

        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(portraitContainer);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel nameLbl = new JLabel(name.trim(), SwingConstants.CENTER);
        nameLbl.setFont(new Font("Serif", Font.BOLD, 22));
        nameLbl.setForeground(accentColor);
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLbl);

        JLabel subLbl = new JLabel(sub, SwingConstants.CENTER);
        subLbl.setFont(new Font("SansSerif", Font.ITALIC, 16));
        subLbl.setForeground(fgColor.darker());
        subLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subLbl);

        card.add(Box.createRigidArea(new Dimension(0, 5)));

        String htmlQuote = quote.trim().replace("\n\n", "\n").replace("\n", "<br/>");
        JLabel quoteLbl = new JLabel(
                "<html><div style='text-align: center; width: 190px;'><i>" + htmlQuote + "</i></div></html>",
                SwingConstants.CENTER);
        quoteLbl.setFont(new Font("Serif", Font.PLAIN, 14));
        quoteLbl.setForeground(fgColor);
        quoteLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(quoteLbl);

        String htmlDesc = desc.trim().replace("\n\n", "\n").replace("\n", "<br/>");
        JLabel descLbl = new JLabel(
                "<html><div style='text-align: center; width: 190px;'>" + htmlDesc + "</div></html>",
                SwingConstants.CENTER);
        descLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLbl.setForeground(accentColor.darker());
        descLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(descLbl);

        card.add(Box.createVerticalGlue());

        JButton selectBtn = new JButton("SELECT");
        selectBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectBtn.setForeground(Color.BLACK);
        selectBtn.setBackground(accentColor);
        selectBtn.setFocusPainted(false);
        selectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectBtn.setMaximumSize(new Dimension(150, 35));
        selectBtn.addActionListener(e -> {
            if (selectedCharacter == charObj) {
                // Deselect
                selectedCharacter = null;
                selectBtn.setText("SELECT");
                selectBtn.setBackground(accentColor);
            } else {
                // Select new
                selectedCharacter = charObj;

                // Visually deselect all other buttons
                for (JButton btn : selectionButtons) {
                    btn.setText("SELECT");
                    btn.setBackground(accentColor);
                }

                selectBtn.setText("SELECTED");
                selectBtn.setBackground(new Color(100, 180, 100)); // Green for selected
            }

            if (selectedCharacter != null) {
                partyStatusLabel.setText("Selected: " + selectedCharacter.getName());
            } else {
                partyStatusLabel.setText("Selected: None");
            }
            startAdventureBtn.setEnabled(selectedCharacter != null);
        });

        selectionButtons.add(selectBtn);
        card.add(selectBtn);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        return card;
    }
}
