package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.helper.ServiceResult;
import arrow.mems.persistence.entity.RoleMaster;
import arrow.mems.persistence.repository.UsersRepository;

@Named
public class UserService extends AbstractService {

  @Inject
  protected UsersRepository repo;

  /**
   * This is to use EntityManager
   * The reason of example to present for people that use entityManager to action on DB same the old
   * system (business_traceability)
   * You can use method of entityManager : merge , find , persist , remove .v.v.
   * */
  public ServiceResult<RoleMaster> updateUserInfoByEm(RoleMaster roleMaster) {
    // Find the user by entityManager
    roleMaster = super.em.find(RoleMaster.class, roleMaster.getRoleId());
    // Update properties
    this.em.merge(roleMaster);
    return new ServiceResult<RoleMaster>(true, roleMaster);
  }

  public void addThemeToUser(String theme, int userId) {
    this.repo.addThemeToUser(theme, userId);
  }

  public List<Integer> findUserInOneOffice(String officeCode) {
    return this.repo.findUserInOneOffice(officeCode);
  }
}
