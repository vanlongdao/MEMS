/**
 *
 */
package arrow.mems.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.CollectionUtils;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.service.CountryService;
import arrow.mems.util.string.ArrowStrUtils;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class CountryManagementBean extends AbstractBean {

  @Inject
  CountryService countryService;
  private List<MtCountry> listCountries;

  @PostConstruct
  public void init() {
    this.listCountries = this.countryService.getAllCountries();
  }

  public List<MtCountry> getListCountries() {
    if (this.listCountries == null) {
      this.listCountries = this.countryService.autoCompleteCountry(ArrowStrUtils.EMPTY_STRING, this.listCountries);
    }
    return this.listCountries;
  }

  public List<MtCountry> filterCountry(final String query) {
    if (StringUtils.isEmpty(query))
      return this.getListCountries();

    return CollectionUtils.filter(this.getListCountries(), object -> {
      final MtCountry item = (MtCountry) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || String.valueOf(item.getCountryId()).contains(query);
    });
  }
}
