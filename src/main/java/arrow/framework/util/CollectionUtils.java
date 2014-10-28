package arrow.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ObjectUtils;

public class CollectionUtils {
  public static boolean isEmpty(final Collection<?> collection) {
    return (collection == null) || (collection.size() == 0);
  }

  public static boolean isNotEmpty(final Collection<?> collection) {
    return !CollectionUtils.isEmpty(collection);
  }

  public static <T> List<T> copy(final List<T> list) {
    final List<T> result = new ArrayList<T>();
    result.addAll(list);
    return result;
  }

  /**
   * Compare Collections: considered equal if they are of the same size, contain the same members in
   * the same iteration order.
   *
   * @param a
   * @param b
   * @return
   */
  public static boolean equal(final Collection<?> a, final Collection<?> b) {
    if (a == b) {
      return true;
    }

    if ((a == null) ^ (b == null)) {
      return false;
    }

    if (a.equals(b)) {
      return true;
    }

    if (a.size() != b.size()) {
      return false;
    }

    final Iterator<?> ib = b.iterator();
    for (final Iterator<?> ia = a.iterator(); ia.hasNext();) {
      if (!ObjectUtils.equals(ia.next(), ib.next())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Compare Maps: considered equal if they are of the same size, and contain the same key-value
   * mappings, regardless of iteration order
   *
   * @param a
   * @param b
   * @return
   */
  public static boolean equal(final Map<?, ?> a, final Map<?, ?> b) {
    if (a == b) {
      return true;
    }

    if ((a == null) ^ (b == null)) {
      return false;
    }

    if (a.equals(b)) {
      return true;
    }

    if (a.size() != b.size()) {
      return false;
    }

    for (final Entry<?, ?> entry : a.entrySet()) {
      if (!ObjectUtils.equals(entry.getValue(), b.get(entry.getKey()))) {
        return false;
      };
    }
    return true;
  }

  /**
   * Filter source list by filterPredicate
   *
   * @param srcList
   * @param filterPredicate
   * @return resultList (Copy item from source List)
   */
  public static <T> List<T> filter(final List<T> srcList, final Predicate filterPredicate) {
    final List<T> temp = CollectionUtils.copy(srcList);
    org.apache.commons.collections.CollectionUtils.filter(temp, filterPredicate);
    return temp;
  }
}
