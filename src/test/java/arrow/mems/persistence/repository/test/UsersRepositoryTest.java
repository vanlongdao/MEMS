package arrow.mems.persistence.repository.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.mems.persistence.repository.UsersRepository;

public class UsersRepositoryTest extends Arquillian {
  @Inject
  UsersRepository repo;

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testFindUserInOneOffice() {
    final String officeCode = "123";
    final List<Integer> userIds = this.repo.findUserInOneOffice(officeCode);
    Assert.assertNotNull(userIds);
  }
}
