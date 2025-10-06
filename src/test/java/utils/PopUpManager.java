package utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopUpManager {

    private static List<String> testSteps = new ArrayList<>();
    private static int currentStep = 0;

    public static void initializeTest(String testName) {
        testSteps.clear();
        currentStep = 0;

        // Test başlangıç pop-up'ı
        showCenteredPopup("🚀 TEST BAŞLANGICI",
                testName,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showTestStep(String stepNumber, String description) {
        testSteps.add("ADIM " + stepNumber + ": " + description);
        currentStep++;

        showCenteredPopup("📋 TEST ADIMI " + stepNumber,
                description,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showApiCall(String method, String url, String body) {
        String message = "Method: " + method + "\n" +
                "URL: " + url + "\n" +
                (body != null ? "Body: " + body : "");

        showCenteredPopup("📡 API ÇAĞRISI", message, JOptionPane.QUESTION_MESSAGE);
    }

    public static void showAssertion(String field, String expected, String actual, boolean isSuccess) {
        String icon = isSuccess ? "✅" : "❌";
        String title = isSuccess ? "BAŞARILI DOĞRULAMA" : "DOĞRULAMA HATASI";

        String message = "Alan: " + field + "\n" +
                "Beklenen: " + expected + "\n" +
                "Gerçek: " + actual;

        showCenteredPopup(icon + " " + title, message,
                isSuccess ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    public static void showResponse(String responseBody, int statusCode) {
        String message = "Status Code: " + statusCode + "\n" +
                "Response: " + (responseBody.length() > 100 ?
                responseBody.substring(0, 100) + "..." : responseBody);

        showCenteredPopup("📨 API YANITI", message, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showTestComplete(boolean success) {
        String title = success ? "🎉 TEST BAŞARILI" : "💥 TEST BAŞARISIZ";
        String message = "Toplam Adım: " + testSteps.size() + "\n\n";

        for (int i = 0; i < testSteps.size(); i++) {
            message += (i + 1) + ". " + testSteps.get(i) + "\n";
        }

        showCenteredPopup(title, message,
                success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String errorMessage) {
        showCenteredPopup("❌ HATA", errorMessage, JOptionPane.ERROR_MESSAGE);
    }

    private static void showCenteredPopup(String title, String message, int messageType) {
        // GUI işlemleri için Event Dispatch Thread kullan
        if (SwingUtilities.isEventDispatchThread()) {
            createAndShowPopup(title, message, messageType);
        } else {
            SwingUtilities.invokeLater(() -> {
                createAndShowPopup(title, message, messageType);
            });
        }
    }

    private static void createAndShowPopup(String title, String message, int messageType) {
        // Pencereyi oluştur
        JOptionPane pane = new JOptionPane(message, messageType);
        JDialog dialog = pane.createDialog(null, title);

        // Pencereyi merkezde göster
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(false); // Modal olmasın, test devam etsin

        // Pencere boyutunu ayarla
        dialog.setSize(400, 300);
        dialog.setResizable(true);

        // Otomatik kapanma süresi (ms)
        Timer timer = new Timer(3000, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }

    // Manuel kapatma için alternatif metod
    public static void showPopupWithButton(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}