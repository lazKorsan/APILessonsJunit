package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LuxuryPopUpManager {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color LIGHT_TEXT = new Color(236, 240, 241);

    public static void showLuxuryPopup(String title, String message, PopUpType type) {
        SwingUtilities.invokeLater(() -> createLuxuryWindow(title, message, type));
    }

    private static void createLuxuryWindow(String title, String message, PopUpType type) {
        // Ana frame
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setAlwaysOnTop(true);

        // Yuvarlak köşeler için
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 300, 30, 30));

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DARK_BG);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(20, 25, 20, 25)
        ));

        // Başlık barı
        JPanel titlePanel = createTitlePanel(title, type, frame);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // İçerik alanı
        JPanel contentPanel = createContentPanel(message);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = createButtonPanel(frame);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Ekran merkezinde
        frame.setVisible(true);

        // Otomatik kapanma timer'ı
        Timer timer = new Timer(5000, e -> frame.dispose());
        timer.setRepeats(false);
        timer.start();
    }

    private static JPanel createTitlePanel(String title, PopUpType type, JFrame frame) {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getColorByType(type));
        titlePanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // İkon
        JLabel iconLabel = new JLabel(getIconByType(type));
        iconLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Başlık
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Kapat butonu
        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(0, 0, 0, 0));
        closeBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.addActionListener(e -> frame.dispose());

        titlePanel.add(iconLabel, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(closeBtn, BorderLayout.EAST);

        return titlePanel;
    }

    private static JPanel createContentPanel(String message) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK_BG);
        contentPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // Büyük yazı
        messageArea.setForeground(LIGHT_TEXT);
        messageArea.setBackground(DARK_BG);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(DARK_BG);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        return contentPanel;
    }

    private static JPanel createButtonPanel(JFrame frame) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(DARK_BG);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton okButton = new JButton("TAMAM");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(PRIMARY_COLOR);
        okButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> frame.dispose());

        // Hover efekti
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                okButton.setBackground(PRIMARY_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                okButton.setBackground(PRIMARY_COLOR);
            }
        });

        buttonPanel.add(okButton);
        return buttonPanel;
    }

    private static Color getColorByType(PopUpType type) {
        switch (type) {
            case SUCCESS: return SUCCESS_COLOR;
            case ERROR: return ERROR_COLOR;
            case WARNING: return WARNING_COLOR;
            case INFO: return PRIMARY_COLOR;
            default: return PRIMARY_COLOR;
        }
    }

    private static String getIconByType(PopUpType type) {
        switch (type) {
            case SUCCESS: return "✅ ";
            case ERROR: return "❌ ";
            case WARNING: return "⚠️ ";
            case INFO: return "ℹ️ ";
            default: return "💎 ";
        }
    }

    public static void showTestStart(String bookingApiTesti) {
    }

    public static void showTestStep(String number, String s) {
    }

    public static void showResponse(int statusCode, String string) {
    }

    public static void showAssertion(String bookingid, String number, String bookingid1, boolean b) {
    }

    public static void showTestComplete(boolean b, int i) {
    }

    public static void showApiCall(String post, String s, String string) {
    }

    // Gölge border sınıfı
    static class ShadowBorder implements javax.swing.border.Border {
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gölge efekti
            for (int i = 0; i < 5; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - i * 4));
                g2.drawRoundRect(x + i, y + i, width - 1 - i * 2, height - 1 - i * 2, 30, 30);
            }
            g2.dispose();
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }

        public boolean isBorderOpaque() {
            return false;
        }
    }

    public enum PopUpType {
        SUCCESS, ERROR, WARNING, INFO
    }
}