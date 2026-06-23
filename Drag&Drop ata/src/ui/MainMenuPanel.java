package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import util.InputHandler;

// eto naman yung main menu panel ng game para sa card layout at pag lipat ng panel (Start, Credits, Exit)
public class MainMenuPanel extends JPanel {

    private final Color accentColor = new Color(180, 150, 100);

    private final Color fgColor = new Color(220, 215, 200);

    private Image backgroundImage;

    public MainMenuPanel() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/main_menu_bg.jpg"));
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void initialize() {
        setLayout(null);
        setBackground(new Color(20, 25, 30));
        setSize(1366, 768);

        JLabel lblTitle = new JLabel("A E T H E R M O O R", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 72));
        lblTitle.setForeground(accentColor);
        lblTitle.setBounds(0, 150, 1366, 100);
        add(lblTitle);

        JLabel lblSubtitle = new JLabel(".A Broken Kingdom.  .Lost Souls.  .One Truth To Uncover.",
                SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 24));
        lblSubtitle.setForeground(Color.WHITE);
        lblSubtitle.setBounds(0, 250, 1366, 50);
        add(lblSubtitle);

        JButton btnStart = createMenuButton("START NEW GAME");
        btnStart.setBounds(533, 350, 300, 50);
        btnStart.addActionListener(e -> {
            InputHandler.submitInput("1");
        });
        add(btnStart);

        JButton btnCredits = createMenuButton("CREDITS");
        btnCredits.setBounds(533, 420, 300, 50);
        btnCredits.addActionListener(e -> {
            InputHandler.submitInput("3");
        });
        add(btnCredits);

        JButton btnExit = createMenuButton("EXIT TO DESKTOP");
        btnExit.setBounds(533, 490, 300, 50);
        btnExit.addActionListener(e -> {
            InputHandler.submitInput("4");
            System.exit(0);
        });
        add(btnExit);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 22));
        btn.setForeground(fgColor);
        btn.setBackground(new Color(30, 35, 45));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 1),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)));
        btn.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(400, 60));
        // pwede mo rin baguhin yung size ng button,
        // basta gamitin mo lang yung setBounds() instead of setMaximumSize()
        // e.g. setBounds(x, y, width, height)
        // x = left position
        // y = top position
        // width = width
        // height = height

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(accentColor);
                btn.setForeground(Color.BLACK);
                // dito naman yung pag umaandar yung mouse sa button
                // like hovering dun sa button
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(30, 35, 45));
                btn.setForeground(fgColor);
                // dito naman yung pag umaalis yung mouse sa button
                // like when you move your mouse away from the button, babalik sa original color
            }
        });

        btn.addActionListener(e -> {
            // eto yung para sa card layout para lumipat ng panel
        });

        return btn;
    }
}
