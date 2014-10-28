package arrow.framework.faces.component;

import javax.faces.component.FacesComponent;

import org.primefaces.component.datatable.DataTable;

import arrow.framework.util.i18n.Messages;
import arrow.mems.config.AppConfig;

@FacesComponent(ArrowDataTable.COMPONENT_TYPE)
public class ArrowDataTable extends DataTable {
  private static final int ROW_COUNT_PER_PAGE = 20;
  private static final String ROW_PER_PAGE_TEMPLATE = "10, 20, 30, 50";
  private static final String PAGINATOR_POSITION = "bottom";
  private static final String ROW_INDEX_VAR = "index";
  public static final String COMPONENT_TYPE = "arrow.framework.faces.component.ArrowDataTable";
  private static final String PAGINATOR_TEMPLATE =
      "{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";

  public ArrowDataTable() {
    super.setRows(AppConfig.getMaxRowsPerPage());
    super.setRowsPerPageTemplate(ArrowDataTable.ROW_PER_PAGE_TEMPLATE);
    super.setPaginatorPosition(ArrowDataTable.PAGINATOR_POSITION);
    super.setRowIndexVar(ArrowDataTable.ROW_INDEX_VAR);
    super.setPaginatorTemplate(ArrowDataTable.PAGINATOR_TEMPLATE);
    super.setPaginator(true);
    super.setPaginatorAlwaysVisible(false);
  }

  @Override
  public String getEmptyMessage() {
    return Messages.get("noResult");
  }
}
