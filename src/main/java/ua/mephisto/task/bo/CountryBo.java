package ua.mephisto.task.bo;

import ua.mephisto.task.model.Country;

/**
 * Business logic
 * Created by mephisto on 27.10.15.
 */
public interface CountryBo {
    void save(Country country);
    void update(Country country);
    void delete(Country country);

}
