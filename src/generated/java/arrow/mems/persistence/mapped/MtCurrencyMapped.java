//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MtCurrencyMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MtCurrency> getEntityClass() {
    return arrow.mems.persistence.entity.MtCurrency.class;
  }
  
  //default constructor
  public MtCurrencyMapped() {
  }
  
  @Column(name="CCY_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mt_currency_seq_gen")
  @SequenceGenerator(name = "mt_currency_seq_gen", sequenceName = "mt_currency_ccy_id_seq", allocationSize=1)
  protected int ccyId;
  
  public int getCcyId() {
    return this.ccyId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.ccyId;
  }

  //Normal columns
  @Column(name="CCY_LOCAL_POST")
  protected java.lang.String ccyLocalPost;

  public java.lang.String getCcyLocalPost() {
    return this.ccyLocalPost;
  }

  public void setCcyLocalPost(java.lang.String ccyLocalPost) {
    this.ccyLocalPost = ccyLocalPost;
  }
  @Column(name="CCY_LOCAL_PRE")
  protected java.lang.String ccyLocalPre;

  public java.lang.String getCcyLocalPre() {
    return this.ccyLocalPre;
  }

  public void setCcyLocalPre(java.lang.String ccyLocalPre) {
    this.ccyLocalPre = ccyLocalPre;
  }
  @Column(name="CCY_NAME")
  protected java.lang.String ccyName;

  public java.lang.String getCcyName() {
    return this.ccyName;
  }

  public void setCcyName(java.lang.String ccyName) {
    this.ccyName = ccyName;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}