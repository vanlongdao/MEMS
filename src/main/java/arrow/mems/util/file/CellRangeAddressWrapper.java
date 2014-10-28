package arrow.mems.util.file;

import org.apache.poi.ss.util.CellRangeAddress;

class CellRangeAddressWrapper implements Comparable<CellRangeAddressWrapper> {

  public CellRangeAddress range;

  /**
   * @param theRange the CellRangeAddress object to wrap.
   */
  public CellRangeAddressWrapper(CellRangeAddress theRange) {
    this.range = theRange;
  }

  /**
   * @param o the object to compare.
   * @return -1 the current instance is prior to the object in parameter, 0: equal, 1: after...
   */
  @Override
  public int compareTo(CellRangeAddressWrapper o) {

    if ((this.range.getFirstColumn() < o.range.getFirstColumn()) && (this.range.getFirstRow() < o.range.getFirstRow()))
      return -1;
    else if ((this.range.getFirstColumn() == o.range.getFirstColumn()) && (this.range.getFirstRow() == o.range.getFirstRow()))
      return 0;
    else
      return 1;

  }

}
