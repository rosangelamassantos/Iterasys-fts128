package site;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Boticario {
    String url = "https://www.boticario.com.br";
    WebDriver driver;
    String pastaPrint = "evidenciasboticario/" + new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime()) + "/";

    public void printScreen(String nomePrint) throws IOException {
    File foto =  ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(foto, new File(pastaPrint + nomePrint + ".png"));

    }

    @Before
    public void iniciar(){

        ChromeOptions chOptions = new ChromeOptions(); // instanciar o objeto de configuração do ChromeDriver
        chOptions.addArguments("--disable-notifications");

        System.setProperty("webdriver.chrome.driver","drivers/chrome/87/chromedriver.exe");
        driver = new ChromeDriver(chOptions); // <-- Instanciar o Selenium como um controlador do Chrome
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
    }

    @After
    public void finalizar(){
        //driver.quit();
    }

   @Test
    public void consultarEgeo() throws InterruptedException, IOException {
       driver.get(url);
       Thread.sleep(3000);
       printScreen("01 - Pagina Home");
       driver.findElement(By.id("onetrust-accept-btn-handler")).click();
       Thread.sleep(3000);
       driver.findElement(By.id("autocomplete-input")).sendKeys("Egeo bomb" + Keys.ENTER);
       printScreen("02 - Pesquisa perfume");
       Thread.sleep(3000);
       printScreen("03 - Pesquisa realizada");
       Assert.assertEquals("R$ 91,90", driver.findElement(By.cssSelector("div.item-price-value")).getText());
       // todo: fazer o 2º popup (do browser)

       }
}