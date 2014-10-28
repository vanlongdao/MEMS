package arrow.framework.persistence;

import java.util.ArrayList;
import java.util.List;

public class StatementList {
  private final List<String> statements = new ArrayList<String>();

  private String prefix;

  public void setPrefix(final String prefix) {
    this.prefix = prefix;
  }

  public String getPrefix() {
    return this.prefix;
  }

  public StatementList() {}

  public StatementList(final String prefix) {
    this.setPrefix(prefix);
  }

  public String build() {
    final StringBuilder builder = new StringBuilder();
    if ((this.prefix != null) && (this.statements.size() > 0)) {
      builder.append(this.prefix);
    }

    for (int i = 0; i < this.statements.size(); i++) {
      if (i > 0) {
        builder.append(this.separator);
      }
      builder.append(this.statements.get(i));
    }

    return builder.toString();
  }

  private String separator = " ";

  public void setSeparator(final String separator) {
    this.separator = separator;
  }

  public String getSeparator() {
    return this.separator;
  }

  public void add(final String statement) {
    if ((statement == null) || (statement.trim().length() == 0)) {
      throw new IllegalArgumentException("Argument is empty");
    }
    this.statements.add(statement);
  }
}
