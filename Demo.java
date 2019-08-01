package com.selenium.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Flash.FlashObjectWebDriver;

public class GuruDemo {
	
	public static WebDriver driver;
	
	// For Chrome Browser in DemoQA
	
	public void setUpChrome() {
		
		System.setProperty("webdriver.chrome.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				
	}
	
	// For FireFox
    public void setUpFireFox() {
		
    	System.setProperty("webdriver.gecko.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\geckodriver.exe");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so","true"); 
		profile.setPreference("plugin.state.flash", 2);
		dc.setCapability(FirefoxDriver.PROFILE, profile);
		driver = new FirefoxDriver(dc);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		
		
	}
    
    
	
	//Tooltip & Double Click Implementation	
	public  void tTipDoubleClick() {
			
			driver.get("https://demoqa.com/tooltip-and-double-click/");
			Actions act= new Actions(driver);
			//double click on button
			act.doubleClick(driver.findElement(By.id("doubleClickBtn"))).build().perform();
			//Click OK on Alert
			driver.switchTo().alert().accept();
			//Right click to see context menu
			act.contextClick(driver.findElement(By.id("rightClickBtn"))).build().perform();
			// Tool Tip example
			act.moveToElement(driver.findElement(By.id("tooltipDemo"))).build().perform();
		}

		
		
		
		//Resizable Interaction Implementation
		
		public  void resizableDemo() {
			
			driver.get("https://demoqa.com/resizable/");
			Actions act= new Actions(driver);
			act.clickAndHold(driver.findElement(By.xpath("//div[@id='resizable']/div[3]"))).moveByOffset(40, 80).release().build().perform();
		}

		
		//DatePicker Widget Implementation
		
		
	 public void datePicker() {
			
			driver.get("https://demoqa.com/datepicker/");
			driver.findElement(By.xpath(".//*[@id='datepicker']")).click();
			WebDriverWait wait=new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='ui-datepicker-div']"))));
			List<WebElement> dates = driver.findElements(By.className("ui-state-default"));
			int lCount=driver.findElements(By.className("ui-state-default")).size();
			for(WebElement date : dates) {
				
				String dText=date.getText();
				if(dText.equalsIgnoreCase("16")) {
					date.click();
					break;
					
				}
			}
			
		}
	
	// Flash Movie Demo in Guru99 Implementation
	
	public  void flashDemo() {
		
		FlashObjectWebDriver flashDemo = new FlashObjectWebDriver(driver,"myFlashMovie");
		driver.get("http://demo.guru99.com/test/flash-testing.html");
		try {
			Thread.sleep(5000);
			flashDemo.callFlashObject("Play");
			Thread.sleep(2000);	
			flashDemo.callFlashObject("StopPlay");
			Thread.sleep(2000);	
			flashDemo.callFlashObject("Zoom", "70");
			Thread.sleep(2000);	
			flashDemo.callFlashObject("Zoom", "140");
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}		
		
	}
	
	// Ajax Demo in Guru99 Implementation
	
	public  void ajaxDemo() {
		driver.get("http://demo.guru99.com/test/ajax.html");
		
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		//Click yes on radio button
		driver.findElement(By.id("yes")).click();
		// click the check button
		WebElement checkButton=driver.findElement(By.id("buttoncheck"));
		checkButton.click();
		WebElement rButtonText=driver.findElement(By.xpath("//p[@class='radiobutton']"));
		String radioText=rButtonText.getText();
		System.out.println(radioText);
		
		//Reset the value
		
		driver.findElement(By.cssSelector("input[value='Reset']")).click();
		//Click No on radio button
		driver.findElement(By.id("no")).click();
		checkButton.click();
		radioText=rButtonText.getText();
		System.out.println(radioText);
				
	}
	
	//Tool Tip Demo in Guru99 Implementation
	
	public  void toolTipDemo() {
		
		driver.get("http://demo.guru99.com/test/tooltip.html");
		//driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		Actions act= new Actions(driver);
		act.moveToElement(driver.findElement(By.id("download_now"))).build().perform();			
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		GuruDemo dq = new GuruDemo();
		
		// Run scripts in Chrome Browser
		dq.setUpChrome();
		dq.flashDemo();
		dq.ajaxDemo();
		dq.toolTipDemo();
		dq.tTipDoubleClick();
		dq.resizableDemo();
		dq.datePicker();
		
		//driver.close();	
		
		// Run scripts in FireFox Browser
		dq.setUpFireFox();
		dq.flashDemo();
		dq.ajaxDemo();
		dq.toolTipDemo();
		dq.resizableDemo();
		dq.tTipDoubleClick();
		dq.datePicker();
		//driver.close();

	}

}
