package com.demo.selenium;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import Flash.FlashObjectWebDriver;

public class GuruDemo {

	public static WebDriver driver;

	// For Chrome Browser in DemoQA

	@SuppressWarnings("deprecation")
	public static void setUpChrome(String url) {

		System.setProperty("webdriver.chrome.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\chromedriver.exe");
		DesiredCapabilities dc = DesiredCapabilities.chrome();
		dc.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		driver = new ChromeDriver(dc);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.get(url);
	}

	// For FireFox
	@SuppressWarnings("deprecation")
	public static void setUpFireFox(String url) {

		System.setProperty("webdriver.gecko.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\geckodriver.exe");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so","true"); 
		profile.setPreference("plugin.state.flash", 2);
		dc.setCapability(FirefoxDriver.PROFILE, profile);
		driver = new FirefoxDriver(dc);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.get(url);

	}

	public static void tearDown(WebDriver driver) {
		driver.close();

	}

	/*Flash Movie Demo in Guru99 Implementation using FlashObjectWebDriver
	Include Flash.jar in project build path*/

	public static void flashDemo() throws InterruptedException {

		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='flash-testing'")).click();
		FlashObjectWebDriver flashDemo = new FlashObjectWebDriver(driver,"myFlashMovie");
		try {
			Thread.sleep(20000);
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

	public  static void ajaxDemo() {

		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='ajax.html'")).click();
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		//Click yes on radio button
		driver.findElement(By.cssSelector("#yes")).click();
		// click the check button
		WebElement checkButton=driver.findElement(By.cssSelector("#buttoncheck"));
		checkButton.click();
		WebElement rButtonText=driver.findElement(By.xpath("//p[@class='radiobutton']"));
		String radioText=rButtonText.getText();
		System.out.println(radioText);

		//Reset the value

		driver.findElement(By.cssSelector("input[value='Reset']")).click();
		//Click No on radio button
		driver.findElement(By.cssSelector("#no")).click();
		checkButton.click();
		radioText=rButtonText.getText();
		System.out.println(radioText);
	}

	//Tool Tip Demo in Guru99 Implementation

	public static void toolTipDemo() throws InterruptedException {

		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='tooltip.html'")).click();
		//driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		Actions act= new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[@class='box']/a"))).build().perform();
		Thread.sleep(500);
		List<WebElement> tTipTexts=driver.findElements(By.xpath("//div[@class='tooltip']/table/tbody/tr/td"));
		int tCount=tTipTexts.size();
		System.out.println("Tool Tip Data:");
		for(int i=0;i<tCount-1;i++) {		
			System.out.println(tTipTexts.get(i).getText());		
		}
		WebElement element=driver.findElement(By.xpath("//div[@class='tooltip']/a"));
		System.out.println(element.getText());
	}
	
	
	//Radio & Checkbox Demo in Guru99 Implementation
	
	public static void radioCheckBoxDemo() {

		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='radio.html'")).click();
		WebElement option1 = driver.findElement(By.cssSelector("input#vfb-7-1"));							
		WebElement option3 = driver.findElement(By.cssSelector("input#vfb-7-3"));							
		//Radio Button1 is selected		
		option1.click();			
		System.out.println("Radio Button Option 1 Selected");					
		//Radio Button1 is de-selected and Radio Button 3 is selected		
		option3.click();			
		System.out.println("Radio Button Option 3 Selected");					
		// Selecting CheckBox		
		WebElement Checkbox1 = driver.findElement(By.cssSelector("input#vfb-6-0"));							
		// This will Toggle the Check box 		
		Checkbox1.click();			
		// Check whether the Check box is toggled on 		
		if (Checkbox1.isSelected()) {					
			System.out.println("Checkbox 1 is Toggled On");					
		} else {			
			System.out.println("Checkbox 1 is Toggled Off");					
		}
		WebElement Checkbox2 = driver.findElement(By.cssSelector("input#vfb-6-1"));	
		for (int i=0; i<2; i++) {
			// This will Toggle the Check box 2		
			Checkbox2.click();	
			System.out.println("The Checkbox 2 Status is -  "+Checkbox2.isSelected());
		}
	}
	

	//Table Demo in Guru99 Implementation
	
	public static void tableDemo() {
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='table.html'")).click();
		List<WebElement> tDataList=driver.findElements(By.xpath("//table/tbody/tr/td")); 
		System.out.println("Table Data : ");
		for(WebElement tData : tDataList  ) {			
			String tableText = tData.getText();
			System.out.print(tableText + "\t");
		}		
	}
	
	
	// Accessing Link Implementation
	
	public static void accessLink() {
		
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='link.html'")).click();
		// Link to Google	
		WebElement link1=driver.findElement(By.cssSelector("body:nth-child(2) > a:nth-child(2)"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(link1));
		link1.click();	
		// Accessing Link to Facebook
		driver.get("http://demo.guru99.com/test/link.html");
		driver.findElement(By.xpath("//body//a[2]")).click();
		driver.navigate().back();
	}
	
	// Inside & Outside Block Implementation
	
	public static void blockLevelTags() {
		
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='block.html'")).click();
		//Click inside block level Tag
		WebElement insideBlock=driver.findElement(By.xpath("//p/a"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(insideBlock));
		insideBlock.click();
		driver.get("http://demo.guru99.com/test/block.html");
		driver.findElement(By.xpath("//span[contains(text(),'Outside a block-level tag.')]")).click();
		driver.navigate().back();
	}
	
	//Delete Customer Implementation
	
	public static void deleteCustomer() {
		
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Delete Customer Form')]")).click();
		WebElement custId=driver.findElement(By.cssSelector("input[name='cusid']"));
		//input customer ID to be deleted
		custId.sendKeys("356");
		//Reset customer ID
		driver.findElement(By.xpath("//input[@type='reset']")).click();
		//input customer ID to be deleted
		custId.sendKeys("5236");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		String alertText=driver.switchTo().alert().getText();
		System.out.println("\n Alert Text is  " + alertText);
		//Cancel on Alert
		driver.switchTo().alert().dismiss();
		// Click Ok on Alert		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();	
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();	
	}
	
	//Yahoo download file Implementation
	
	public static void yahooDemo() {
		
		driver.get("http://demo.guru99.com/test/yahoo.html");
        WebElement dButton = driver.findElement(By.xpath("//div[@id='hdr_dwnld']/a"));
        dButton.click();
        String source = dButton.getAttribute("href");
        System.out.println(source);
        String wget_command = "cmd /c C:\\Wget\\wget.exe -P F: --no-check-certificate " + source;
        try {
        Process exec = Runtime.getRuntime().exec(wget_command);
        int exitVal = exec.waitFor();
        //System.out.println("Exit value: " + exitVal);
        String currentURL =driver.getCurrentUrl();
        String pageSource =driver.getPageSource();
        String title =driver.getTitle();
        if(pageSource.contains("Not Found")||(title.equalsIgnoreCase("404 Not Found"))) {
        	driver.navigate().back();
        }
        } catch (InterruptedException | IOException ex) {
        System.out.println(ex.toString());
        }
        driver.get("http://demo.guru99.com/");
	}
	
	//File upload Implementation
	
	public static void fileUpload() {
		
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[contains(@href,'upload')]")).click();
		WebElement upload = driver.findElement(By.xpath("//input[@id='uploadfile_0']"));
	       // enter the file path onto the file-selection input field
		upload.sendKeys("F:\\testing.html");
        // check the "I accept the terms of service" check box
        driver.findElement(By.cssSelector("input#terms")).click();
        // click the "UploadFile" button
        driver.findElement(By.xpath("//button[@id='submitbutton']")).click();
        //String uploadText=driver.findElement(By.xpath("//h3[@id='res']")).getText();
        String uploadText=driver.findElement(By.cssSelector("h3#res")).getText();
        System.out.println(" Upload Status: " + uploadText);		
	}
	
	//Login Implementation
	public static void login() {
		
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='login.html']")).click();
		// Input User Name
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test user");
		// Input Password
		driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys("test pass");
		//Click Sign In
		driver.findElement(By.xpath("//p[@class='submit']/button[@id='SubmitLogin']")).click();
		String expectedUrl="http://demo.guru99.com/test/success.html";
		String actualUrl=driver.getCurrentUrl();
		if(actualUrl.equalsIgnoreCase(expectedUrl)) {
			System.out.println(" Logged In Successfully");
		}		
	}
	
	//Social Icon Implementation
	
	public static void socialIcons() throws InterruptedException {

		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='social-icon']")).click();
		// Rss Icon
		List<WebElement> socialIcons=driver.findElements(By.xpath("//div[@class='soc-ico show-round']/a"));
		Iterator<WebElement> it= socialIcons.iterator();
		Actions action= new Actions(driver);
		WebElement Icon;
		while(it.hasNext()) {
			WebElement socialIcon=it.next();
			String toolTipText=socialIcon.getAttribute("Title");
			// verifying tool tip text
			if(toolTipText.equalsIgnoreCase("Rss")){
				System.out.println(toolTipText+" Icon: Tool Tip is as Expected");		
			}
			else if(toolTipText.equalsIgnoreCase("facebook")) {
				System.out.println(toolTipText+" Icon: Tool Tip is as Expected");	
			}
			else if(toolTipText.equalsIgnoreCase("Github")) {
				System.out.println(toolTipText+" Icon: Tool Tip is as Expected");		
			}
			else if(toolTipText.equalsIgnoreCase("YouTube")) {
				System.out.println(toolTipText+" Icon: Tool Tip is as Expected");			
			}
			else if(toolTipText.equalsIgnoreCase("Google+")) {
				System.out.println(toolTipText+" Icon: Tool Tip is as Expected");
			}
			else
			{
				System.out.println(toolTipText+" Icon: Tool Tip is as Expected");
			}
		}
	
	}
	
	//Selenium AutoIT Implementation  //works with FF not in chrome (intermittent issue)
	
	/*Set Up AutoIT & edit file path in AutoIT editor, then include the updated exe file
	(Used FileUploadFirefox.exe & FileUpload.exe here)*/
	
	
	public static void autoITDemo() throws IOException, InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, 60);	
		driver.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='autoit.html']")).click();
		Actions act=new Actions(driver);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();
		driver.switchTo().frame("JotFormIFrame-72320244964454");
		WebElement nameElement=driver.findElement(By.cssSelector("input#input_3"));
		WebElement emailElement=driver.findElement(By.cssSelector("input#input_4"));
		WebElement article= driver.findElement(By.xpath("//textarea[@id='input_6']"));
		WebElement submitButton= driver.findElement(By.cssSelector("button#input_2"));
		nameElement.sendKeys(" Tester");
		nameElement.sendKeys(Keys.PAGE_DOWN);
		driver.findElement(By.cssSelector("input#input_4")).sendKeys("test.a@gmail.com");
		emailElement.sendKeys(Keys.TAB);
		WebElement uploadButton=driver.findElement(By.xpath("//input[@id='input_5']"));
		wait.until(ExpectedConditions.visibilityOf(uploadButton));
		act.click(uploadButton).perform();
		//Execute the AutoIT script 	
			if(browserName.equalsIgnoreCase("firefox")) {
				Runtime.getRuntime().exec("F:\\AutoITScripts\\FileUploadFirefox.exe");
				article.sendKeys("Selenium AutoIT Demo ");
				wait.until(ExpectedConditions.visibilityOf(submitButton));
				submitButton.sendKeys(Keys.PAGE_DOWN);
				act.click(submitButton).perform();
			}
			else {
				Process p = Runtime.getRuntime().exec("F:\\AutoITScripts\\FileUpload.exe");
				p.waitFor();      
				article.sendKeys("Selenium AutoIT Demo ");
				submitButton.click();
				//act.moveToElement(submitButton).click().build().perform();			
			}	
	}

	
	//Scroll bar Implementation using Sikuli
	
	public static void scrollBar() throws FindFailed {
		
		driver.get("http://demo.guru99.com/");
		driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='scrolling.html']")).click();
		//using Sikuli 
		Screen s = new Screen();
        Pattern scroll  = new Pattern("F:\\SikuliImages\\ScrollBar.PNG");
        s.wait(scroll, 40);
        s.click(scroll);	
        s.mouseMove(1000, 1000);	
      			
	}
	
	//Drag and Drop Implementation
	
    public static void dragAndDrop() {
		
    	driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.cssSelector("a[href*='drag_drop.html']")).click();
		//Element which needs to drag.    		
    	WebElement bankElement=driver.findElement(By.xpath("//*[@id='credit2']/a"));	
       //Element on which need to drop.		
        WebElement accountElement=driver.findElement(By.xpath("//*[@id='bank']/li"));					
        WebElement amount=driver.findElement(By.xpath("//*[@id='fourth']/a"));
        WebElement debitAmount=driver.findElement(By.xpath("//ol[@id='amt7']/li"));
       // WebElement amountCredit=driver.findElement(By.xpath("//*[@id='credit0']/a"));
        WebElement creditDrop=driver.findElement(By.xpath("//ol[@id='amt8']//li"));
        WebElement accountCredit=driver.findElement(By.xpath("//*[@id='credit1']/a")); //loan
        WebElement accountDrop=driver.findElement(By.xpath("//*[@id='loan']/li"));
       //Using Action class for drag and drop.		
        Actions act=new Actions(driver);					
		//Dragged and dropped.		
		act.dragAndDrop(bankElement, accountElement).build().perform();	
		act.dragAndDrop(amount, debitAmount).build().perform();	
		act.dragAndDrop(accountCredit, accountDrop).build().perform();	
		act.dragAndDrop(amount, creditDrop).build().perform();
	}
	
  //Selenium Date Picker Implementation
    
    public static void datePicker() {
		
    	driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Selenium DatePicker Demo')]")).click();
		WebElement birthday = driver.findElement(By.xpath("//form//input[@name='bdaytime']"));
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();		
		if(browserName.equalsIgnoreCase("firefox")) {	
			birthday.sendKeys("02-15-2015");
		    //Press tab to shift focus to time field
			birthday.sendKeys(Keys.SPACE);
			birthday.sendKeys("   00:59 AM");
			driver.findElement(By.xpath("//body//input[2]")).click();
			String text=driver.findElement(By.xpath("//body/div[2]")).getText();
			System.out.println(" Birth Day & Time : " +text);
		}
		else {		
			birthday.sendKeys("02152015");
		    //Press tab to shift focus to time field
			birthday.sendKeys(Keys.TAB);
			birthday.sendKeys("0059AM");
			driver.findElement(By.xpath("//body//input[2]")).click();
			String text=driver.findElement(By.xpath("//body/div[2]")).getText();
			System.out.println(" Birth Day & Time : " +text);
		}
		
   	}
   	
    // Selenium IDE Test 
    
    public static void seleniumIDE() {
    	
    	driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[contains(@href,'facebook.html')]")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test username");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("test password");
		driver.findElement(By.cssSelector("input#persist_box")).click();
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		driver.navigate().back();
    }
    
    //Cookie Handling 
    
    public static void cookieHandling() {
    	
    	driver.get("http://demo.guru99.com/test/cookie/selenium_aut.php");
    	/*driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[contains(@href,'selenium_aut.php')]")).click();*/
    	// Input Email id and Password If you are already Register		
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("abc123");							
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("123xyz");							
        driver.findElement(By.cssSelector("button[name='submit']")).click();					
        Set<Cookie> ckSet=	driver.manage().getCookies();
        Cookie newCookies = null;
        Date expiry = new Date();	
        for(Cookie ck : ckSet){	
        	 String name = ck.getName();					
             String value =ck.getValue();					
             String domain = ck.getDomain();					
             String path = ck.getPath();     
             expiry=ck.getExpiry();
             Boolean isSecure =ck.isSecure();
             newCookies = new Cookie(name,value,domain,path,expiry);
             driver.manage().addCookie(newCookies); 
             System.out.println(newCookies);
        }
           	driver.get("http://demo.guru99.com/test/cookie/selenium_aut.php");
            driver.navigate().refresh();        
            driver.manage().deleteAllCookies();   	           
    }
    
    //File Upload using sikuli : Include sikulixapi-1.1.0.jar 
    
    public static void sikuliFileUpload() throws FindFailed{
    	
    	driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
    	driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[contains(@href,'image_upload')]")).click();
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();
		Actions act=new Actions(driver);
        Screen s = new Screen();
        Pattern fileInputTextBox = new Pattern("F:\\SikuliImages\\FileName.PNG");
        Pattern firefoxInputTextBox = new Pattern("F:\\SikuliImages\\FireFoxFileName.PNG");
        Pattern openButton = new Pattern("F:\\SikuliImages\\OpenButton.PNG"); 	
		WebElement uploadButton=driver.findElement(By.xpath(".//*[@id='photoimg']"));
		if(browserName.equalsIgnoreCase("chrome")) {
			uploadButton.click();
			 s.wait(fileInputTextBox, 40);
			 s.type(fileInputTextBox, "F:\\testResume\\test.doc");
			 s.click(openButton);
		}
		else {
			act.moveToElement(uploadButton).click().build().perform();
			 s.wait(firefoxInputTextBox, 40);
			 s.type(firefoxInputTextBox, "F:\\testResume\\test.doc");
			 s.click(openButton);		
		}	
    }
    
	public static void main(String[] args) throws InterruptedException, FindFailed, IOException {

		// Run scripts in Chrome Browser
		setUpChrome("http://demo.guru99.com/");
		flashDemo();
		ajaxDemo();
		toolTipDemo();
		fileUpload();
		radioCheckBoxDemo();
		tableDemo();
		accessLink();
		blockLevelTags();
		deleteCustomer();
		yahooDemo();
		cookieHandling();
		sikuliFileUpload();
		login();
		socialIcons();
		scrollBar();
		dragAndDrop();
		datePicker();
		seleniumIDE();
		autoITDemo();
		
		// Run scripts in FireFox Browser
		setUpFireFox("http://demo.guru99.com/");
		
		flashDemo();
		ajaxDemo();
		toolTipDemo();
		radioCheckBoxDemo();
		tableDemo();
		accessLink();
		blockLevelTags();
		deleteCustomer();
		yahooDemo();
		fileUpload();
		login();
		socialIcons();
		autoITDemo();
		scrollBar();
		cookieHandling();
		dragAndDrop();
		datePicker();
		seleniumIDE();
		sikuliFileUpload(); 
		
	}

}
