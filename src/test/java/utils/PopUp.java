package utils;

public class PopUp {

    public static void showTestStep(String stepNumber, String description) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ 🚀 TEST ADIMI " + stepNumber + "                                                      │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ " + padRight(description, 55) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public static void showApiCall(String method, String url) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ 📡 API ÇAĞRISI                                         │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ Method: " + padRight(method, 46) + " │");
        System.out.println("│ URL: " + padRight(url, 49) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public static void showAssertion(String field, String expected, String actual) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ ✅ DOĞRULAMA                                            │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ Alan: " + padRight(field, 48) + " │");
        System.out.println("│ Beklenen: " + padRight(expected, 45) + " │");
        System.out.println("│ Gerçek: " + padRight(actual, 47) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public static void showError(String errorMessage) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ ❌ HATA                                                 │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ " + padRight(errorMessage, 55) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public static void showSuccess(String message) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ 🎉 BAŞARILI                                            │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ " + padRight(message, 55) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public static void showDataComparison(String title, String sent, String expected, String received) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ 📊 " + padRight(title, 52) + " │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ Gönderilen: " + padRight(sent, 42) + " │");
        System.out.println("│ Beklenen: " + padRight(expected, 45) + " │");
        System.out.println("│ Alınan: " + padRight(received, 46) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public static void showTestStart(String testName) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                    TEST BAŞLANGICI                     ║");
        System.out.println("║ " + padRight(testName, 55) + " ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public static void showTestEnd(String testName, boolean passed) {
        String status = passed ? "✅ TEST BAŞARILI" : "❌ TEST BAŞARISIZ";
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║ " + padRight(status, 55) + " ║");
        System.out.println("║ " + padRight(testName, 55) + " ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    private static String padRight(String s, int n) {
        if (s == null) s = "null";
        return String.format("%-" + n + "s", s.length() > n ? s.substring(0, n-3) + "..." : s);
    }
}