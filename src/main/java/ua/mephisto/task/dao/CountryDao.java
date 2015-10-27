package ua.mephisto.task.dao;

import ua.mephisto.task.model.Country;

/**
 * Class to work with country object in db.
 * Created by mephisto on 27.10.15.
 */
public interface CountryDao {

    void save(Country country);
    void update(Country country);
    void delete(Country country);
}
