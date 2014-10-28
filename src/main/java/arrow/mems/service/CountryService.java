/**
 *
 */
package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arrow.framework.persistence.ArrowQuery;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.repository.MtCountryRepository;


/**
 * @author tainguyen
 *
 */
public class CountryService extends AbstractService {

  @Inject
  MtCountryRepository countryRepository;

  private List<MtCountry> allCountries;

  public List<MtCountry> getAllCountries() {
    if (this.allCountries == null) {
      this.allCountries = this.countryRepository.findAll();
    }
    return this.allCountries;
  }

  public List<MtCountry> autoCompleteCountry(String query, List<MtCountry> listAllCountries) {
    final List<MtCountry> suggestions = new ArrayList<MtCountry>();
    for (final MtCountry add : listAllCountries) {
      if (add.getName().startsWith(query)) {
        suggestions.add(add);
      }
    }
    return suggestions;
  }

  public List<MtCountry> getCountries() {
    final ArrowQuery<MtCountry> query = new ArrowQuery<MtCountry>(super.em);
    query.select("e").from("MtCountry e");
    query.orderBy("e.countryId");
    return query.getResultList();
  }
}
