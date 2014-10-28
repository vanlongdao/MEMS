package arrow.framework.util.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;

public class ArrayUtils {
  public static boolean isEmpty(Object[] objects) {
    return (objects == null) || (objects.length == 0);
  }

  public static boolean isNotEmpty(Object[] objects) {
    return !ArrayUtils.isEmpty(objects);
  }

  @SafeVarargs
  public static <T> Set<T> asSet(final T... array) {
    final Set<T> result = new HashSet<T>();
    for (final T a : array) {
      result.add(a);
    }

    return result;
  }

  public static ArrayList<List<MdevChecklistItem>> initArray(int sizeArray) {
    final ArrayList<List<MdevChecklistItem>> newArray = new ArrayList<>();
    final List<MdevChecklistItem> e = null;
    for (int i = 0; i < sizeArray; i++) {
      newArray.add(e);
    }
    return newArray;
  }

  public static ArrayList<List<MdevChecklist>> initArrayChecklist(int sizeArray) {
    final ArrayList<List<MdevChecklist>> newArray = new ArrayList<>();
    final List<MdevChecklist> e = null;
    for (int i = 0; i < sizeArray; i++) {
      newArray.add(e);
    }
    return newArray;
  }
}
