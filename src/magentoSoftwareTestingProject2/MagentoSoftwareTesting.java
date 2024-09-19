package magentoSoftwareTestingProject2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MagentoSoftwareTesting extends Parameters {

	@BeforeTest
	public void setup() {
		GeneralSetup();
	}

	@Test(priority = 1, enabled = true)
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

	@Test(priority = 2, enabled = true)
	public void signUp() {
		driver.get(singOutPage);

		WebElement signOutAsElement = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
		String actualSignOut = signOutAsElement.getText();
		Assert.assertEquals(actualSignOut, expectedSignOut);
	}

	@Test(priority = 3, enabled = true)
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
	public void addBagsItems() {

	}

}
