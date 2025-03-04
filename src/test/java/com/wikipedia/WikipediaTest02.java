package com.wikipedia;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class WikipediaTest02 
{

    @Test
    public void testSearchRolandCorporation() 
    {
        // Configurar el driver de Selenium 
        System.setProperty("webdriver.gecko.driver", "/home/agropecuario/geckodriver"); 

        // Inicializar el WebDriver 
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        try {
            // Navegar a la página principal de Wikipedia
            driver.get("https://es.wikipedia.org/wiki/Wikipedia:Portada");
            driver.manage().window().maximize();

            // Buscar "Roland Corporation"
            WebElement searchInput = driver.findElement(By.name("search"));
            searchInput.sendKeys("Roland Corporation");
            searchInput.submit();
            
            // Esperar la carga de la página
            Thread.sleep(3000);

            // Lista de modelos a buscar y colores
            List<String> modelos = Arrays.asList("TB-303", "TR-909");
            List<String> colores = Arrays.asList("red", "blue");
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            for (int i = 0; i < modelos.size(); i++) {
                String modelo = modelos.get(i);
                String color = colores.get(i);

                // Buscar el elemento del modelo
                WebElement modeloElement = driver.findElement(By.xpath("//a[contains(text(), '" + modelo + "')]") );
                
                // Hacer scroll hasta el elemento
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", modeloElement);
                Thread.sleep(2000);
                
                // Resaltar el elemento en color correspondiente
                js.executeScript("arguments[0].style.backgroundColor = arguments[1]; arguments[0].style.border = '3px solid black';", modeloElement, color);
                
                // Pequeña pausa para visualizar el cambio
                Thread.sleep(2000);
            }
            
            // Verificar que la página se ha cargado correctamente
            String title = driver.getTitle();
            assertTrue(title.contains("Roland Corporation"));
            
            // Pausa final para observar el resultado antes de cerrar
            Thread.sleep(3000);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar el navegador
            driver.quit();
        }
    }
}
