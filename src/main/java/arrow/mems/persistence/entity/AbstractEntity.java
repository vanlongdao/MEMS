//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.util.Instance;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.mapped.AbstractEntityMapped;
import arrow.mems.persistence.repository.UsersRepository;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class AbstractEntity extends AbstractEntityMapped {

  public AbstractEntity() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  
  @Transient
  protected boolean selected;

  @Transient
  protected ArrowLogger logger;

  @Transient
  private boolean isPersisted;

  /**
   * This method will be used by BeanCopier.
   *
   * @return
   */
  public boolean getPersisted() {
    return this.isPersisted;
  }

  public boolean isPersisted() {
    return this.isPersisted;
  }

  public boolean isSelected() {
    return this.selected;
  }

  @PrePersist
  public void prePersist() {
    final Class<?> clazz = this.getClass();
    final int currentUserId = Instance.get(UserCredential.class).getUserId();
    try {
      final Object value = clazz.getMethod("getCreatedAt").invoke(this);
      if (value == null) {
        clazz.getMethod("setCreatedAt", LocalDateTime.class).invoke(this, LocalDateTime.now());
      }
      final Object createdBy = clazz.getMethod("getCreatedBy").invoke(this);
      if (((createdBy == null) || (Integer.valueOf(createdBy.toString()) <= 0)) && (currentUserId > 0)) {
        clazz.getMethod("setCreatedBy", int.class).invoke(this, currentUserId);
      }
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
      this.logger.debug("Error while set created_by, created_at", e);
    } catch (final NoSuchMethodException ex) {
      // ignore this. but keep logging
      this.logger.debug("Missing method name", ex);

    }
  }

  public void setPersisted(boolean pIsPersisted) {
    this.isPersisted = pIsPersisted;
  }

  public void setSelected(boolean pSelected) {
    this.selected = pSelected;
  }

  @PostLoad
  protected void postLoad() {
    this.isPersisted = true;
  }

  @PostPersist
  protected void postPersisted() {
    this.isPersisted = true;
  }

  @JsonIgnore
  public Users getCreatedByUser() {
    if (this.createdBy > 0)
      return Instance.get(UsersRepository.class).findBy(this.createdBy);
    return null;
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  