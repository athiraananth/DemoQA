package com.selenium.demo;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Flash.FlashObjectWebDriver;

public class DemoQA {

	public static WebDriver driver;


	// For Chrome Browser
	public static void setUpChrome(String url) {

		System.setProperty("webdriver.chrome.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(url);

	}

	// For FireFox
	public static void setUpFireFox(String url) {

		System.setProperty("webdriver.gecko.driver", "F:\\Athira\\Selenium\\Selenium 3.141.59\\Driver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(url);

	}


	public static void tearDown(WebDriver driver) {
		driver.close();

	}



	// Mouse Hover Implementation : https://webref.ru/ : encoding UTF-8

	public static void mouseHoverDemo(){

		//driver.get("https://webref.ru/");
		WebElement menuItem=driver.findElement(By.xpath("//li[@class='dropdown']//span[text()='Руководства']"));
		Actions act = new Actions(driver);
		act.moveToElement(menuItem).clickAndHold().build().perform();
		List<WebElement> sMenuItems=driver.findElements(By.xpath("//li[@class='dropdown']/ul/li/a"));
		for(WebElement sMenu : sMenuItems) {
			String linkText=sMenu.getText();

			while(linkText.equalsIgnoreCase("Мобильные приложения") ||(linkText.equalsIgnoreCase("Mobile applications"))){
				try {
					if (sMenu.isEnabled() && sMenu.isDisplayed()) {
						
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", sMenu);
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

		driver.findElement(By.cssSelector("a[href*='selectable'")).click();
		List<WebElement> olList=driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		Actions act=new Actions(driver);
		int x = olList.size();
		for(int i=0;i<x;i++) {
			WebElement item=olList.get(i);
			//System.out.println(item.isDisplayed());

			if(i>5)
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", olList.get(i-3));
			}

			act.moveToElement(item).click().build().perform();

			if(item.getAttribute("class").contains("ui-selected")) {
				//System.out.println(item.getText());
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

		WebDriverWait wait = new WebDriverWait(driver, 15);
		driver.findElement(By.xpath("//a[contains(text(),'Selectmenu')]")).click();
		// Selecting speed from drop down
		WebElement speedElement=driver.findElement(By.xpath("//span[@id='speed-button']"));
		speedElement.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@id='speed-menu']"))));
		List<WebElement> sList=driver.findElements(By.xpath("//div[@class='ui-selectmenu-menu ui-front ui-selectmenu-open']/ul/li/div"));
		Actions act=new Actions(driver);
		//act.moveToElement(speed).build().perform();
		JavascriptExecutor js= ((JavascriptExecutor) driver);
		//js.executeScript("arguments[0].click();", speed);
		Iterator<WebElement> it= sList.iterator();

		while(it.hasNext()) {

			WebElement listElement=it.next();
			//System.out.println(listElement.getText());
			if(listElement.getText().equalsIgnoreCase("fast")) {

				act.moveToElement(listElement).build().perform();
				js.executeScript("arguments[0].click();", listElement);

				//System.out.println("Selected Speed:"+speedElement.getText());
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

				act.moveToElement(listElement).build().perform();
				js.executeScript("arguments[0].click();", listElement);
				break;
			}

		}

		js.executeScript("arguments[0].scrollIntoView(true);", speedElement);

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
				js.executeScript("arguments[0].scrollIntoView(true);", numberMenu.get(i-4));
				if(listElement.getText().equalsIgnoreCase("15")) {
					act.moveToElement(listElement).build().perform();
					js.executeScript("arguments[0].click();", listElement);
					break;
				}

			}

		}
		js.executeScript("arguments[0].scrollIntoView(true);", speedElement);

		//selecting salutation

		WebElement salElement=driver.findElement(By.xpath("//span[@id='salutation-button']"));
		salElement.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@id='salutation-menu']"))));
		List<WebElement> salList=driver.findElements(By.xpath("//div[@class='ui-selectmenu-menu ui-front ui-selectmenu-open']/ul/li/div"));
		it= salList.iterator();
		while(it.hasNext()) {

			WebElement listElement=it.next();
			//System.out.println(listElement.getText());

			if(listElement.getText().contains("Prof")) {

				act.moveToElement(listElement).build().perform();
				js.executeScript("arguments[0].click();", listElement);
				//System.out.println("Selected Salutation:"+salElement.getText());
				break;
			}

		}

	}


	//Progress Bar Implementation : DemoQA
	public static void progressBar() {

		driver.findElement(By.xpath("//a[contains(text(),'Progressbar')]")).click();
		WebElement pBar=driver.findElement(By.xpath("//div[@class='ui-progressbar-value ui-corner-left ui-widget-header']"));
		JavascriptExecutor js= ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('style', 'width: 80%;')",pBar);

	}

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

		driver.findElement(By.xpath("//a[contains(text(),'Checkboxradio')]")).click();

		// Radio Group
		//driver.findElement(By.cssSelector("input#radio-3")).click();
		List<WebElement> rButton=driver.findElements(By.xpath("//input[@name='radio-1']"));
		List<WebElement> labelList=driver.findElements(By.xpath("//label[contains(@for,'radio')]"));
		int count =rButton.size();
		for(int i=0;i<count;i++) {

			String text=labelList.get(i).getText();
			if(text.equalsIgnoreCase("Paris"))

			{

				labelList.get(i).click();
				//de-selects
				labelList.get(i).click();
				//Selects London
				labelList.get(i+1).click();

			}
		}

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


			}
		}

		JavascriptExecutor js= ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='widget']/h2")));
		// Check box Nested in Label		
		List<WebElement> nestedButton=driver.findElements(By.xpath("//label[@class='ui-checkboxradio-label ui-corner-all ui-button ui-widget']/child::input"));
		labelList=driver.findElements(By.xpath("//label[contains(@for,'checkbox-nested')]"));
		cCount=nestedButton.size();
		for(int i=0;i<cCount;i++) {

			String text=labelList.get(i).getText().trim();
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


	//Checkboxradio Implementation : DemoQA
	public static void controlGroupDemo() throws InterruptedException {

		driver.findElement(By.xpath("//a[contains(text(),'Controlgroup')]  ")).click();
		JavascriptExecutor js=((JavascriptExecutor) driver);
		Actions act= new Actions(driver);
		// Controlgroup-Horizontal
		WebElement carType=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/span[@id='car-type-button']"));
		carType.click();
		//selecting car type
		WebElement selCarType=driver.findElement(By.xpath("//div[contains(@class,'ui-selectmenu-open')]/ul[@id='car-type-menu']/li[5]"));
		act.moveToElement(selCarType).build().perform();
		js.executeScript("arguments[0].click();", selCarType);
		Thread.sleep(500);
		//Selecting transmission
		carType.sendKeys(Keys.TAB);
		WebElement transmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/input[@id='transmission-automatic']"));
		//Selecting Automatic
		js.executeScript("arguments[0].click();", transmission);
		//transmission.sendKeys(Keys.ENTER);
		Thread.sleep(200);
		transmission.sendKeys(Keys.ARROW_RIGHT);
		//Toggles to Standard
		transmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/input[@id='transmission-standard']"));
		js.executeScript("arguments[0].click();", transmission);
		Thread.sleep(500);
		transmission.sendKeys(Keys.TAB);
		//selecting insurance


		WebElement insurance=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-horizontal')]/input[@type='checkbox']"));
		js.executeScript("arguments[0].click();", insurance);
		/*insurance.sendKeys(Keys.ENTER);*/
		insurance.sendKeys(Keys.TAB);
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
		vCarType.click();
		WebElement selCarType2=driver.findElement(By.xpath("//div[contains(@class,'ui-selectmenu-open')]/ul[@id='ui-id-8-menu']/li[3]"));
		act.moveToElement(selCarType2).build().perform();
		js.executeScript("arguments[0].click();", selCarType2);
		Thread.sleep(500);
		vCarType.sendKeys(Keys.TAB);
		//selecting transmission
		WebElement vTransmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]/input[@id='transmission-automatic-v']"));
		js.executeScript("arguments[0].click();", vTransmission);
		Thread.sleep(200);
		transmission.sendKeys(Keys.ARROW_UP);

		vTransmission=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]/input[@id='transmission-standard-v']"));
		js.executeScript("arguments[0].click();", vTransmission);
		Thread.sleep(500);
		vTransmission.sendKeys(Keys.TAB);
		//selecting insurance
		WebElement vInsurance=driver.findElement(By.xpath("//div[contains(@class,'ui-controlgroup-vertical')]/input[@type='checkbox']"));
		js.executeScript("arguments[0].click();", vInsurance);
		Thread.sleep(500);
		vInsurance.sendKeys(Keys.TAB);
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
		hSpinner.sendKeys(Keys.DOWN);
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
		act.moveToElement(jazzMenu).build().perform();
		JavascriptExecutor js=((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", jazzMenu);
		Thread.sleep(500);
		act.moveToElement(modern).build().perform();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", modern);
		act.moveToElement(elecMenu).click().build().perform();
		Thread.sleep(500);
		act.moveToElement(utlities).build().perform();
		js.executeScript("arguments[0].click();", utlities);
	}

	public static void main(String[] args) throws InterruptedException {



		/*setUpChrome("https://webref.ru/");
		mouseHoverDemo();
		tearDown(driver);
       */
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
		progressBar();
		menuDemo();
		dialogDemo();
		datePicker();
		controlGroupDemo();
		checkboxRadioDemo();
		buttonsDemo();
		autoComplete();
		accordion();
		tearDown(driver);


		/*setUpFireFox("https://webref.ru/");
		mouseHoverDemo();
		tearDown(driver);*/

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
		progressBar();
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
