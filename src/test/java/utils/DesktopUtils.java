package utils;

import javax.swing.*;
import java.awt.*;

/**
 * Selenium WebDriver olmadan, doğrudan işletim sistemi üzerinde modern ve görsel
 * bildirim pencereleri (pop-up) göstermek için yardımcı metotlar içerir.
 */
public class DesktopUtils {

    private static void showAutoClosingDialog(String title, String htmlContent, int duration) {
        new Thread(() -> {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(45, 52, 54));
            mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 184, 148), 2));

            JLabel messageLabel = new JLabel(htmlContent);
            messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            mainPanel.add(messageLabel, BorderLayout.CENTER);

            JOptionPane optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

            JDialog dialog = optionPane.createDialog(title);

            Timer timer = new Timer(duration, e -> dialog.dispose());
            timer.setRepeats(false);
            timer.start();

            dialog.setUndecorated(true);
            dialog.setBackground(new Color(0,0,0,0));
            dialog.setAlwaysOnTop(true);

            // === DÜZELTME ===
            // pack() metodu, özel HTML içeriğiyle boyutu her zaman doğru hesaplayamayabilir.
            // Bu nedenle, pencereye sabit ve görünür bir boyut vererek görünür olmasını garantiliyoruz.
            dialog.setSize(450, 180);

            dialog.setLocationRelativeTo(null); // Ekranda ortala.
            dialog.setVisible(true);
        }).start();
    }

    /**
     * 🎯 Test başlangıcı için tasarlanmış pop-up.
     */
    public static void showStartPopup(String testName, String testDescription) {
        String html = "<html><body style='width: 400px; font-family: Arial, sans-serif; color: white;'>"
                + "<div style='font-size: 26px; color: #74b9ff; margin-bottom: 15px;'><b>🚀 TEST BAŞLIYOR</b></div>"
                + "<hr style='border-color: #636e72;'>"
                + "<p style='font-size: 15px;'><b>Test:</b> " + testName + "</p>"
                + "<p style='font-size: 15px;'><b>Açıklama:</b> " + testDescription + "</p>"
                + "</body></html>";
        showAutoClosingDialog("Test Başlangıcı", html, 4000);
    }

    /**
     * 🎯 Testin ilerleme adımlarını göstermek için tasarlanmış pop-up.
     */
    public static void showProgressPopup(String stepName, String stepDescription) {
        String html = "<html><body style='width: 400px; font-family: Arial, sans-serif; color: white;'>"
                + "<div style='font-size: 26px; color: #fdcb6e; margin-bottom: 15px;'><b>⏳ İŞLEMDE...</b></div>"
                + "<hr style='border-color: #636e72;'>"
                + "<p style='font-size: 15px;'><b>Adım:</b> " + stepName + "</p>"
                + "<p style='font-size: 15px;'><b>Açıklama:</b> " + stepDescription + "</p>"
                + "</body></html>";
        showAutoClosingDialog("Test İlerlemesi", html, 3000);
    }

    /**
     * 🎯 Test başarıyla tamamlandığında gösterilecek pop-up.
     */
    public static void showSuccessPopup(String testName) {
        String html = "<html><body style='width: 400px; font-family: Arial, sans-serif; color: white;'>"
                + "<div style='font-size: 26px; color: #55efc4; margin-bottom: 15px;'><b>✅ BAŞARILI</b></div>"
                + "<hr style='border-color: #636e72;'>"
                + "<p style='font-size: 15px;'><b>Test:</b> " + testName + "</p>"
                + "<p style='font-size: 15px;'><b>Durum:</b> Test başarıyla tamamlandı.</p>"
                + "</body></html>";
        showAutoClosingDialog("Test Sonucu", html, 5000);
    }
}