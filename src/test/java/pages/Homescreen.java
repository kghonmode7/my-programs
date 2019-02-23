package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;

public class Homescreen 
{
	public AndroidDriver driver;
	@FindBy(xpath="//*[@resource-id='com.android.bbkcalculator:id/minus']")
	public WebElement minus;
	@FindBy(xpath="//*[@resource-id='com.android.bbkcalculator:id/plus']")
	public WebElement plus;
	@FindBy(xpath="//*[@resource-id='com.android.bbkcalculator:id/mul']")
	public WebElement multiply;
	@FindBy(xpath="//*[@resource-id='com.android.bbkcalculator:id/div']")
	public WebElement divide;
	@FindBy(xpath="//*[@resource-id='com.android.bbkcalculator:id/clear']")
	public WebElement clear;
	@FindBy(xpath="//*[@resource-id='com.android.bbkcalculator:id/equal']")
	public WebElement equals;
	@FindBy(xpath="//*[@class='android.widget.EditText']")
	public WebElement outputbox;
	public Homescreen(AndroidDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	public void clickadd()
	{
		plus.click();
	}
	public void clickminus()
	{
		minus.click();
	}
	public void clickmultiply()
	{
		multiply.click();
	}
	public void clickdivide()
	{
		divide.click();
	}
	public void clickequals()
	{
		equals.click();
	}
	public void clickclear()
	{
		clear.click();
	}
	public String getoutput()
	{
		String o=outputbox.getAttribute("text");
		return(o);
	}
}
