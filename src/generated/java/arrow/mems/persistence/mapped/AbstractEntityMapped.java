//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class AbstractEntityMapped extends arrow.framework.persistence.ArrowEntity {

  //Normal columns
  @Column(name="CREATED_AT")
  protected java.time.LocalDateTime createdAt;

  public java.time.LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(java.time.LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  @Column(name="CREATED_BY")
  protected int createdBy;

  public int getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }
  @Version
  @Column(name="OBJECT_VERSION")
  protected java.lang.Integer objectVersion;

  public java.lang.Integer getObjectVersion() {
    return this.objectVersion;
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}