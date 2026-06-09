package ui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

// eto naman yung main frame ng game para sa card layout at pag lipat ng panel 
public class MainFrame {

    private static MainFrame instance;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    // dito start ang main frame ng game para sa card layout at pag lipat lipat ng panel/scene
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame window = new MainFrame();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public MainFrame() {
        initialize();
        instance = this;
    }
    // eto naman yung pag initialize para sa card layout at pag lipat ng panel
    /**
     * @wbp.parser.entryPoint
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("A E T H E R M O O R");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new java.awt.Color(10, 12, 15));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setPreferredSize(new java.awt.Dimension(1366, 768));
        mainPanel.setMinimumSize(new java.awt.Dimension(1366, 768));
        mainPanel.setMaximumSize(new java.awt.Dimension(1366, 768));

        // Wrap it in a GridBagLayout to center it on the fullscreen monitor
        JPanel wrapperPanel = new JPanel(new java.awt.GridBagLayout());
        wrapperPanel.setBackground(new java.awt.Color(10, 12, 15));
        wrapperPanel.add(mainPanel);

        frame.getContentPane().add(wrapperPanel);
    }
    // eto naman yung pag add ng panel para sa card layout
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }
    // eto naman yung pag show ng panel para sa card layout
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
    // eto naman yung pag set ng visibility ng frame para sa card layout
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
    // eto naman yung pag get ng frame para sa card layout
    public JFrame getFrame() {
        return frame;
    }
}
