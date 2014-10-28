package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.mems.persistence.entity.MtCountry;

public class CountryServiceTest extends Arquillian {
  @Inject
  CountryService countryService;

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetAllCountries() throws Exception {
    final List<MtCountry> listMtCountries = this.countryService.getAllCountries();
    Assert.assertNotNull(listMtCountries);
    Assert.assertEquals(listMtCountries.size(), 250);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testAutoCompleteCountry() throws Exception {
    final List<MtCountry> listMtCountries = this.countryService.autoCompleteCountry("an", this.countryService.getAllCountries());

    Assert.assertNotNull(listMtCountries);

    boolean flag = true;
    for (final MtCountry country : listMtCountries) {
      if (!country.getName().contains("an")) {
        flag = false;
      }
    }
    Assert.assertTrue(flag);
  }

}
