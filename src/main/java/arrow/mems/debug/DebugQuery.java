package arrow.mems.debug;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.bean.AbstractBean;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.Person;
import arrow.mems.service.CountryService;
import arrow.mems.service.PersonManagementService;

@Named
@ViewScoped
public class DebugQuery extends AbstractBean {

  @Inject
  CountryService service;

  @Inject
  PersonManagementService personService;

  private List<Person> listPersons;

  public List<Person> getListPersons() {
    if (this.listPersons == null) {
      this.listPersons = this.personService.getListPerson("123");
    }
    return this.listPersons;
  }

  public void setListPersons(List<Person> pListPersons) {
    this.listPersons = pListPersons;
  }

  private List<MtCountry> list;

  public List<MtCountry> getListCountries() {
    if (this.list == null) {
      this.list = this.service.getCountries();
    }
    return this.list;
  }

  public void getList() {
    this.getListCountries();
  }

  public void tryToGetException() {
    // we got exception when try to access element from the 21st one.
    for (int index = 0; index < 22; index++) {
      this.getListCountries().get(index);
    }
  }
}
