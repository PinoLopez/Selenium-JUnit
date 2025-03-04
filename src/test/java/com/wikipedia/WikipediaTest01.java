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

public class WikipediaTest01
{

    @Test
    public void testSearchWikipedia() 
    {
        // Configurar el driver de Selenium 
        System.setProperty("webdriver.gecko.driver", "/home/agropecuario/geckodriver"); 

        // Inicializar el WebDriver 
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        try 
        {
            // Navegar a la página de Wikipedia
            driver.get("https://en.wikipedia.org/wiki/Mercedes-Benz_Sprinter");
            driver.manage().window().maximize();

            // Lista de modelos a buscar
            List<String> modelos = Arrays.asList("First generation (W901-W905)", "Second generation (W906)");
            List<String> colores = Arrays.asList("gold", "silver");
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            for (int i = 0; i < modelos.size(); i++) 
            {
                String modelo = modelos.get(i);
                String color = colores.get(i);

                // Buscar el encabezado del modelo
                WebElement modeloElement = driver.findElement(By.xpath("//span[contains(text(), '" + modelo + "')]") );
                
                // Asegurar que el elemento sea visible en la pantalla
                js.executeScript("window.scrollBy(0, -200); arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", modeloElement);
                Thread.sleep(2000);
                
                // Agregar fondo de color y borde al elemento para destacarlo
                js.executeScript("arguments[0].style.backgroundColor = arguments[1]; arguments[0].style.border = '3px solid red';", modeloElement, color);
                
                // Pequeña pausa para observar el efecto
                Thread.sleep(2000);
            }
            
            // Verificar que la página se ha cargado correctamente
            String title = driver.getTitle();
            assertTrue(title.contains("Mercedes-Benz Sprinter"));
            
            // Pausa final para observar el resultado antes de cerrar
            Thread.sleep(2000);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar el navegador
            driver.quit();
        }
    }
}
