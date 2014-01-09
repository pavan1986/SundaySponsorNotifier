

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * This class retrieves the Google Authorization code using
 * {@link HtmlUnitDriver}.
 * 
 * 
 * @author vamshi
 * 
 */
public class AuthCodeRetriever {

	public static void main(String[] args) throws InterruptedException {
		AuthCodeRetriever
				.retrieveAuthCode("https://accounts.google.com/o/oauth2/auth?access_type=online&approval_prompt=auto&client_id=437177876571.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/drive");
	}

	public static String retrieveAuthCode(String url)
			throws InterruptedException {
		String authCode = null;
		//DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		driver.findElement(By.id("Email")).sendKeys("recorder@ihf-usa.org");
		driver.findElement(By.id("Passwd")).sendKeys("recorder@1235");
		driver.setJavascriptEnabled(true);
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(7000);
		WebElement element = driver.findElement(By.id("submit_approve_access"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		authCode = driver.findElement(By.id("code")).getAttribute("value");
		System.out.println("Auth Code\t:" + authCode);
		driver.close();
		return authCode;

	}
}
