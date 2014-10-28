package arrow.framework.faces.util;

import javax.faces.model.SelectItem;

import arrow.framework.util.i18n.Messages;

public class LabelKeySelectItem extends SelectItem {
  String labelKey;

  public LabelKeySelectItem(final Object value, final String labelKey) {
    this.setValue(value);
    this.labelKey = labelKey;
  }

  @Override
  public String getLabel() {
    return Messages.get(this.labelKey);
  }

  public String getLabelKey() {
    return this.labelKey;
  }

  public void setLabelKey(final String labelKey) {
    this.labelKey = labelKey;
  }
}
