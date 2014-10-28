package arrow.mems.util.mail;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.mail.MessagingException;
import javax.mail.Session;

import arrow.framework.util.mail.MailService;

@Singleton
public class ArrowMailService extends MailService {

  /**
   *
   */
  /**
   * <pre>
   * <mail-session name="businessTraceability" debug="true" jndi-name="java:jboss/mail/arrowMail" from="noreply@arrow-tech.vn">
   *                 <smtp-server outbound-socket-binding-ref="arrow-smtp" ssl="false" tls="true" username="noreply@arrow-tech.vn" password="tri2014&amp;"/>
   *             </mail-session>
   * </pre>
   *
   * <pre>
   * <outbound-socket-binding name="arrow-smtp">
   *             <remote-destination host="smtp-mail.outlook.com" port="587"/>
   *         </outbound-socket-binding>
   * </pre>
   */
  @Resource(lookup = "java:jboss/mail/arrowMail")
  private Session mailSession;

  @PostConstruct
  public void start() {

  }

  @Override
  public Session getMailSession() throws MessagingException {
    return this.mailSession;
  }
}
