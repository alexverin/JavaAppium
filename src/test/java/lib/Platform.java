package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";


    private static Platform instance;

    private Platform()
    {

    }

    public static Platform getInstance()
    {
        if (instance == null)
        {
            instance = new Platform();
        }
        return instance;
    }


    public RemoteWebDriver getDriver() throws Exception
    {
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid())
        {
            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
        }
        else if (this.isIOS())
        {
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        }
        else
        {
            throw new Exception("Can't detect type of Driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }
    public boolean isMv()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "AndroidDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "Users/alekseyverin/JAA_MY/apks/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 7");
        capabilities.setCapability("platformVersion", "12.1");
        capabilities.setCapability("app", "/Users/alekseyverin/JAA_MY/apks/Wikipedia.app");
        return capabilities;
    }

    private boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    public String getPlatformVar()

    {
        return System.getenv("PLATFORM");
    }

}
