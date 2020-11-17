// 1 - Pacote
package siteiterasys;

// 2 - Bibliotecas

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// 3 - Classe
public class Curso {
    WebDriver driver; // atributo - Selenium
    String url; // atributo url do site

    @Before // são as anotações
    public void iniciar(){
        // public todo mundo pode ver
        // void - método
        url = "https://www.iterasys.com.br";
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe"); //incluir tudo minúsculo, não incluir espaços
        driver = new ChromeDriver(); // Instanciar o Selenium como um controlador do Chrome
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000,TimeUnit.MILLISECONDS);
           }

    @After
    public void finalizar(){
    driver.quit();

    }

    @Test
    public void consultarTestLink(){
    driver.get(url);
    driver.findElement(By.id("searchtext")).sendKeys("TestLink" + Keys.ENTER);
    driver.findElement(By.cssSelector("span.comprar")).click();

    //Validar nome do curso = mesma forma de buscar que o validar preço
    String resultadoEsperado = "TestLink";
    //String resultadoEsperado = "Mantis";    // resultado errado
    String resultatoAtual = driver.findElement(By.cssSelector("span.item-title")).getText();
    assertEquals(resultadoEsperado,resultatoAtual);

    //Validar o preço = mesma forma de buscar que o validar nome do curso
    assertEquals("R$ 79,99",driver.findElement(By.cssSelector("span.new-price")).getText());

    //Validar o subTotal
    //String resultadoEsperado = "SUBTOTAL R$ 79,99";
    //String resultadoAtual = driver.findElement(By.cssSelector("div.subtotal")).getText();
    //assertEquals(resultadoEsperado,resultadoAtual);

    assertEquals("SUBTOTAL R$ 79,99",driver.findElement(By.cssSelector("div.subtotal")).getText());

    //Validar o valor das parcelas
    assertTrue( driver.findElement(By.cssSelector("div.ou-parcele")).getText().contains("ou em 12 x de R$ 8,03"));

       }

    @Test
    public void consultarMantis(){
        driver.get(url);
        driver.findElement(By.id("searchtext")).sendKeys("Mantis" + Keys.ENTER);
        driver.findElement(By.cssSelector("span.comprar")).click();

        //Validar nome do curso = mesma forma de buscar que o validar preço
        //String resultadoEsperado = "TestLink";
        String resultadoEsperado = "Mantis";    // resultado errado
        String resultatoAtual = driver.findElement(By.cssSelector("span.item-title")).getText();
        assertEquals(resultadoEsperado,resultatoAtual);

        //Validar o preço = mesma forma de buscar que o validar nome do curso
        assertEquals("R$ 49,99",driver.findElement(By.cssSelector("span.new-price")).getText());

        //Validar o subTotal
        //String resultadoEsperado = "SUBTOTAL R$ 79,99";
        //String resultadoAtual = driver.findElement(By.cssSelector("div.subtotal")).getText();
        //assertEquals(resultadoEsperado,resultadoAtual);

        assertEquals("SUBTOTAL R$ 49,99",driver.findElement(By.cssSelector("div.subtotal")).getText());

        //Validar o valor das parcelas
        assertTrue( driver.findElement(By.cssSelector("div.ou-parcele")).getText().contains("ou em 12 x de R$ 5,02"));

    }
}
