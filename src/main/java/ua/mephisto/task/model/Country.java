package ua.mephisto.task.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity to hold country data.
 *
 * Created by mephisto on 27.10.15.
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue
    private long id;

    private String countryName;
    private String countryISOCode;
    @Embedded
    @OneToMany(mappedBy = "country",cascade = CascadeType.ALL)
    private List<City> cities = new ArrayList<City>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }

    public void setCountryISOCode(String countryISOCode) {
        this.countryISOCode = countryISOCode;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
