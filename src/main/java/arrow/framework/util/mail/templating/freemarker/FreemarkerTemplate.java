package arrow.framework.util.mail.templating.freemarker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.jboss.weld.exceptions.IllegalArgumentException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTemplate implements Serializable {
  /**
   *
   */
  private final InputStream templateIn;

  public FreemarkerTemplate(final InputStream templateIn) {
    this.templateIn = templateIn;
  }

  public static InputStream loadTemplate(final String name, final Locale locale) {
    // put locale language (_ja, _vi) into name of resource.
    final int indexOfLastDot = name.lastIndexOf(".");
    if (indexOfLastDot > 0) {
      final String suffix = name.substring(indexOfLastDot);
      final String localeName = name.replace(suffix, "_" + locale.getLanguage() + suffix);
      return FreemarkerTemplate.class.getClassLoader().getResourceAsStream(localeName);
    } else
      throw new IllegalArgumentException("Template file is not correct");
  }

  public String merge(final Map<String, Object> context) throws IOException, TemplateException {

    final BufferedReader reader = new BufferedReader(new InputStreamReader(this.templateIn, "UTF-8"));
    final Template template = new Template("templateName", reader, new Configuration());
    final StringWriter sw = new StringWriter();
    template.process(context, sw);
    final String result = sw.toString();
    sw.close();

    return result;
  }

}
