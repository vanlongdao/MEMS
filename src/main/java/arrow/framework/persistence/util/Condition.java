package arrow.framework.persistence.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Condition {
  private final String condition;
  private List<Object> parameters;

  private Condition() {
    this.condition = "";
    this.setParameters(new ArrayList<Object>());
  }

  public Condition(final String condition, final List<Object> params) {
    this.condition = condition;
    this.setParameters(params == null ? new ArrayList<Object>() : params);
    this.validate();
  }

  public Condition(final String condition, final Object... params) {
    this(condition, Arrays.asList(params));
  }

  public List<Object> getParameters() {
    return this.parameters;
  }

  public String build() {
    return "(" + this.condition + ")";
  }

  private void validate() {
    if (this.condition != null) {
      final int qmCount = this.countUnquotedQuestionMarks(this.condition);
      final int paramCount = this.getParameters() == null ? 0 : this.getParameters().size();
      if (qmCount != paramCount) {
        throw new IllegalStateException("Number of ? does not equal number of parameters");
      }
    }
  }

  private int countUnquotedQuestionMarks(final String string) {
    boolean search = true;
    int count = 0;

    for (int i = 0; i < string.length(); i++) {
      final char charAt = string.charAt(i);
      if (charAt == '\'') {
        search = !search;
      }
      if (search && (charAt == '?')) {
        count++;
      }
    }
    return count;
  }

  public boolean isEmpty() {
    return ((this.getParameters() == null) || (this.getParameters().size() == 0))
        && (((this.condition != null) && (this.condition.trim().length() == 0)));
  }

  public void setParameters(final List<Object> parameters) {
    this.parameters = parameters;
  }

  public static class Junction extends Condition {
    private String joiner;
    private final List<Condition> conditions = new ArrayList<Condition>();

    @Override
    public boolean isEmpty() {
      for (final Condition condition : this.conditions) {
        if (!condition.isEmpty()) {
          return false;
        }
      }
      return true;
    }

    @Override
    public String build() {
      final StringBuffer buffer = new StringBuffer();

      for (final Condition condition : this.conditions) {
        if (!condition.isEmpty()) {
          if (buffer.length() != 0) {
            buffer.append(this.getJoiner());
          }
          buffer.append(condition.build());
        }
      }

      return "(" + buffer.toString() + ")";
    }

    public Junction add(final Condition condition) {
      if (condition != null) {
        this.conditions.add(condition);
        this.getParameters().addAll(condition.getParameters());
      }

      return this;
    }

    public Junction add(final String condition, final Object... params) {
      return this.add(new Condition(condition, params));
    }

    public String getJoiner() {
      return joiner;
    }

    public void setJoiner(String joiner) {
      this.joiner = joiner;
    }
  }

  public static class Conjunction extends Junction {
    public Conjunction() {
      this.setJoiner(" AND ");
    }
  }

  public static class Disjunction extends Junction {
    public Disjunction() {
      this.setJoiner(" OR ");
    }
  }
}
