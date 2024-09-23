package magentoSoftwareTestingProject2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MagentoSoftwareTesting extends Parameters {

	@BeforeTest
	public void setup() {
		GeneralSetup();
	}

	@Test(priority = 1, enabled = false)
	public void createAnAccountTest() throws InterruptedException {

		WebElement creatAnAccountButton = driver.findElement(By.linkText("Create an Account"));
		creatAnAccountButton.click();

		WebElement firstNameButtom = driver.findElement(By.id(firstName));
		WebElement lastNameButton = driver.findElement(By.id(lastName));
		WebElement emailButton = driver.findElement(By.id(emailAddress));
		WebElement passwordButton = driver.findElement(By.id(passwordInput));
		WebElement passwordConfirmationButton = driver.findElement(By.id(passwordConfirmation));

		firstNameButtom.sendKeys(randFirstName);
		lastNameButton.sendKeys(randLastName);
		emailButton.sendKeys(randFirstName + randLastName + randNamber + domanEmail);
		emailAddressToSignInPage = randFirstName + randLastName + randNamber + domanEmail;
		passwordButton.sendKeys(password);
		passwordConfirmationButton.sendKeys(password);

		WebElement submitButton = driver.findElement(By.cssSelector("button[title='Create an Account']"));
		submitButton.click();

		Thread.sleep(1000);

		WebElement messageAsWebElement = driver
				.findElement(By.cssSelector("div[data-bind='html: $parent.prepareMessageForHtml(message.text)']"));
		String actualMessageAsAccount = messageAsWebElement.getText();
		Assert.assertEquals(actualMessageAsAccount, expectedMessageAsAccount);
	}

	@Test(priority = 2, enabled = false)
	public void signUp() {
		driver.get(singOutPage);

		WebElement signOutAsElement = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
		String actualSignOut = signOutAsElement.getText();
		Assert.assertEquals(actualSignOut, expectedSignOut);
	}

	@Test(priority = 3, enabled = false)
	public void signIn() throws InterruptedException {
		WebElement loginButton = driver.findElement(By.cssSelector("div[class='panel header'] li[data-label='or'] a"));
		loginButton.click();

		WebElement emailInput = driver.findElement(By.id("email"));
		WebElement passwordInput = driver.findElement(By.id("pass"));
		WebElement signInButton = driver.findElement(By.id("send2"));

		emailInput.sendKeys(emailAddressToSignInPage);
		passwordInput.sendKeys(password);
		signInButton.click();

		Thread.sleep(1000);

		String messageAsSingIn = driver.findElement(By.cssSelector("div[class='panel header'] span[class='logged-in']"))
				.getText();
		boolean actualMessageAsSignIn = messageAsSingIn.contains("Welcome");
		Assert.assertEquals(actualMessageAsSignIn, expectedMessageAsSignIn);
	}

	@Test(priority = 4, enabled = true)
	public void addBagsItems() throws InterruptedException {

//		go to bags section add the first,third ,fifth item etc.. ( one element is added next one is skipped) 

		WebElement gearSection = driver.findElement(By.id("ui-id-6"));
		gearSection.click();

		WebElement bagsSection = driver.findElement(By.linkText("Bags"));
		bagsSection.click();

		WebElement itemsContainer = driver.findElement(By.cssSelector(".products.wrapper.grid.products-grid"));
		List<WebElement> items = itemsContainer.findElements(By.tagName("li"));

		// Loop to add first 3 items
		for (int i = 0; i < items.size(); i += 2) {
			// Store item URL before clicking
			String itemUrl = items.get(i).findElement(By.tagName("a")).getAttribute("href");

			// Open item in a new tab
			((JavascriptExecutor) driver).executeScript("window.open('" + itemUrl + "', '_blank');");

			// Switch to the new tab
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			// Add to cart
			WebElement addToCart = driver.findElement(By.cssSelector(".action.tocart.primary"));
			addToCart.click();

			Thread.sleep(2000);

			// Close the current tab
			driver.close();

			// Switch back to the main tab
			driver.switchTo().window(tabs.get(0));

		}
		driver.navigate().refresh();

		WebElement cartButton = driver.findElement(By.className("counter"));
		cartButton.click();

		WebElement PricesContainer = driver
				.findElement(By.cssSelector("span[data-bind='html: cart().subtotal_excl_tax'] span[class='price']"));
		String actualPrice = PricesContainer.getText();

		List<WebElement> AllPrices = PricesContainer.findElements(By.className("price"));
		for (WebElement priceElement : AllPrices) {
			String priceText = priceElement.getText();

			// Remove the "$" sign and the decimal ".00"
			String priceWithoutDollar = priceText.replace("$", "").split("\\.")[0];

			// Convert the remaining string to an integer
			int price = Integer.parseInt(priceWithoutDollar);

			String expectedPrice = Integer.toString(price);
			Assert.assertEquals(actualPrice, expectedPrice);

		}
	}

	@Test(priority = 5, enabled = true)
	public void checkOutProcess() {
		WebElement cartCheckout = driver.findElement(By.id("top-cart-btn-checkout"));
		cartCheckout.click();

	}
}
