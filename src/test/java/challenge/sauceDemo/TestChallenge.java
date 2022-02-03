package challenge.sauceDemo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class TestChallenge {
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		List<WebElement>priceLists = driver.findElements(By.className("inventory_item_price"));
		Double maximumPrice = Double.parseDouble(priceLists.get(0).getText().substring(1));
		ArrayList<Double> finalItemPrices = new ArrayList<Double>();
		for(int i=0; i<priceLists.size(); i++)
		{
			String prices = priceLists.get(i).getText();
			Double itemPrice = Double.parseDouble(prices.substring(1));
			finalItemPrices.add(itemPrice);
			if(finalItemPrices.get(i)>maximumPrice) {
				maximumPrice = finalItemPrices.get(i);
				driver.findElements(By.xpath("//*[@class='inventory_item_price']/following-sibling::button")).get(i).click();
				Assert.assertEquals(driver.findElements(By.xpath("//*[@class='inventory_item_price']/following-sibling::button")).get(i).getText(), "REMOVE");
			}
			
		
		}
		System.out.println("Maximum Price in the product lists: "+maximumPrice);
		driver.findElement(By.id("shopping_cart_container")).click();
		String finalPrice = driver.findElement(By.className("inventory_item_price")).getText();
		Double actualPrice = Double.parseDouble(finalPrice.substring(1));
		System.out.println("Actual Price in the checkout page: "+actualPrice);
		Assert.assertEquals(actualPrice, maximumPrice);
		driver.quit();
	}

}
