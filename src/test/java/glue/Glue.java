package glue;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import junit.framework.Assert;
import pages.Homescreen;

public class Glue 
{
	public AndroidDriver driver;
	public URL u;
	public Scenario s;
	public Properties p;
	public Homescreen hs;
	@Before
	public void method1(Scenario s) throws Exception
	{
		this.s=s;// s object work for current scenario
		//Load properties file
		FileInputStream fi=new FileInputStream("E:\\chin20786\\com.mindq.calc\\src\\test\\resources\\repository\\file1.properties");
		p=new Properties();
		p.load(fi);
		//Start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
		Thread.sleep(5000);
		//Get appium server address
		u=new URL("http://127.0.0.1:4723/wd/hub");
	}
	@Given("^launch site$")
	public void method2()
	{
		//Provide details of app and device(ARD)
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","165b8d0e");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","5.0.2");
		dc.setCapability("appPackage","com.android.bbkcalculator");
		dc.setCapability("appActivity","com.android.bbkcalculator.Calculator");
		//Create driver object to launch app in AVD 
		while(2>1)
		{
			try
			{
				driver=new AndroidDriver(u,dc);
				break;
			}
			catch(Exception ex)
			{	
			}
		}
		//Create object to page class
		hs=new Homescreen(driver);
	}
	@Then("^validate operation with given data$")
	public void method3(DataTable testdata) throws Exception
	{
		System.out.println("Method called");
		for(Map<String,String> data: testdata.asMaps())
		{
			try
			{
				//Wait for app launching
				Thread.sleep(10000);
				//Enter input1
				for(int i=0;i<data.get("input1").length();i++)
				{
					char d=data.get("input1").charAt(i);
					if(d=='-')
					{
						hs.clickminus();
						Thread.sleep(5000);
					}
					else
					{
						driver.findElement(By.xpath("//*[@resource-id='com.android.bbkcalculator:id/digit"+d+"']")).click();
						Thread.sleep(5000);
					}
				}
				//Click button for operation
				if(data.get("operation").equalsIgnoreCase("add"))
				{
					hs.clickadd();
					Thread.sleep(5000);
				}
				else if(data.get("operation").equalsIgnoreCase("substract"))
				{
					hs.clickminus();
					Thread.sleep(5000);
				}
				else if(data.get("operation").equalsIgnoreCase("multiply"))
				{
					hs.clickmultiply();
					Thread.sleep(5000);
				}
				else
				{
					hs.clickdivide();
					Thread.sleep(5000);
				}
				//Enter input2
				for(int i=0;i<data.get("input2").length();i++)
				{
					char d=data.get("input2").charAt(i);
					if(d=='-')
					{
						hs.clickminus();
						Thread.sleep(5000);
					}
					else
					{
						//driver.findElement(By.xpath("//android.widget.ImageButton[@text='"+d+"']")).click();
						driver.findElement(By.xpath("//*[@resource-id='com.android.bbkcalculator:id/digit"+d+"']")).click();
						Thread.sleep(5000);
					}
				}
				//Click equals
				hs.clickequals();
				Thread.sleep(5000);
				//Get output
				String temp=hs.getoutput();
				//Validation
				int i1=Integer.parseInt(data.get("input1"));
				int i2=Integer.parseInt(data.get("input2"));
				int o=Integer.parseInt(temp);
				if(data.get("operation").equalsIgnoreCase("add") && o==i1+i2)
				{
					s.write(data.get("operation")+"Test passed");
				}
				else if(data.get("operation").equalsIgnoreCase("substract") && o==i1-i2)
				{
					s.write(data.get("operation")+"Test passed");
				}
				else if(data.get("operation").equalsIgnoreCase("multiply") && o==i1*i2)
				{
					s.write(data.get("operation")+"Test passed");
				}
				else if(data.get("operation").equalsIgnoreCase("divide") && o==i1/i2)
				{
					s.write(data.get("operation")+"Test passed");
				}
				else
				{
					byte[] b=((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
					s.embed(b,data.get("operation")+"Test failed");
					Assert.fail();//to stop failed scenario execution
				}
				//Clear output
				hs.clickclear();
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
	}
	@And("^close app$")
	public void method4()
	{
		driver.closeApp();
	}
	@After
	public void method5() throws Exception
	{
		//Stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
 }

