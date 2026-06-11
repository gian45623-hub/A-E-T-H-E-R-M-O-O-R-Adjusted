package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import util.InputHandler;

// eto naman yung main menu panel ng game para sa card layout at pag lipat ng panel (Start, Credits, Exit)
public class MainMenuPanel extends JPanel {

    private final Color accentColor = new Color(180, 150, 100);
    // design ng accent color (ang accent color ay yung
    // kulay na ginagamit para sa mga buttons at iba pang
    // interactive elements)
    private final Color fgColor = new Color(220, 215, 200);
    // design ng foreground color (ang foreground color ay yung
    // kulay ng text na ginagamit para sa mga buttons at iba
    // pang interactive elements)
    // such as yung buttons and stuff

    public MainMenuPanel() {
        initialize();
    }

    private void initialize() {
        setLayout(null); // design layout ng panel
        setBackground(new Color(20, 25, 30)); // design ng background ng panel
        setSize(1366, 768); // design ng size ng panel

        JLabel lblTitle = new JLabel("A E T H E R M O O R", SwingConstants.CENTER); // design ng title
        lblTitle.setFont(new Font("Serif", Font.BOLD, 72)); // design ng title
        lblTitle.setForeground(accentColor); // design ng title (font color)
        lblTitle.setBounds(0, 150, 1366, 100); // position ng title
        add(lblTitle);

        JLabel lblSubtitle = new JLabel("A Broken Kingdom. Three Souls. Long Journey.", SwingConstants.CENTER); // design
                                                                                                                // ng
                                                                                                                // subtitle
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 24)); // design ng subtitle (font style)
        lblSubtitle.setForeground(fgColor.darker()); // design ng subtitle (font color)
        lblSubtitle.setBounds(0, 250, 1366, 50); // position ng subtitle
        add(lblSubtitle);

        JButton btnStart = createMenuButton("START NEW GAME"); // design ng start new game button
        btnStart.setBounds(533, 350, 300, 50); // position ng start new game button
        btnStart.addActionListener(e -> {
            InputHandler.submitInput("1");
        }); // eto naman yung pag click ng start new game button
            // then mag lilipat sa character selection panel
        add(btnStart);

        JButton btnCredits = createMenuButton("CREDITS"); // design ng credits button
        btnCredits.setBounds(533, 420, 300, 50); // position ng credits button
        btnCredits.addActionListener(e -> {
            InputHandler.submitInput("3");
        });// eto naman yung pag click ng credits button
           // then mag lilipat sa credits panel
        add(btnCredits);

        JButton btnExit = createMenuButton("EXIT TO DESKTOP"); // design ng exit to desktop button
        btnExit.setBounds(533, 490, 300, 50); // position ng exit to desktop button
        btnExit.addActionListener(e -> {
            InputHandler.submitInput("4");
            System.exit(0);
        }); // eto naman yung pag click ng exit to desktop
            // then mag exit yung game
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
