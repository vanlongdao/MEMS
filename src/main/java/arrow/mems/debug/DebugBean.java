package arrow.mems.debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.validator.ArrowValidator;
import arrow.mems.bean.AuthenticationBean;

@Named
@ViewScoped
public class DebugBean implements Serializable {
  private AuthenticationBean testValidateBean = new AuthenticationBean();
  private TestValidation bean = new TestValidation();

  private String message = "test";
  @Inject
  ArrowValidator validator;

  private String generatedKey;

  public String getGeneratedKey() {
    return this.generatedKey;
  }

  public void setGeneratedKey(String pGeneratedKey) {
    this.generatedKey = pGeneratedKey;
  }

  public void generateKey() {
    this.generatedKey = RandomStringUtils.random(32, "abcdefghyjklmnopqrstuvwxyz1234567890");
  }

  // NOTES: This method never be called if we use Annotation Constraints, so don't need to validate
  // in Service if your annotations are perfect.
  public void testValidate() {
    final List<Message> message = this.validator.validate(this.testValidateBean);
    message.get(0).show();
  }

  public AuthenticationBean getTestValidateBean() {
    return this.testValidateBean;
  }

  public void setTestValidateBean(AuthenticationBean pTestValidateBean) {
    this.testValidateBean = pTestValidateBean;
  }

  public TestValidation getBean() {
    return this.bean;
  }

  public void setBean(TestValidation pBean) {
    this.bean = pBean;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String pMessage) {
    this.message = pMessage;
  }

  private String[] listLevels = new String[] {"level 1", "level 2", "level 3"};

  public String[] getListLevels() {
    return this.listLevels;
  }

  public void setListLevels(String[] pListLevels) {
    this.listLevels = pListLevels;
  }

  private Map<String, List<String>> mapLevelAndSupervisors;

  public Map<String, List<String>> getMapLevelAndSupervisors() {
    if (this.mapLevelAndSupervisors == null) {
      this.mapLevelAndSupervisors = new HashMap<String, List<String>>();
      final List<String> level1 = new ArrayList<String>();
      level1.add("Ducva");
      this.mapLevelAndSupervisors.put("level 1", level1);
    }
    return this.mapLevelAndSupervisors;
  }

  public void setMapLevelAndSupervisors(Map<String, List<String>> pMapLevelAndSupervisors) {
    this.mapLevelAndSupervisors = pMapLevelAndSupervisors;
  }

  public void addSupervisor(String levelId) {
    List<String> supervisor = this.getMapLevelAndSupervisors().get(levelId);
    if (supervisor == null) {
      supervisor = new ArrayList<String>();
    }
    supervisor.add("New Officer");
    this.getMapLevelAndSupervisors().put(levelId, supervisor);
  }

  public List<String> getListSupervisor(String levelId) {
    return this.getMapLevelAndSupervisors().get(levelId);
  }

  public static class TestValidation {
    @NotNull
    private String field1;
    @NotNull
    private String field2;

    public String getField1() {
      return this.field1;
    }

    public void setField1(String pField1) {
      this.field1 = pField1;
    }

    public String getField2() {
      return this.field2;
    }

    public void setField2(String pField2) {
      this.field2 = pField2;
    }
  }

  public void fireException() {
    throw new NullPointerException();
  }
}
