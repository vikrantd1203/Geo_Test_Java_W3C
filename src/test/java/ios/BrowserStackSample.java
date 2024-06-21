package ios;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserStackSample {

  public static void main(String[] args)
      throws MalformedURLException, InterruptedException {
    DesiredCapabilities caps = new DesiredCapabilities();
    HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();

    // Set your access credentials
    browserstackOptions.put("userName", "your_username");
    browserstackOptions.put("accessKey", "your_access_key");

    // Set other BrowserStack capabilities
    browserstackOptions.put("appiumVersion", "1.22.0");
    browserstackOptions.put("projectName", "Java Project");
    browserstackOptions.put("buildName", "GeoTest-Set-LocationTwice");
    browserstackOptions.put("sessionName", "SetLocation_Twice");
    //Initial Location
    browserstackOptions.put("gpsLocation", "21.146244672007352,79.08931972231493");

    // Passing browserstack capabilities inside bstack:options
    caps.setCapability("bstack:options", browserstackOptions);

    // Set URL of the application under test
    caps.setCapability("app", "your_app_url");

    // Specify device and os_version for testing
    caps.setCapability("deviceName", "iPhone 14");
    caps.setCapability("platformName", "ios");
    caps.setCapability("platformVersion", "16");

    // Initialise the remote Webdriver using BrowserStack remote URL
    // and desired capabilities defined above
    IOSDriver driver = new IOSDriver(
        new URL("http://hub.browserstack.com/wd/hub"),
        caps);

    //Set First Location
    driver.setLocation(new Location(15.386823879042266, 73.81535589458176, 0));
    
    Thread.sleep(5000);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    WebElement allowButton = wait.until(
        ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Allow While Using App")));

    //Again set Location
    driver.setLocation(new Location(49.00938, -122.75306, 0));

    Thread.sleep(5000);

    // Click on the "Allow While Using App" button
    allowButton.click();

    WebElement demoMapElement = wait
        .until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//XCUIElementTypeOther[@name='Demo Map']")));
    demoMapElement.click();

    Thread.sleep(10000);

    // Locate the element using the provided XPath
    WebElement demoMapTextElement = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.xpath("//XCUIElementTypeOther[@name='Demo Map']/XCUIElementTypeOther/XCUIElementTypeStaticText")));

    Thread.sleep(5000);
    
    // Get the text from the located element
    List<WebElement> staticTextElements = demoMapElement.findElements(
        AppiumBy.xpath("./XCUIElementTypeOther/XCUIElementTypeStaticText"));

    for (WebElement element : staticTextElements) {
      String text = element.getText();
      System.out.println("Element text: " + text);
    }
    driver.quit();
  }
}
