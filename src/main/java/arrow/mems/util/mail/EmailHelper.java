package arrow.mems.util.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import arrow.framework.util.Instance;
import arrow.framework.util.i18n.Messages;
import arrow.framework.util.mail.ArrowMail;
import arrow.framework.util.mail.MailService;
import arrow.framework.util.mail.templating.freemarker.FreemarkerTemplate;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.persistence.entity.Users;
import freemarker.template.TemplateException;

@ApplicationScoped
public class EmailHelper {

  @Inject
  @ConfigProperty(name = "emailSubject")
  private String emailSubject;

  public static final String TEMPLATE_PARAMS_USER = "user";
  public static final String TEMPLATE_PARAMS_URL = "url";
  public static final String TEMPLATE_PARAMS_DATETIME = "datetime";
  public static final String TEMPLATES_RECOVER_PASSWORD_FTL = "arrow/templates/recoverPassword.ftl";
  public static final String TEMPLATES_APPROVAL_FOR_REQUESTOR_FTL = "arrow/templates/approvalForRequestor.ftl";
  public static final String TEMPLATES_APPROVAL_FOR_SUPERVISOR_FTL = "arrow/templates/approvalForSupervisor.ftl";

  public static void sendRecoverPassword(final Users accout, final String toAddress, final String url) throws IOException, TemplateException,
  MessagingException {
    final MailService mailService = Instance.get(MailService.class);
    final String subject = Messages.get("recover_password");
    final FreemarkerTemplate template =
        new FreemarkerTemplate(FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_RECOVER_PASSWORD_FTL, Locale.ENGLISH));
    final Map<String, Object> context = new HashMap<>();
    context.put(EmailHelper.TEMPLATE_PARAMS_USER, accout);
    context.put(EmailHelper.TEMPLATE_PARAMS_URL, url);

    final String content = template.merge(context);
    final ArrowMail mail = new ArrowMail(toAddress, subject, content);
    mailService.sendMail(mail);
  }

  public static void sendInfoApprovalForRequestor(final ApprovalSummary approvalInfo, final String toAddress) throws IOException, TemplateException,
  MessagingException {
    final MailService mailService = Instance.get(MailService.class);
    final String subject = Messages.get("approval_infomation");
    final FreemarkerTemplate template =
        new FreemarkerTemplate(FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_APPROVAL_FOR_REQUESTOR_FTL, Locale.ENGLISH));
    final Map<String, Object> context = new HashMap<>();

    final String content = template.merge(context);
    final ArrowMail mail = new ArrowMail(toAddress, subject, content);
    mailService.sendMail(mail);
  }

  public static void sendInfoApprovalForSupervisor(final ApprovalSummary approvalInfo, final String toAddress) throws IOException, TemplateException,
  MessagingException {
    final MailService mailService = Instance.get(MailService.class);
    final String subject = Messages.get("approval_infomation");
    final FreemarkerTemplate template =
        new FreemarkerTemplate(FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_APPROVAL_FOR_SUPERVISOR_FTL, Locale.ENGLISH));
    final Map<String, Object> context = new HashMap<>();

    final String content = template.merge(context);
    final ArrowMail mail = new ArrowMail(toAddress, subject, content);
    mailService.sendMail(mail);
  }

}
