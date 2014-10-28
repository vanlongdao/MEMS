package arrow.framework.util.collections;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface SelectableList<E> extends List<E> {
  public void selectAll();

  public void deselectAll();

  public void select(E element, boolean selected);

  public SortedSet<Integer> getSelectedIndexes();

  public Map<E, Boolean> getElementSelectionMap();

  public Map<Integer, Boolean> getIndexSelectionMap();
}
