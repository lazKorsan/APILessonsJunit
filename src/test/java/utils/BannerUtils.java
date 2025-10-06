package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Testler sırasında hem konsolda hem de tarayıcıda görsel banner'lar göstermek için yardımcı metotlar içerir.
 */
public class BannerUtils {

    public static void displayDynamicBanner(WebDriver driver, String bannerType,
                                            String mainTitle, String testName,
                                            String testDescription, int duration) {
        displayConsoleBanner(bannerType, mainTitle, testName, testDescription);
        // Sadece bir WebDriver nesnesi varsa (null değilse) tarayıcıda banner göster
        if (driver != null) {
            displayBrowserBanner(driver, bannerType, mainTitle, testName, testDescription, duration);
        }
    }

    /**
     * 🎯 Test başlangıcı için hızlı kullanım metodu.
     */
    public static void displayStartBanner(WebDriver driver, String testName, String testDescription) {
        displayDynamicBanner(driver, "start", "TEST BAŞLIYOR", testName, testDescription, 5000);
    }

    /**
     * 🎯 Testin ilerleme adımlarını göstermek için banner.
     */
    public static void displayProgressBanner(WebDriver driver, String progressTitle, String testName, String stepDescription) {
        displayDynamicBanner(driver, "progress", progressTitle, testName, stepDescription, 3000);
    }

    /**
     * 🎯 Test başarıyla tamamlandığında gösterilecek banner.
     */
    public static void displaySuccessBanner(WebDriver driver, String successTitle, String testName) {
        displayDynamicBanner(driver, "success", successTitle, testName, "Test başarıyla tamamlandı.", 4000);
    }

    /**
     * 🎯 Konsola renkli ve ikonlu bir banner yazdırır.
     */
    private static void displayConsoleBanner(String bannerType, String mainTitle, String testName, String testDescription) {
        String icon = getBannerIcon(bannerType);
        String colorCode = getBannerColorCode(bannerType);

        String banner =
                "\n" + colorCode +
                        "==================================================\n" +
                        "               " + icon + " " + mainTitle + " \n" +
                        "==================================================\n" +
                        "⏰ Zaman: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "\n" +
                        "🎯 Test: " + testName + "\n" +
                        "📝 Durum: " + testDescription + "\n" +
                        // "🌐 URL: " + ConfigReader.getProperty("lfc") + "\n" + // TODO: Kendi ConfigReader sınıfınızı oluşturduktan sonra bu satırı aktif edebilirsiniz.
                        "==================================================\n" +
                        "\u001B[0m"; // Rengi sıfırla

        System.out.println(banner);
    }

    /**
     * 🎯 Tarayıcı ekranında JavaScript kullanarak dinamik bir HTML banner gösterir.
     */
    private static void displayBrowserBanner(WebDriver driver, String bannerType,
                                             String mainTitle, String testName,
                                             String testDescription, int duration) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            String[] colors = getBannerColors(bannerType);
            String icon = getBannerIcon(bannerType);

            String script = "var oldBanner=document.getElementById('dynamicTestBanner');if(oldBanner){oldBanner.remove();}var banner=document.createElement('div');banner.id='dynamicTestBanner';banner.style.cssText='position:fixed;left:50%;top:50%;transform:translate(-50%,-50%);background:linear-gradient(135deg, '+arguments[0]+');color:white;padding:20px;border-radius:15px;z-index:9999;box-shadow:0 10px 30px rgba(0,0,0,0.3);font-family:Arial,sans-serif;text-align:center;min-width:350px;border:3px solid '+arguments[1]+';animation:fadeIn 0.5s ease-in;';banner.innerHTML='<div style=\"font-size: 28px; margin-bottom: 10px;\">'+arguments[2]+'</div><h3 style=\"margin:0 0 15px 0;font-size:20px;font-weight:bold;\">'+arguments[3]+'</h3><div style=\"font-size:14px;margin-bottom:8px;background:rgba(255,255,255,0.1);padding:5px;border-radius:5px;\"><strong>Test:</strong> '+arguments[4]+'</div><div style=\"font-size:14px;margin-bottom:8px;\"><strong>Açıklama:</strong> '+arguments[5]+'</div><div style=\"font-size:11px;opacity:0.8;margin-top:15px;border-top:1px solid rgba(255,255,255,0.2);padding-top:10px;\">'+new Date().toLocaleString()+'</div><div style=\"font-size:10px;opacity:0.6;margin-top:5px;\">'+arguments[6]+' ms sonra kapanacak...</div>';var style=document.createElement('style');style.innerHTML='@keyframes fadeIn{from{opacity:0;transform:translate(-50%,-60%)}to{opacity:1;transform:translate(-50%,-50%)}}';document.head.appendChild(style);document.body.appendChild(banner);setTimeout(function(){banner.style.animation='fadeOut 0.5s ease-out';setTimeout(function(){if(banner.parentNode){banner.parentNode.removeChild(banner)}},500)},arguments[6]);var fadeOutStyle=document.createElement('style');fadeOutStyle.innerHTML='@keyframes fadeOut{from{opacity:1;transform:translate(-50%,-50%)}to{opacity:0;transform:translate(-50%,-60%)}}';document.head.appendChild(fadeOutStyle);";

            js.executeScript(script, colors[0], colors[1], icon, mainTitle, testName, testDescription, duration);

        } catch (Exception e) {
            System.out.println("⚠️ Browser banner gösterilemedi: " + e.getMessage());
        }
    }

    private static String getBannerIcon(String bannerType) {
        switch (bannerType.toLowerCase()) {
            case "start": return "🚀";
            case "progress": return "⏳";
            case "success": return "✅";
            case "warning": return "⚠️";
            case "error": return "❌";
            default: return "🎯";
        }
    }

    private static String[] getBannerColors(String bannerType) {
        switch (bannerType.toLowerCase()) {
            case "start": return new String[]{"#667eea 0%, #764ba2 100%", "#ff6b6b"};
            case "progress": return new String[]{"#f093fb 0%, #f5576c 100%", "#ffd93d"};
            case "success": return new String[]{"#4facfe 0%, #00f2fe 100%", "#00b894"};
            case "warning": return new String[]{"#ff9a9e 0%, #fad0c4 100%", "#fdcb6e"};
            case "error": return new String[]{"#ff5858 0%, #f09819 100%", "#d63031"};
            default: return new String[]{"#667eea 0%, #764ba2 100%", "#ff6b6b"};
        }
    }

    private static String getBannerColorCode(String bannerType) {
        switch (bannerType.toLowerCase()) {
            case "start": return "\u001B[35m"; // Purple
            case "progress": return "\u001B[33m"; // Yellow
            case "success": return "\u001B[32m"; // Green
            case "warning": return "\u001B[33m"; // Yellow
            case "error": return "\u001B[31m"; // Red
            default: return "\u001B[36m"; // Cyan
        }
    }
}