package ua.mephisto.task.model;

import javax.persistence.*;

/**
 * Entity to hold data about city.
 *
 * Created by mephisto on 27.10.15.
 */
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue
    private long id;

    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
