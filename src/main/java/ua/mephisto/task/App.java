package ua.mephisto.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.mephisto.task.bo.CountryBo;
import ua.mephisto.task.model.Country;

import java.io.IOException;
import java.util.List;

/**
 * Created by mephisto on 27.10.15.
 */
public class App {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/config/applicationContext.xml");
        HTTPUrlConnector con = (HTTPUrlConnector) applicationContext.getBean("connector");

        try {
            List<Country> countries = con.sendPost();
            CountryBo countryBo = (CountryBo)applicationContext.getBean("countryBo");
            //Save data
            for(Country country: countries){
                countryBo.save(country);
            }
            System.out.println("All is work");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
