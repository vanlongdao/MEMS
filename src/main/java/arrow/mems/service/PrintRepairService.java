package arrow.mems.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import arrow.framework.util.mail.templating.freemarker.FreemarkerTemplate;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.util.file.ExportFile;

@Named
public class PrintRepairService extends AbstractService {

  public void exportToExcel(Map<String, Object> content, List<ReplPart> listReplPart, List<PartOrder> listPartOrder, List<Checklist> listChecklist,
      List<PartOrderItem> listPartOrderItem) throws FileNotFoundException, IOException {
    final InputStream is = FreemarkerTemplate.loadTemplate(ExportFile.TEMPLATES_PRINTED_REPAIR_REQUEST, Locale.ENGLISH);
    // final FileInputStream fis = new FileInputStream(new
    // File(ExportFile.TEMPLATES_PRINTED_REPAIR));
    final XSSFWorkbook wb = new XSSFWorkbook(is);

    final XSSFSheet sheetMain = wb.getSheet("main_sheet");
    final XSSFSheet sheetComment = wb.getSheet("sheet_comment");
    final XSSFSheet sheetEnd = wb.getSheet("sheet_end");
    // Dynamic sheet
    final XSSFSheet sheetPartOrder = wb.getSheet("sheet_repair");
    final XSSFSheet sheetReplPart = wb.getSheet("list_devices");
    final XSSFSheet sheetDeviceMeasure = wb.getSheet("device_measure");

    // Fill data into first sheet has not dynamic data
    ExportFile.fillDataToStaticForm(wb, content, sheetMain);
    ExportFile.fillDataToStaticForm(wb, content, sheetComment);
    ExportFile.fillDataToStaticForm(wb, content, sheetEnd);

    // Fill data into dynamic sheet "list_devices"
    ExportFile.fillDatabByReplPart(wb, sheetReplPart, listReplPart);
    // Fill data into dynamic sheet "sheet_repair"
    ExportFile.fillDatabByPartOrder(wb, sheetPartOrder, listPartOrder);
    // Fill data into dynamic sheet "device_measure"
    ExportFile.fillDatabByChecklist(wb, sheetDeviceMeasure, listChecklist);

    ExportFile.copySheets(sheetMain, sheetReplPart, true);
    ExportFile.copySheets(sheetMain, sheetComment, true);
    ExportFile.copySheets(sheetMain, sheetDeviceMeasure, true);
    ExportFile.copySheets(sheetMain, sheetPartOrder, true);
    ExportFile.copySheets(sheetMain, sheetEnd, true);

    // Delete sheet that be use temp
    ExportFile.deleteSheet(wb, sheetReplPart);
    ExportFile.deleteSheet(wb, sheetComment);
    ExportFile.deleteSheet(wb, sheetDeviceMeasure);
    ExportFile.deleteSheet(wb, sheetEnd);
    ExportFile.deleteSheet(wb, sheetPartOrder);

    is.close();
    PrintRepairService.sendFileToClient(ExportFile.OUTPUT_FILE_PRINT_REPAIR_REQUEST, wb);
    // fos.close();
  }

  public static void sendFileToClient(final String fileOutputName, final Workbook fileExport) throws IOException {
    final FacesContext fc = FacesContext.getCurrentInstance();
    final ExternalContext ec = fc.getExternalContext();
    ec.setResponseContentType("application/ms-excel; charset=UTF-8");
    ec.setResponseCharacterEncoding("UTF-8");
    ec.setResponseHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileOutputName, "UTF-8"));
    final OutputStream output = ec.getResponseOutputStream();
    fileExport.write(output);
    output.flush();
    output.close();

    // force to complete response phase
    fc.responseComplete();
  }
}
