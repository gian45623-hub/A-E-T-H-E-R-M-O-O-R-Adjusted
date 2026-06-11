package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CreditsPanel extends JPanel {
    // eto naman yung credits panel na may design at may button para makabalik sa
    // menu
    private final Color bgColor = new Color(10, 12, 15); // design ng background color ng panel
    private final Color accentColor = new Color(180, 150, 100); // accent color ng panel
    private final Color fgColor = new Color(220, 215, 200); // foreground color ng panel

    // eto naman yung pag initialize ng credits panel
    public CreditsPanel() {
        initialize();
    }

    private void initialize() { // eto naman yung pag initialize para sa credits panel
        setLayout(null); // design ng layout ng panel
        setBackground(bgColor); // design ng background color ng panel
        setSize(1366, 768); // size ng credits panel

        JLabel lblTitle = new JLabel("C R E D I T S", SwingConstants.CENTER); // design ng title ng credits panel
        lblTitle.setFont(new Font("Serif", Font.BOLD, 64)); // font ng title ng credits panel
        lblTitle.setForeground(accentColor); // design ng color ng title ng credits panel
        lblTitle.setBounds(0, 150, 1366, 100); // design ng position ng title ng credits panel
        add(lblTitle); // eto naman yung pag add ng title ng credits panel

        JLabel lblRole = new JLabel("[TYPE ROLE HERE]", SwingConstants.CENTER); // design ng role ng credits panel
        lblRole.setFont(new Font("SansSerif", Font.BOLD, 18)); // font ng role ng credits panel
        lblRole.setForeground(accentColor.darker()); // design ng color ng role ng credits panel
        lblRole.setBounds(0, 350, 1366, 30); // design ng position ng role ng credits panel
        add(lblRole); // eto naman yung pag add ng role ng credits panel

        JLabel lblName = new JLabel("[TYPE NAME HERE]", SwingConstants.CENTER); // design ng name para sa credits panel
        lblName.setFont(new Font("Serif", Font.BOLD, 36)); // font ng name para sa credits panel
        lblName.setForeground(fgColor); // design ng color ng name para sa credits panel
        lblName.setBounds(0, 400, 1366, 40); // design ng position ng name para sa credits panel
        add(lblName); // eto naman yung pag add ng name para sa credits panel

        JLabel lblinfo = new JLabel("THIS GAME IS FOR SCHOOL PROJECT ONLY", SwingConstants.CENTER); // design ng info
                                                                                                    // text
        lblinfo.setFont(new Font("Serif", Font.PLAIN, 12)); // font ng info text
        lblinfo.setForeground(fgColor); // design ng color ng info text
        lblinfo.setBounds(0, 450, 1366, 40); // design ng position ng info text
        add(lblinfo); // eto naman yung pag add ng info text

        JButton btn = new JButton("BACK TO MENU"); // design ng button para makabalik sa menu
        btn.setFont(new Font("SansSerif", Font.BOLD, 16)); // font ng button para makabalik sa menu
        btn.setForeground(Color.BLACK); // design ng color ng button para makabalik sa menu
        btn.setBackground(accentColor); // design ng background color ng button para makabalik sa menu
        btn.setFocusPainted(false); // design ng focus ng button para makabalik sa menu
        btn.setBounds(583, 550, 200, 40); // design ng position ng button para makabalik sa menu
        btn.addActionListener(e -> {
            util.InputHandler.submitInput(""); // para ma signal na exit sa panel
        });
        add(btn); // eto naman yung pag add ng button
    }
}
