package arrow.mems.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.resourceloader.InjectableResource;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;
import arrow.mems.constant.CommonConstants;
import arrow.mems.util.dialog.DialogUtil;

@Named
@ApplicationScoped
public class CommonBean extends AbstractBean {
  public java.util.Date convert(LocalDateTime dateTime) {
    Date.from(dateTime.toInstant(ZoneOffset.UTC));
    return DateUtils.convertJavaTimeToJavaDate(dateTime);
  }

  public java.util.Date convert(LocalDate date) {
    return DateUtils.convertLocalDateToDate(date);
  }

  public String getDefaultDateFormat() {
    return CommonConstants.DAY_MONTH_YEAR_FORMAT;
  }

  public String getDefaultTimeFormat() {
    return CommonConstants.DEFAULT_TIME_FORMAT;
  }

  public String getDefaultDateTimeFormat() {
    return CommonConstants.DEFAULT_DATETIME_FORMAT;
  }

  @Inject
  @InjectableResource(location = "rev.txt")
  private InputStream inputStream;

  public String getCurrentRevision() {
    if (StringUtils.isEmpty(this.currentRevision)) {
      try {
        this.currentRevision = IOUtils.toString(this.inputStream, "UTF8");
      } catch (final IOException e) {
        this.log.debug("error while reading rev.txt", e);
      }
    }
    return this.currentRevision;
  }

  public void setCurrentRevision(String pCurrentRevision) {
    this.currentRevision = pCurrentRevision;
  }

  private String currentRevision;

  public String encodeUrl(String url) {
    try {
      return URLEncoder.encode(url, "UTF8");
    } catch (final UnsupportedEncodingException e) {
      // ignore the exception
      return url;
    }
  }

  private String drawing_json;

  public String getDrawing_json() {
    return this.drawing_json;
  }

  public void setDrawing_json(String pDrawing_json) {
    this.drawing_json = pDrawing_json;
  }

  public void saveDrawingJson(String json) {
    DialogUtil.CloseDialog(json);
  }

  public void closeDrawing() {
    DialogUtil.CloseDialog();
  }
}
