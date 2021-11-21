package driver;

import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverClient {

    private static WebDriverClient webDriverClient;
    private static FirefoxDriver firefoxDriver;

    public static synchronized WebDriverClient getInstance() {

        if (webDriverClient == null) {
            webDriverClient = new WebDriverClient();
        }

        return webDriverClient;
    }

    public FirefoxDriver getFirefoxDriver() {
        if (firefoxDriver == null) {
            firefoxDriver = new FirefoxDriver();
            System.setProperty("webdriver.gecko.driver","geckodriver.exe");
        }
        return firefoxDriver;
    }

}
