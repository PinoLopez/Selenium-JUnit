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

public class WikipediaTest05 
{

    @Test
    public void testHighlightElementsOnLaFlechePage() 
    {
        // Configurar el driver de Selenium 
        System.setProperty("webdriver.gecko.driver", "/home/agropecuario/geckodriver"); 

        // Inicializar el WebDriver 
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        try 
        {
            // Navegar a la página de La Flèche en Wikipedia
            driver.get("https://en.wikipedia.org/wiki/La_Fl%C3%A8che");

            // Lista de elementos a buscar y sus colores de resaltado
            List<String> elementos = Arrays.asList("France", "Sarthe");
            List<String> colores = Arrays.asList("yellow", "cyan");
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            for (int i = 0; i < elementos.size(); i++) {
                String elemento = elementos.get(i);
                String color = colores.get(i);

                // Buscar el elemento en la página
                WebElement elementoEncontrado = driver.findElement(By.xpath("//a[contains(text(), '" + elemento + "')]") );
                
                // Hacer scroll hasta el elemento
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", elementoEncontrado);
                Thread.sleep(1000);
                
                // Resaltar el elemento con el color correspondiente
                js.executeScript("arguments[0].style.backgroundColor = arguments[1]; arguments[0].style.border = '3px solid black';", elementoEncontrado, color);
                
                // Pequeña pausa para visualizar el cambio
                Thread.sleep(2000);
            }
            
            // Verificar que la página se ha cargado correctamente
            String title = driver.getTitle();
            assertTrue(title.contains("La Flèche"));
            
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
