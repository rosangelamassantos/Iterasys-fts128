// 1 - pacote
package site;

// 2 - bibliotecas

import org.apache.commons.io.FileUtils;
import org.junit.After;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


// 3 - classe
@RunWith(Parameterized.class) // Esta classe é parametrizada = lê uma massa de teste
public class Curso {
    // 3.1 - atributos
    WebDriver driver;
    static String url;
    String pastaPrint = "evidencias/" + new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime()) + "/";

    // 3.2 - métodos ou funções

    // métodos ou funções de apoio (util / commons)

    //método para tirar print
    public void tirarPrint(String nomePrint) throws IOException {
        File foto = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(foto,new File(pastaPrint + nomePrint + ".png"));
    }

    // função para ler uma massa de teste

    // 1 - Atributos
    private String id;
    private String curso;
    private String valor;
    private String subtotal;
    private String parcelamento;
    private String browser;

    // 2 - Construtor (De-Para entre os campos na massa e os atributos)

    public Curso(String id, String curso, String valor, String subtotal, String parcelamento, String browser) {
        this.id = id;
        this.curso = curso;
        this.valor = valor;
        this.subtotal = subtotal;
        this.parcelamento = parcelamento;
        this.browser = browser;
    }


    // 3 - Collection Intermediária entre o Constructor e a Collection que vai fazer a leitura
    // Ela serve para apontar a pasta e o nome do arquivo a ser lido

    @Parameterized.Parameters
    public static Collection<String[]> LerArquivo() throws IOException {
    return LerCSV("db/FTS128 Massa Iterasys.csv");
    }

    // 4 - Collection que lê um arquivo no formato CSV
    public static Collection<String[]> LerCSV(String nomeCSV) throws IOException {
        // Lè o arquivo no disco e disponibiliza na memória RAM
        BufferedReader arquivo = new BufferedReader(new FileReader(nomeCSV));
        String linha; // cria uma variavel linha
        List<String[]> dados = new ArrayList<>(); // cria uma lista para receber o resultado

        while ((linha = arquivo.readLine()) != null) {
            String[] campos = linha.split(";");
            dados.add(campos);
        }
        arquivo.close();
        return dados;
    }

    @BeforeClass
    public static void antesDeTudo(){
        url = "https://www.iterasys.com.br";
        System.setProperty("webdriver.chrome.driver","drivers/chrome/86/chromedriver.exe");
        System.setProperty("webdriver.edge.driver","drivers/edge/msedgedriver.exe");
        System.setProperty("webdriver.gecko.driver","drivers/firefox/geckodriver.exe");
        System.setProperty("webdriver.ie.driver","drivers/ie/IEDriverServer.exe");
        }

    @Before
    public void iniciar(){
        switch(browser){
            case "Chrome":
                driver = new ChromeDriver(); // <-- Instanciar o Selenium como um controlador do Chrome
                break;
            case "Edge":
                driver = new EdgeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            case "IE":
                driver = new InternetExplorerDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
    }

    @After
    public void finalizar(){
        driver.quit();
    }

    @Test
    public void consultarCurso() throws InterruptedException, IOException {
        driver.get(url);
        tirarPrint("Passo 1 - Acessou a Home");
        driver.findElement(By.id("searchtext")).sendKeys(curso + Keys.ENTER);
        //Thread.sleep(2000);
        tirarPrint("Passo 2 - Exibe os cursos relacionado a TestLink");
        Thread.sleep(15000);
        driver.findElement(By.cssSelector("span.comprar")).click();

        // Validar nome do curso
        String resultadoEsperado = curso;
        String resultadoAtual = driver.findElement(By.cssSelector("span.item-title")).getText();
        assertEquals(resultadoEsperado,resultadoAtual);

        tirarPrint("Passo 3 - Exibe o titulo, valor e parcelamento do curso");

        // Validar o preço
        assertEquals(valor,driver.findElement(By.cssSelector("span.new-price")).getText());

        // Validar o preço da direita
        assertEquals(subtotal, driver.findElement(By.cssSelector("div.subtotal")).getText());
        // Validar o valor das parcelas
        assertTrue( driver.findElement(By.cssSelector("div.ou-parcele")).getText().contains(parcelamento));

    }

    //@Test
    public void consultarMantis() throws InterruptedException {
        driver.get(url);
        driver.findElement(By.id("searchtext")).sendKeys("Mantis"+ Keys.ENTER);
        //Thread.sleep(2000);
        driver.findElement(By.cssSelector("span.comprar")).click();

        // Validar nome do curso
        String resultadoEsperado = "Mantis";
        String resultadoAtual = driver.findElement(By.cssSelector("span.item-title")).getText();
        assertEquals(resultadoEsperado,resultadoAtual);

        // Validar o preço
        assertEquals("R$ 49,99",driver.findElement(By.cssSelector("span.new-price")).getText());

        // Validar o preço da direita
        assertEquals("SUBTOTAL R$ 49,99", driver.findElement(By.cssSelector("div.subtotal")).getText());
        // Validar o valor das parcelas
        assertTrue( driver.findElement(By.cssSelector("div.ou-parcele")).getText().contains("ou em 12 x de R$ 5,02"));

    }

}

