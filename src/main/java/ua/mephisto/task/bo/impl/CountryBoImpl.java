package ua.mephisto.task.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mephisto.task.bo.CountryBo;
import ua.mephisto.task.dao.CountryDao;
import ua.mephisto.task.model.Country;

/**
 * Created by mephisto on 27.10.15.
 */
@Service("countryBo")
public class CountryBoImpl implements CountryBo {
    @Autowired
    private CountryDao countryDao;

    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public void save(Country country) {
        countryDao.save(country);
    }

    public void update(Country country) {
        countryDao.update(country);
    }

    public void delete(Country country) {
        countryDao.delete(country);

    }
}
