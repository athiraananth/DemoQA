package com.demo.selenium;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import Flash.FlashObjectWebDriver;

public class DemoQA {

	public static WebDriver driver;


	// For Chrome Browser
	public static void setUpChrome(String url) {

		System.setProperty("webdriver.chrome.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\chromedriver.exe");
		DesiredCapabilities dc = DesiredCapabilities.chrome();
		dc.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		driver = new ChromeDriver(dc);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.get(url);

	}

	// For FireFox
	public static void setUpFireFox(String url) {

		System.setProperty("webdriver.gecko.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\geckodriver.exe");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		driver = new FirefoxDriver(dc);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.get(url);

	}


	public static void tearDown(WebDriver driver) {
		driver.close();

	}



	/* Mouse Hover Implementation : https://webref.ru/ : encoding UTF-8
	 * Not working in chrome using WebDriver 
	 * Implemented chrome interaction using Sikuli :  works in both browser
	 * Implemented firefox interaction using WebDriver
*/
	public static void mouseHoverDemo() throws InterruptedException, FindFailed{

		//driver.get("https://webref.ru/");
		//WebElement menuItem=driver.findElement(By.xpath("//li[@class='dropdown']//span[text()='Руководства']"));
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();
		WebElement menuItem=driver.findElement(By.xpath("//li[3]//span[1]"));
		if(browserName.equalsIgnoreCase("chrome")) {

			Screen s = new Screen();
			Pattern subMenu  = new Pattern("F:\\SikuliImages\\SubMenu.PNG");
			Pattern menu  = new Pattern("F:\\SikuliImages\\MainMenu.PNG");
			Pattern mobileMenu  = new Pattern("F:\\SikuliImages\\MobileMenu.PNG");
			s.wait(menu, 40);
			s.click(menu);	
			s.wait(subMenu, 40);
			s.mouseMove(subMenu);
			s.wait(mobileMenu, 40);
			s.click(mobileMenu);	
		}
		else {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(menuItem));
			Actions act = new Actions(driver);
			act.moveToElement(menuItem).perform();
			List<WebElement> sMenuItems=driver.findElements(By.xpath("//li[@class='dropdown']/ul/li/a"));
			for(WebElement sMenu : sMenuItems) {
				String linkText=sMenu.getText();

				while(linkText.equalsIgnoreCase("Мобильные приложения") ||(linkText.equalsIgnoreCase("Mobile applications"))){
					try {
						if (sMenu.isEnabled() && sMenu.isDisplayed()) {
							act.moveToElement(sMenu).perform();
							wait.until(ExpectedConditions.elementToBeClickable(sMenu));
							sMenu.click();
							break;
						} else {
							System.out.println("Unable to click on element");
						}
					}

					catch(StaleElementReferenceException e) {
						System.out.println("Element is not attached to the page document "+ e.getStackTrace());
						driver.close();
					}
				}	

			}
		}
	}



	//Tooltip & Double Click Implementation	: Demo QA

	public  static void tTipDoubleClick() {

		driver.findElement(By.cssSelector("a[href*='double-click'")).click();
		Actions act= new Actions(driver);
		//double click on button
		act.doubleClick(driver.findElement(By.cssSelector("button[id='doubleClickBtn']"))).build().perform();
		//Click OK on Alert
		driver.switchTo().alert().accept();
		//Right click to see context menu
		act.contextClick(driver.findElement(By.cssSelector("button[id='rightClickBtn']"))).build().perform();
		// Tool Tipexample
		WebElement tTip=driver.findElement(By.cssSelector("div.tooltip"));
		act.moveToElement(tTip).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.tooltiptext")));
		System.out.println("Tool Tip Text: "+ driver.findElement(By.cssSelector("span.tooltiptext")).getText());
	}

	//Resizable Interaction Implementation : DemoQA
	public static void resizableDemo() {

		driver.findElement(By.cssSelector("a[href*='resizable'")).click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		Actions act= new Actions(driver);
		act.clickAndHold(driver.findElement(By.xpath("//div[@id='resizable']/div[3]"))).moveByOffset(40, 80).release().build().perform();
	}

	//DatePicker Widget Implementation : DemoQA
	public static void datePicker() {

		driver.findElement(By.cssSelector("a[href*='datepicker'")).click();
		WebElement datePicker=driver.findElement(By.xpath(".//*[@id='datepicker']"));
		datePicker.click();
		WebDriverWait wait=new WebDriverWait(driver, 60);
		Actions act= new Actions(driver);
		WebElement calender=driver.findElement(By.xpath("//div[@id='ui-datepicker-div']"));
		List<WebElement> dates = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//tbody//tr/td[@data-handler='selectDay']"));
		int lCount=dates.size();
		//selects date from Current Month :August 16
		/*wait.until(ExpectedConditions.visibilityOf(calender));
		act.moveToElement(calender).perform();*/
		for(WebElement date : dates) {
			String dText=date.getText();
			if(dText.equalsIgnoreCase("16")) {
				date.click();
				break;
			}
		 }
			//selects date from Previous Month :July 20
			datePicker.click();
			/*wait.until(ExpectedConditions.visibilityOf(calender));
			act.moveToElement(calender).perform();*/
			WebElement prevMonth=driver.findElement(By.xpath("//div[contains(@class,'ui-datepicker-header')]//a//span[@class='ui-icon ui-icon-circle-triangle-w']"));
			prevMonth.click();	
			dates = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//tbody//tr/td[@data-handler='selectDay']"));
			for(WebElement preDate : dates) {
				String pText=preDate.getText();
				if(pText.equalsIgnoreCase("20")) {
					preDate.click();
					break;
				}
			}
		}


	//Sortable Interaction Implementation : DemoQA
	public static void sortable()  {
		driver.findElement(By.cssSelector("a[href*='sortable'")).click();
		List<WebElement> sItems=driver.findElements(By.cssSelector("li[class='ui-state-default ui-sortable-handle']"));
		int itemCount=sItems.size();
		Actions act= new Actions(driver);
		for(int i=0;i<itemCount-1;i++) {
			act.dragAndDrop(sItems.get(i+1),sItems.get(i));
			act.build().perform();
		}
	}

	//Selectable Interaction Implementation : DemoQA 

	public static void selectable() {
		driver.manage().window().setSize(new Dimension(1400, 780));
		driver.findElement(By.cssSelector("a[href*='selectable'")).click();
		List<WebElement> olList=driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		Actions act=new Actions(driver);
		int x = olList.size();
		for(int i=0;i<x;i++) {
			WebElement item=olList.get(i);
			act.moveToElement(item).click().build().perform();
			if(item.getAttribute("class").contains("ui-selected")) {
				System.out.println(item.getText());
			}
			else {
				System.out.println("Something went Wrong, Element not Selected!!");
			}
		}
	}

	//Droppable Interaction Implementation : DemoQA

	public static void droppable() {
		driver.findElement(By.cssSelector("a[href*='droppable'")).click();
		WebElement source=driver.findElement(By.cssSelector("#draggable"));
		WebElement target=driver.findElement(By.cssSelector("#droppable"));
		source.click();
		Actions act = new Actions(driver);
		act.dragAndDrop(source, target).build().perform();
	}

	//Draggable Interaction Implementation : DemoQA

	public static void draggable() {
		driver.findElement(By.cssSelector("a[href*='draggable'")).click();
		WebElement source=driver.findElement(By.cssSelector("div[id='draggable']"));
		Actions act = new Actions(driver);
		act.clickAndHold(source).moveByOffset(140, 110).build().perform();
	}

	//Keyboard Events Sample Form Implementation : DemoQA

	public static void keyBoardEvents() throws InterruptedException {
		driver.findElement(By.cssSelector("a[href*='keyboard-events'")).click();
		WebElement nameField=driver.findElement(By.xpath("//input[@id='userName']"));
		Actions act = new Actions(driver);
		act.click(nameField).sendKeys(" Test Name").sendKeys(Keys.TAB).build().perform();
		act.click(driver.findElement(By.cssSelector("textarea#currentAddress"))).sendKeys(" Current Address").sendKeys(Keys.TAB).build().perform();
		act.click(driver.findElement(By.cssSelector("textarea#permanentAddress"))).sendKeys(" Permanent Address").sendKeys(Keys.TAB).build().perform();
		act.click(driver.findElement(By.cssSelector("input[type='submit']"))).build().perform();
		Thread.sleep(500);
		String alertText=driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
	}

	//Keyboard Events Sample Form Implementation : DemoQA

	public static void toolTip() {
		
		driver.findElement(By.xpath("//aside[2]//ul[1]//li[3]//a[1]")).click();
		WebElement agetTip=driver.findElement(By.cssSelector("input#age"));
		Actions act = new Actions(driver);
		act.moveToElement(agetTip).build().perform();
		String toolTipText=driver.findElement(By.xpath("//div[@class='ui-helper-hidden-accessible']/div")).getText();
		System.out.println("toolTipText : "+toolTipText);
	}

	//Tabs Implementation : DemoQA
	public static void tabDemo() {
		driver.findElement(By.xpath("//a[contains(text(),'Tabs')]")).click();
		List<WebElement> tabElements=driver.findElements(By.xpath("//div[@id='tabs']/ul/li/a"));
		Iterator<WebElement> it= tabElements.iterator();
		Actions act = new Actions(driver);
		while(it.hasNext()) {
			WebElement tab=it.next();
			act.click(tab).build().perform();
			System.out.println(" Tab Title: "+tab.getText());
		}
	}

	//Spinner Implementation : DemoQA

	public static void spinnerDemo() throws InterruptedException {
		driver.findElement(By.xpath("//a[contains(text(),'Spinner')]")).click();
		WebElement spinner=driver.findElement(By.cssSelector("input#spinner"));
		WebElement spinnerUp=driver.findElement(By.xpath("//span[@class='ui-button-icon ui-icon ui-icon-triangle-1-n']"));
		WebElement spinnerDown=driver.findElement(By.xpath("//span[@class='ui-button-icon ui-icon ui-icon-triangle-1-s']"));
		Actions act = new Actions(driver);
		act.clickAndHold(spinnerUp).build().perform();
		driver.findElement(By.xpath(" //button[@id='getvalue']")).click();
		String spText=driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		act.clickAndHold(spinnerDown).build().perform();
		Thread.sleep(500);
		act.release(spinnerDown).build().perform();
		driver.findElement(By.xpath("//button[@id='setvalue']")).click();
		if(spinner.getAttribute("aria-valuenow").equalsIgnoreCase("5")) {
			Thread.sleep(500);
			System.out.println("Successfully set value to 5 ");
		}
		// Spinner disabled
		driver.findElement(By.cssSelector("button#disable")).click();
		Thread.sleep(500);
		if(spinner.isEnabled()) {
			System.out.println("Spinner Disabled");

			driver.findElement(By.cssSelector("button#disable")).click();
		}
		else {
			System.out.println("Spinner Enabled");
			driver.findElement(By.cssSelector("button#disable")).click();
		}

		System.out.println(spinner.getAttribute("aria-valuenow"));

		//toggle widget 
		driver.findElement(By.cssSelector("button#destroy")).click();

		spinner.clear();
		spinner.sendKeys("25");
		// Toggle to Spinner
		driver.findElement(By.cssSelector("button#destroy")).click();

	}

	//Slider Implementation : DemoQA
	public static void sliderDemo() {

		driver.findElement(By.xpath("//a[contains(text(),'Slider')]")).click();
		WebElement slider=driver.findElement(By.xpath("//span[@class='ui-slider-handle ui-corner-all ui-state-default']"));
		Actions act = new Actions(driver);
		act.dragAndDropBy(slider, 200, 0).build().perform();

		System.out.println("Slider Percent: " +slider.getAttribute("style"));


	}

	//Select Menu Implementation : DemoQA 
	public static void selectMenu() {


		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.findElement(By.xpath("//a[contains(text(),'Selectmenu')]")).click();
		// Selecting speed from drop down
		WebElement speedElement=driver.findElement(By.xpath("//span[@id='speed-button']"));
		speedElement.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@id='speed-menu']"))));
		List<WebElement> sList=driver.findElements(By.xpath("//div[@class='ui-selectmenu-menu ui-front ui-selectmenu-open']/ul/li/div"));
		Actions act=new Actions(driver);
		//act.moveToElement(speed).build().perform();
		Iterator<WebElement> it= sList.iterator();

		while(it.hasNext()) {

			WebElement listElement=it.next();
			//System.out.println(listElement.getText());
			if(listElement.getText().equalsIgnoreCase("fast")) {

				wait.until(ExpectedConditions.visibilityOf(listElement));
				//act.moveToElement(listElement).perform();
				act.moveToElement(listElement).click().build().perform();
				break;
			}

		}

		// Selecting file from drop down
		WebElement fileElement=driver.findElement(By.xpath("//span[@id='files-button']"));
		fileElement.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@id='files-menu']"))));
		List<WebElement> fList=driver.findElements(By.xpath("//div[@class='ui-selectmenu-menu ui-front ui-selectmenu-open']/ul/li/div"));
		it= fList.iterator();
		while(it.hasNext()) {

			WebElement listElement=it.next();
			//System.out.println(listElement.getText());
			if(listElement.getText().contains("ui.jQuery")) {

				wait.until(ExpectedConditions.visibilityOf(listElement));
				//act.moveToElement(listElement).perform();
				act.moveToElement(listElement).click().build().perform();
				break;
			}

		}


		//selecting number 
		WebElement numberElement=driver.findElement(By.xpath("//span[@id='number-button']"));
		numberElement.click();
		List<WebElement> numberMenu=driver.findElements(By.xpath("//div[@class='ui-selectmenu-menu ui-front ui-selectmenu-open']/ul/li/div"));

		int listSize=numberMenu.size();
		for(int i=0;i<listSize;i++) {

			WebElement listElement=numberMenu.get(i);
			String text=listElement.getText();
			//System.out.println(listElement.getText());
			if(i>5) {

				listElement.sendKeys(Keys.PAGE_DOWN);
				if(listElement.getText().equalsIgnoreCase("15")) {
					wait.until(ExpectedConditions.visibilityOf(listElement));
					//act.moveToElement(listElement).perform();
					act.moveToElement(listElement).click().build().perform();

					break;
				}

			}

		}
		//selecting salutation

		WebElement salElement=driver.findElement(By.xpath("//span[@id='salutation-button']"));
		salElement.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@id='salutation-menu']"))));
		List<WebElement> salList=driver.findElements(By.xpath("//div[@class='ui-selectmenu-menu ui-front ui-selectmenu-open']/ul/li/div"));
		it= salList.iterator();

		while(it.hasNext()) {

			WebElement listElement=it.next();
			listElement.sendKeys(Keys.PAGE_DOWN);
			//System.out.println(listElement.getText());
			wait.until(ExpectedConditions.visibilityOf(listElement));
			act.moveToElement(listElement).perform();
			if(listElement.getText().contains("Prof")) {

				act.moveToElement(listElement).perform();
				act.click().build().perform();
				break;
			}

		}

	}


	/* //Progress Bar Implementation : DemoQA 
	 //No explicit user interactions to automate this element

	public static void progressBar() {

		driver.findElement(By.xpath("//a[contains(text(),'Progressbar')]")).click();
		WebElement pBar=driver.findElement(By.xpath("//div[@class='ui-progressbar-value ui-corner-left ui-widget-header']"));
		System.out.println(pBar.getAttribute("style"));
		//width: 80%;

	}
	 */

	//Button Implementation : DemoQA

	public static void buttonsDemo() throws InterruptedException {

		driver.findElement(By.xpath("//a[contains(text(),'Button')]")).click();

		//widget buttons
		driver.findElement(By.cssSelector("div[class='widget'] input[type='submit']")).click();
		Thread.sleep(500);
		driver.findElement(By.cssSelector("div[class='widget'] button")).click();
		Thread.sleep(500);
		driver.findElement(By.cssSelector("div[class='widget'] a")).click();
		Thread.sleep(500);

		// CSS buttons
		driver.findElement(By.cssSelector("input[class*='ui-button ui-widget']")).click();
		Thread.sleep(500);
		driver.findElement(By.cssSelector("button[class*='ui-button ui-widget']")).click();
		Thread.sleep(500);
		driver.findElement(By.cssSelector("a[class*='ui-button ui-widget']")).click();


	}

	//AutoComplete Implementation : DemoQA

	public static void autoComplete() throws InterruptedException {

		driver.findElement(By.xpath("//a[contains(text(),'Autocomplete')]")).click();
		WebElement autoElement=driver.findElement(By.cssSelector("input#tags"));
		autoElement.sendKeys("B");		
		autoElement.sendKeys(Keys.DOWN);
		Thread.sleep(500);
		autoElement.sendKeys(Keys.DOWN);
		Thread.sleep(500);
		autoElement.sendKeys(Keys.DOWN);
		Thread.sleep(500);
		autoElement.sendKeys(Keys.ENTER);
	}

	//Accordion Implementation : DemoQA

	public static void accordion() throws InterruptedException {

		driver.findElement(By.xpath("//a[contains(text(),'Accordion')]")).click();
		List<WebElement> accordList=driver.findElements(By.xpath("//h3[contains(@class,'ui-accordion-header')]/span"));
		for(WebElement accordion : accordList) {

			accordion.click();
			Thread.sleep(500);
		}

		accordList=driver.findElements(By.xpath("//h3[contains(@class,'ui-accordion-header')]"));

		for(int i=0; i<(accordList.size());i++) {

			String aTitle=accordList.get(i).getText().trim();
			if(aTitle.equalsIgnoreCase("Section 3")) {
				accordList.get(i).click();
			}
		}

	}

	//Checkboxradio Implementation : DemoQA 

	public static void checkboxRadioDemo() {

		driver.manage().window().setSize(new Dimension(1400, 780));
		driver.findElement(By.xpath("//a[contains(text(),'Checkboxradio')]")).click();
		// Radio Group
		//driver.findElement(By.cssSelector("input#radio-3")).click();
		List<WebElement> rButton=driver.findElements(By.xpath("//input[@name='radio-1']"));
		List<WebElement> labelList=driver.findElements(By.xpath("//label[contains(@for,'radio')]"));
		int count =rButton.size();
		for(int i=0;i<count;i++) {
			WebElement element=labelList.get(i);
			String text=element.getText();
			if(text.equalsIgnoreCase("Paris"))
			{
				element.click();
				//de-selects
				element.click();
				//Selects London
				element=labelList.get(i+1);
				element.click();
				/*element.sendKeys(Keys.ENTER);
				element.sendKeys(Keys.PAGE_DOWN);*/
			}

		}

		//driver.findElement(By.xpath("//div[@class='widget']/h2[2]")).sendKeys(Keys.PAGE_DOWN);
		List<WebElement> checkButton=driver.findElements(By.xpath("//input[@type='checkbox']"));

		labelList=driver.findElements(By.xpath("//label[contains(@for,'checkbox-')]"));
		int cCount=checkButton.size();
		for(int i=0;i<cCount;i++) {
			String text=labelList.get(i).getText().trim();
			if(text.equalsIgnoreCase("3 Star"))
			{
				//Toggled on
				labelList.get(i).click();
				labelList.get(i+1).click();
				//Toggled off
				labelList.get(i).click();
				//labelList.get(i).sendKeys(Keys.PAGE_DOWN);
			}
		}

		//driver.findElement(By.xpath("//div[@class='widget']/h2[3]")).sendKeys(Keys.PAGE_DOWN);
		// Check box Nested in Label		
		List<WebElement> nestedButton=driver.findElements(By.xpath("//label[@class='ui-checkboxradio-label ui-corner-all ui-button ui-widget']/child::input"));
		labelList=driver.findElements(By.xpath("//label[contains(@for,'checkbox-nested')]"));
		cCount=nestedButton.size();
		//driver.findElement(By.xpath("//div[@class='widget']/h2[3]")).sendKeys(Keys.PAGE_DOWN);
		for(int i=0;i<cCount;i++) {
			String text=labelList.get(i).getText().trim();
			//labelList.get(i).sendKeys(Keys.PAGE_DOWN);
			if(text.equalsIgnoreCase("2 Queen"))
			{
				//Toggled on
				labelList.get(i).click();
				labelList.get(i+1).click();
				//Toggled off
				labelList.get(i).click();
			}
		}

	}


	//ControlGroup Implementation : DemoQA 
	public static void controlGroupDemo() throws InterruptedException {

		driver.findElement(By.xpath("//a[contains(text(),'Controlgroup')]  ")).click();
		Actions act= new Actions(driver);
		// Controlgroup-Horizontal
		WebElement carType=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/span[@id='car-type-button']"));
		act.click(carType).perform();
		//selecting car type
		WebElement selCarType=driver.findElement(By.xpath("//div[contains(@class,'ui-selectmenu-open')]/ul[@id='car-type-menu']/li[5]"));
		act.moveToElement(selCarType).perform();
		act.click().perform();
		Thread.sleep(500);
		//Selecting transmission
		carType.sendKeys(Keys.TAB);
		WebElement transmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]//label[contains(text(),'Automatic')]"));
		//Selecting Automatic
		act.moveToElement(transmission).perform();
		act.click(transmission).perform();		
		WebElement transElement=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/input[@id='transmission-automatic']"));
		Thread.sleep(200);
		transElement.sendKeys(Keys.ARROW_RIGHT);
		//Toggles to Standard
		WebElement stransmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]//label[contains(text(),'Standard')]"));
		act.moveToElement(stransmission).perform();
		act.click().perform();
		Thread.sleep(500);
		//selecting insurance
		WebElement insurance=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]//label[contains(text(),'Insurance')]"));
		act.click(insurance).perform();
		//Horizontal Spinner
		WebElement hSpinner=driver.findElement(By.xpath("//input[@id='horizontal-spinner']"));
		hSpinner.sendKeys("5");
		// increments # of car
		int i=0;
		do{
			hSpinner.sendKeys(Keys.UP);
			i++;
		}while(i<5);
		Thread.sleep(500);
		// decrements # of car
		hSpinner.sendKeys(Keys.DOWN);
		WebElement hButton=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/button[contains(@class,'ui-button')]"));
		hButton.sendKeys(Keys.ENTER);
		//Controlgroup-Vertical
		//selecting car type
		WebElement vCarType=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]//span[contains(@class,'ui-selectmenu-button')]"));
		act.click(vCarType).perform();
		WebElement selCarType2=driver.findElement(By.xpath("//div[contains(@class,'ui-selectmenu-open')]/ul[@id='ui-id-8-menu']/li[3]"));
		act.moveToElement(selCarType2).perform();
		act.click().perform();
		Thread.sleep(500);
		vCarType.sendKeys(Keys.TAB);
		//selecting transmission
		WebElement vTransmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]//label[contains(text(),'Standard')]"));
		act.moveToElement(vTransmission).perform();
		act.click(vTransmission).perform();
		Thread.sleep(200);
		WebElement aTransmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]//label[contains(text(),'Automatic')]"));	
		WebElement vTransElement=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]/input[@id='transmission-automatic-v']"));
		act.moveToElement(aTransmission).perform();
		act.click(aTransmission).perform();
		Thread.sleep(500);
		vTransElement.sendKeys(Keys.TAB);
		//selecting insurance

		WebElement vInsurance=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]//label[contains(text(),'Insurance')]"));
		act.moveToElement(vInsurance).perform();
		act.click().perform();
		Thread.sleep(500);
		//Vertical Spinner
		WebElement vSpinner=driver.findElement(By.xpath("//input[@id='vertical-spinner']"));
		vSpinner.sendKeys("8");
		i=0;
		do{
			vSpinner.sendKeys(Keys.UP);
			i++;
		}while(i<5);
		Thread.sleep(500);
		// decrements # of car
		vSpinner.sendKeys(Keys.DOWN);
		Thread.sleep(500);
		//click button
		WebElement vButton=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]/button[contains(@class,'ui-button')]"));
		vButton.sendKeys(Keys.ENTER);		

	}


	//Dialog Implementation  : DemoQA

	public static void dialogDemo() {

		driver.findElement(By.xpath("//a[contains(text(),'Dialog')]")).click();
		WebElement dialogTitle=driver.findElement(By.cssSelector("div[class*='ui-dialog-titlebar']"));
		WebElement dialogHandle=driver.findElement(By.xpath("//div[contains(@class,'ui-resizable-ne')]"));
		Actions action= new Actions(driver);
		//drag dialog
		action.clickAndHold(dialogTitle).moveByOffset(140, 110).build().perform();
		//Resize dialog
		action.dragAndDropBy(dialogHandle, 140, 100).build().perform();
		action.dragAndDropBy(dialogHandle, 40, 80).build().perform();
		//Close dialog
		driver.findElement(By.xpath("//div[contains(@class,'ui-dialog-titlebar')]/button/span[contains(@class,'ui-icon-closethick')]")).click();
	}

	//Menu Implementation  : DemoQA 

	public static void menuDemo() throws InterruptedException {

		driver.findElement(By.xpath("//a[contains(text(),'Menu')]")).click();
		List<WebElement> menuItems=driver.findElements(By.xpath("//li[@class='ui-menu-item']/div"));
		Actions act=new Actions(driver);
		WebElement musicMenu=driver.findElement(By.xpath("//div[@id='ui-id-9']"));
		WebElement elecMenu=driver.findElement(By.xpath("//div[@id='ui-id-4']"));
		WebElement utlities=driver.findElement(By.xpath("//div[@id='ui-id-7']"));
		WebElement rockMenu=driver.findElement(By.xpath("//div[@id='ui-id-10']"));
		WebElement classicMenu=driver.findElement(By.xpath("//div[@id='ui-id-12']"));
		WebElement jazzMenu=driver.findElement(By.xpath("//div[@id='ui-id-13']"));
		WebElement modern=driver.findElement(By.xpath("//div[@id='ui-id-16']"));
		act.moveToElement(musicMenu).click().build().perform();
		Thread.sleep(500);
		act.moveToElement(jazzMenu).perform();
		Thread.sleep(500);
		act.moveToElement(modern).click().build().perform();
		act.moveToElement(elecMenu).perform();
		Thread.sleep(500);
		act.moveToElement(utlities).click().build().perform();

	}

	public static void main(String[] args) throws InterruptedException, FindFailed {



		setUpChrome("https://webref.ru/");
		mouseHoverDemo();
		tearDown(driver);
		
		setUpChrome("https://demoqa.com/");
		// Interactions
		sortable();
		selectable();
		resizableDemo();
		droppable();
		draggable();

		// Widgets
		keyBoardEvents();
		tTipDoubleClick();
		toolTip();
		tabDemo();
		spinnerDemo();
		sliderDemo();
		selectMenu();
		menuDemo();
		dialogDemo();
		datePicker();
		controlGroupDemo();
		checkboxRadioDemo();
		buttonsDemo();
		autoComplete();
		accordion();
		tearDown(driver);



		setUpFireFox("https://demoqa.com/");

		// Interactions
		sortable();
		selectable();
		resizableDemo();
		droppable();
		draggable();

		// Widgets
		keyBoardEvents();
		tTipDoubleClick();
		toolTip();
		tabDemo();
		spinnerDemo();
		sliderDemo();
		selectMenu();
		menuDemo();
		dialogDemo();
		datePicker();
		controlGroupDemo();
		checkboxRadioDemo();
		buttonsDemo();
		autoComplete();
		accordion();
		tearDown(driver);
		
	}

}
