package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.PresetPhrases;
import arrow.mems.persistence.repository.PresetPhrasesRepository;
import arrow.mems.test.DeploymentManager;


public class PresetPhrasesManageServiceTest extends DeploymentManager {
  @Inject
  PresetPhrasesManagementService service;
  @Inject
  PresetPhrasesRepository repo;
  @Inject
  UserCredential userCredentail;
  @Inject
  AuthenticationService authenService;


  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testGetListPresetPhrases() {
    List<PresetPhrases> listPresetPhrases = new ArrayList<PresetPhrases>();
    listPresetPhrases = this.service.getListPresetPhrases();
    Assert.assertNotNull(listPresetPhrases);
    Assert.assertEquals(listPresetPhrases.size(), 1);
  }
}
