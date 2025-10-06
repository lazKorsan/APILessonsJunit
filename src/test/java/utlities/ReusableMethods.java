package utlities;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReusableMethods {

    /**
     * Test sırasında belirli bir süre beklemek için kullanılır.
     * NOT: Bu, 'explicit wait' yerine geçmez. Sadece geçici veya demo amaçlı kullanılmalıdır.
     *
     * @param saniye Beklenecek süre (saniye cinsinden).
     */
    public static void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000L);
        } catch (InterruptedException e) {
            // InterruptedException durumunda, thread'in kesintiye uğradığını belirtmek iyi bir pratiktir.
            Thread.currentThread().interrupt();
            System.err.println("Thread bekleme sırasında kesintiye uğradı: " + e.getMessage());
        }
    }

    /**
     * Verilen bir WebElement listesindeki her bir elementin metnini alarak
     * bu metinlerden oluşan bir String listesi döndürür.
     *
     * @param webElementList Metinleri alınacak WebElement'leri içeren liste.
     * @return Elementlerin metinlerini içeren yeni bir String listesi.
     */
    public static List<String> getElementsText(List<WebElement> webElementList) {
        return webElementList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Belirtilen WebElement'in ekran görüntüsünü alır ve Masaüstündeki 'testNG' klasörüne kaydeder.
     * Dosya adı, zaman damgası ile eşsiz hale getirilir.
     *
     * @param element  Ekran görüntüsü alınacak WebElement.
     * @param fileName Kaydedilecek dosyanın temel adı.
     */
    public static void getWebElementScreenshot(WebElement element, String fileName) {
        try {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = fileName + "_" + timeStamp + ".png";
            // Not: Bu yol, sadece sizin bilgisayarınızda çalışacaktır.
            String directoryPath = "C:/Users/Hp/OneDrive/Desktop/loyalfriendcare" ;
            Path fullPath = Paths.get(directoryPath, finalFileName);

            Files.createDirectories(Paths.get(directoryPath));
            File sourceFile = element.getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), fullPath);
            System.out.println("Element ekran görüntüsü başarıyla kaydedildi: " + fullPath);
        } catch (IOException e) {
            System.err.println("Element ekran görüntüsü alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tüm sayfanın ekran görüntüsünü alır ve Masaüstündeki 'testNG' klasörüne kaydeder.
     * Dosya adı, zaman damgası ile eşsiz hale getirilir.
     *
     * @param fileName Kaydedilecek ekran görüntüsü için verilecek temel isim.
     */
    public static void takeFullPageScreenshot(String fileName) {
        try {
            TakesScreenshot tss = (TakesScreenshot) Driver.getDriver();
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = fileName + "_" + timeStamp + ".png";
            // Not: Bu yol, sadece sizin bilgisayarınızda çalışacaktır.
            String directoryPath = "C:/Users/Hp/OneDrive/Desktop/loyalfriendcare";
            Path fullPath = Paths.get(directoryPath, finalFileName);

            Files.createDirectories(Paths.get(directoryPath));
            File sourceFile = tss.getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), fullPath);
            System.out.println("Tam sayfa ekran görüntüsü başarıyla kaydedildi: " + fullPath);
        } catch (IOException e) {
            System.err.println("Tam sayfa ekran görüntüsü alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Hata durumunda ExtentReports'a eklemek için ekran görüntüsü alır.
     * Görüntüyü projenin altındaki 'test-output/Screenshots' klasörüne kaydeder
     * ve raporlama için dosya yolunu String olarak döndürür.
     *
     * @param testName Raporlanacak testin adı, dosya adında kullanılacak.
     * @return Kaydedilen ekran görüntüsünün dosya yolu.
     */
    public static String addScreenshotToReport(String testName) {
        try {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timeStamp + ".png";
            String directoryPath = System.getProperty("user.dir") + "/test-output/Screenshots/";
            Path fullPath = Paths.get(directoryPath, fileName);

            Files.createDirectories(Paths.get(directoryPath));

            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);

            Files.copy(source.toPath(), fullPath);
            // Rapora eklemek için göreceli yolu döndürmek daha taşınabilir olabilir.
            return "Screenshots/" + fileName;
        } catch (IOException e) {
            System.err.println("Rapora ekran görüntüsü eklenirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Açık olan pencereler arasında, hedef URL'e sahip olan pencereye geçiş yapar.
     *
     * @param hedefUrl Geçiş yapılmak istenen pencerenin tam URL'i.
     */
    public static void switchWindowByUrl(String hedefUrl) {
        Set<String> allWindowHandles = Driver.getDriver().getWindowHandles();
        for (String eachHandle : allWindowHandles) {
            Driver.getDriver().switchTo().window(eachHandle);
            if (Driver.getDriver().getCurrentUrl().equals(hedefUrl)) {
                break;
            }
        }
    }

    /**
     * Açık olan pencereler arasında, hedef başlığa (title) sahip olan pencereye geçiş yapar.
     *
     * @param hedefTitle Geçiş yapılmak istenen pencerenin tam başlığı.
     */
    public static void switchWindowByTitle(String hedefTitle) {
        Set<String> allWindowHandles = Driver.getDriver().getWindowHandles();
        for (String eachHandle : allWindowHandles) {
            Driver.getDriver().switchTo().window(eachHandle);
            if (Driver.getDriver().getTitle().equals(hedefTitle)) {
                break;
            }
        }
    }


    public class BulXpath {


        public static void printXpathFormulas(WebElement element) {
            System.out.println("Metin (getText()): " + element.getText());
            System.out.println("Tag adı: " + element.getTagName());
            System.out.println("ID attribute: " + element.getAttribute("id"));
            System.out.println("Class attribute: " + element.getAttribute("class"));
            System.out.println("Placeholder attribute: " + element.getAttribute("placeholder"));

            // XPath formülleri
            String tag = element.getTagName();
            String id = element.getAttribute("id");
            String classAttr = element.getAttribute("class");
            String placeholder = element.getAttribute("placeholder");
            String name = element.getAttribute("name");

            if (id != null && !id.isEmpty()) {
                System.out.println("//" + tag + "[@id='" + id + "']");
                System.out.println("//*[@id='" + id + "']");
                System.out.println("//*[starts-with(@id,'" + id.substring(0, Math.min(3, id.length())) + "')]");
                System.out.println("//*[contains(@id,'" + id + "')]");
            }
            if (classAttr != null && !classAttr.isEmpty()) {
                System.out.println("//" + tag + "[@class='" + classAttr + "']");
            }
            if (placeholder != null && !placeholder.isEmpty()) {
                System.out.println("//" + tag + "[@placeholder='" + placeholder + "']");
            }
            if (name != null && !name.isEmpty()) {
                System.out.println("//" + tag + "[@name='" + name + "']");
            }
            // Kombinasyonlar
            if (id != null && !id.isEmpty() && classAttr != null && !classAttr.isEmpty()) {
                System.out.println("//" + tag + "[@id='" + id + "' and @class='" + classAttr + "']");
            }
            if (id != null && !id.isEmpty() && name != null && !name.isEmpty()) {
                System.out.println("//" + tag + "[@id='" + id + "' and @name='" + name + "']");
            }
        }
    }

    // 1. Temel Scroll Metodu
    public static void scrollToElement(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
        //<===============cagrılması ===========================>
        //ReusableMethods.scrollToElement(practiceexpandtestingPage.oneTimePasswordButtons);
        //<=====================================================>

    }

    // 2. Scroll + Beklemeli Versiyon
    public static void scrollToElementWithWait(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(element));
        scrollToElement(element);
        //<-- scrollToElementWithWait Methodunun Cagırılması -->
        //ReusableMethods.scrollToElementWithWait(practiceexpandtestingPage.oneTimePasswordButtons,300);
        // <-- =================================== -->
    }

    // 3. Sayfa Sonuna Scroll
    public static void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // <-- scrollBottom methodunun Cagırılması -->
        //ReusableMethods.scrollToBottom();
        // <-- =================================== -->
    }
    public static void printProductsInCategory(WebDriver driver, String categoryUrl, String productLocator) {
        driver.get(categoryUrl);
        List<WebElement> products = driver.findElements(By.xpath(productLocator));

        System.out.println("Ürün sayısı: " + products.size());
        System.out.println("Ürünler:");
        for (WebElement urun : products) {
            System.out.println(urun.getText());
        }
    }
    public static void selectByVisibleText(WebElement element, String text) {
        Select objSelect = new Select(element);
        objSelect.selectByVisibleText(text);
    }


    public static void selectByIndex(WebElement element, int index) {
        Select objSelect = new Select(element);
        objSelect.selectByIndex(index);
    }


    public static void selectByValue(WebElement element, String value) {
        Select objSelect = new Select(element);
        List<WebElement> elementCount = objSelect.getOptions();
        objSelect.selectByValue(value);
        System.out.println("number of elements: " + elementCount.size());
    }
    public static void waitAndClickLocationText(WebElement element, String value) {
        Driver.getDriver().findElement(By.xpath("//*[text()='" + value + "']")).click();
    }




        /**
         * BELİRTİLEN TABLO İÇİNDE BİR METNİ ARAR VE SONUCU RAPORLAYARAK DOĞRULAR.
         * @param driver WebDriver nesnesi
         * @param targetUrl Kontrol edilecek sayfanın URL'si (opsiyonel - null geçilebilir)
         * @param tableRowsLocator Tablodaki TÜM SATIRLARI veren XPath (//table//tr gibi)
         * @param searchText Aranacak metin
         * @param columnIndex (Opsiyonel) Aramayı belirli bir sütunda sınırlamak için.
         *                    Verilmezse (-1) tüm satırın metninde arar.
         * @return boolean - Metin bulunursa true, bulunamazsa Assertion hatası fırlatır.
         */
        public static boolean verifyTextInTable(WebDriver driver,
                                                String targetUrl,
                                                String tableRowsLocator,
                                                String searchText,
                                                int... columnIndex) {

            // 1. Eğer URL verilmişse, o sayfaya git
            if (targetUrl != null && !targetUrl.isEmpty()) {
                driver.get(targetUrl);
                System.out.println("Sayfaya gidildi: " + targetUrl);
            }

            // 2. Bekleme mekanizması - Tablonun yüklenmesini bekle
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableRowsLocator)));
            } catch (TimeoutException e) {
                Assert.fail("TABLO BULUNAMADI! Verilen locator: " + tableRowsLocator);
            }

            // 3. Tüm satırları bul
            List<WebElement> allRows = driver.findElements(By.xpath(tableRowsLocator));
            System.out.println("Toplam satır sayısı: " + allRows.size());

            // 4. Hangi sütunda arama yapılacağını belirle (Varargs handling)
            int searchColumn = columnIndex.length > 0 ? columnIndex[0] : -1;

            // 5. Satırları döngü ile ara
            boolean isFound = false;
            int foundAtRow = -1;

            for (int i = 0; i < allRows.size(); i++) {
                WebElement currentRow = allRows.get(i);
                String rowTextToCheck;

                if (searchColumn == -1) {
                    // Tüm satırda ara
                    rowTextToCheck = currentRow.getText();
                } else {
                    // Belirli bir sütunda ara
                    try {
                        // Satırdaki hücreleri al (td veya th)
                        List<WebElement> cells = currentRow.findElements(By.xpath(".//td | .//th"));
                        if (cells.size() > searchColumn) {
                            rowTextToCheck = cells.get(searchColumn).getText();
                        } else {
                            // Eğer o indekste hücre yoksa, boş string ile devam et
                            rowTextToCheck = "";
                        }
                    } catch (StaleElementReferenceException e) {
                        // Element eskimis olabilir, yeniden bul
                        allRows = driver.findElements(By.xpath(tableRowsLocator));
                        currentRow = allRows.get(i);
                        rowTextToCheck = currentRow.getText();
                    }
                }

                // Metni kontrol et
                if (rowTextToCheck.contains(searchText)) {
                    isFound = true;
                    foundAtRow = i + 1; // Kullanıcı için 1-based index
                    System.out.println("✅ BULUNDU! '" + searchText + "' metni " + foundAtRow + ". satırda yer alıyor.");
                    System.out.println("   Satır içeriği: " + rowTextToCheck.trim());
                    break;
                }
            }

            // 6. Raporlama ve Assertion
            if (isFound) {
                System.out.println("✅ DOĞRULAMA BAŞARILI: '" + searchText + "' tabloda bulundu.");
                return true;
            } else {
                // Detaylı hata mesajı
                String errorMessage = "❌ DOĞRULAMA BAŞARISIZ: '" + searchText + "' metni tabloda bulunamadı!\n" +
                        "   Arama yapılan locator: " + tableRowsLocator + "\n" +
                        "   Toplam tarama yapılan satır: " + allRows.size();
                System.out.println(errorMessage);

                // İstersen tüm tabloyu yazdırabilirsin (debug için)
                printTableContents(driver, tableRowsLocator);

                Assert.fail(errorMessage);
                return false; // Assert.fail zaten exception atar, buraya gelmez
            }
        }

        /**
         * TABLO İÇERIĞINI YAZDIRIR (DEBUG İÇİN)
         */
        private static void printTableContents(WebDriver driver, String tableRowsLocator) {
            System.out.println("--- TABLO İÇERİĞİ (DEBUG) ---");
            List<WebElement> rows = driver.findElements(By.xpath(tableRowsLocator));
            for (int i = 0; i < rows.size(); i++) {
                System.out.println((i + 1) + ". satır: " + rows.get(i).getText());
            }
            System.out.println("-----------------------------");
        }
        //printProductsInCategory

    }


