package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CreditsPanel extends JPanel {

    private final Color bgColor = new Color(10, 12, 15);
    private final Color accentColor = new Color(180, 150, 100);
    private final Color fgColor = new Color(220, 215, 200);

    public CreditsPanel() {
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBackground(bgColor);
        setSize(1366, 768);

        JLabel lblTitle = new JLabel("C R E D I T S", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 64));
        lblTitle.setForeground(accentColor);
        lblTitle.setBounds(0, 150, 1366, 100);
        add(lblTitle);

        JLabel lblRole = new JLabel("", SwingConstants.CENTER);
        lblRole.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblRole.setForeground(accentColor.darker());
        lblRole.setBounds(0, 350, 1366, 30);
        add(lblRole);

        JLabel lblName = new JLabel("", SwingConstants.CENTER);
        lblName.setFont(new Font("Serif", Font.BOLD, 36));
        lblName.setForeground(fgColor);
        lblName.setBounds(0, 400, 1366, 40);
        add(lblName);

        JButton btn = new JButton("BACK TO MENU");
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.BLACK);
        btn.setBackground(accentColor);
        btn.setFocusPainted(false);
        btn.setBounds(583, 550, 200, 40);
        btn.addActionListener(e -> {
            util.InputHandler.submitInput("");
        });
        add(btn);
    }
}
