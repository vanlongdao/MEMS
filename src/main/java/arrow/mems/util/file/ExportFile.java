package arrow.mems.util.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import arrow.framework.util.CollectionUtils;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.util.string.ArrowStrUtils;

public class ExportFile {
  public static final String TEMPLATES_PRINTED_REPAIR_REQUEST = "arrow/templates/printRepair.xlsx";
  public static final String OUTPUT_FILE_PRINT_REPAIR_REQUEST = "Repair_request.xlsx";

  public static void fillDataToStaticForm(XSSFWorkbook wb, Map<String, Object> content, XSSFSheet sheet) {
    // Fill data into first sheet has not dynamic data
    final int worksheetSize = sheet.getLastRowNum();
    for (int index = 0; index <= worksheetSize; index++) {
      final int colSize = sheet.getRow(index).getLastCellNum();
      for (int indexCol = 0; indexCol < colSize; indexCol++) {

        if (sheet.getRow(index) == null) {
          break;
        }
        final String keyCol = sheet.getRow(index).getCell(indexCol).getStringCellValue();
        if (content.containsKey(keyCol)) {
          if (CommonConstants.PRINT_REPAIR_IMAGE_FILE.equalsIgnoreCase(keyCol)) {
            final byte[] image = (byte[]) content.get(keyCol);
            if (image == null) {
              sheet.getRow(index).getCell(indexCol).setCellValue("");
            } else {
              ExportFile.insertImageIntoCell(wb, sheet, image, index, indexCol);
            }
            continue;
          }
          if (content.get(keyCol) == null) {
            sheet.getRow(index).getCell(indexCol).setCellValue("");
            continue;
          }
          if (ArrowStrUtils.isEmpty(content.get(keyCol).toString())) {
            sheet.getRow(index).getCell(indexCol).setCellValue("");
          } else {
            sheet.getRow(index).getCell(indexCol).setCellValue(content.get(keyCol).toString());
          }
        } else if (CommonConstants.buildMapPrintRepairContent().containsKey(keyCol)) {
          if (CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE.equalsIgnoreCase(keyCol) || CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE_MEASURE
              .equalsIgnoreCase(keyCol)) {
            continue;
          }
          sheet.getRow(index).getCell(indexCol).setCellValue("");
        }
      }

    }
  }

  public static void fillDatabByReplPart(XSSFWorkbook wb, XSSFSheet replPartSheet, List<ReplPart> listReplPart) {
    // Add fill data into dynamic sheet "list_devices"
    if (CollectionUtils.isNotEmpty(listReplPart)) {
      int indexReplPart = 0;
      for (final ReplPart replPart : listReplPart) {
        // First copy row
        ExportFile.copyRow(wb, replPartSheet, indexReplPart, indexReplPart + 1);
        final int rowSize = replPartSheet.getLastRowNum();
        for (int i = 0; i < rowSize; i++) {
          final int colSize = replPartSheet.getRow(i).getLastCellNum();

          for (int j = 0; j < colSize; j++) {
            if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PRODUCT_NUMBER)) {
              if (replPart.getRemoveDevice() == null) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(replPart.getRemoveDevice().getSerialNo())) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
              } else {
                replPartSheet.getRow(i).getCell(j).setCellValue(replPart.getRemoveDevice().getSerialNo());
              }
            }

            if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_EXPIRATION_DATE)) {
              if (replPart.getRemoveDevice() == null) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(replPart.getRemoveDevice().getSerialNo())) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
              } else {
                replPartSheet.getRow(i).getCell(j).setCellValue(replPart.getRemoveDevice().getExpireDate().toString());
              }
            }

            if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_DEVICE_NUMBER)) {
              if (replPart.getRemoveDevice() == null) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(replPart.getRemoveDevice().getDevCode())) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
              } else {
                replPartSheet.getRow(i).getCell(j).setCellValue(replPart.getRemoveDevice().getDevCode());
              }
            }

            if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PRODUCT_NAME)) {
              if (replPart.getRemoveDevice() == null) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (replPart.getRemoveDevice().getMDevice() == null) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(replPart.getRemoveDevice().getMDevice().getName())) {
                replPartSheet.getRow(i).getCell(j).setCellValue("");
              } else {
                replPartSheet.getRow(i).getCell(j).setCellValue(replPart.getRemoveDevice().getMDevice().getName());
              }
            }

          }
        }
        indexReplPart++;
      }
      // Remove last row is copied
      replPartSheet.removeRow(replPartSheet.getRow(replPartSheet.getLastRowNum()));
    } else {
      final int rowSize = replPartSheet.getLastRowNum();
      for (int i = 0; i <= rowSize; i++) {
        final int colSize = replPartSheet.getRow(i).getLastCellNum();
        if (replPartSheet.getRow(i) == null) {
          continue;
        }
        for (int j = 0; j < colSize; j++) {
          if (replPartSheet.getRow(i).getCell(j) == null) {
            continue;
          }
          if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PRODUCT_NUMBER)) {
            replPartSheet.getRow(i).getCell(j).setCellValue("");
          }
          if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_EXPIRATION_DATE)) {
            replPartSheet.getRow(i).getCell(j).setCellValue("");
          }
          if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_DEVICE_NUMBER)) {
            replPartSheet.getRow(i).getCell(j).setCellValue("");
          }
          if (replPartSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PRODUCT_NAME)) {
            replPartSheet.getRow(i).getCell(j).setCellValue("");
          }
        }
      }
      // Remove last row is copied
      // replPartSheet.removeRow(replPartSheet.getRow(replPartSheet.getLastRowNum()));
      // replPartSheet.createRow(rowSize);
    }
  }

  public static void fillDatabByChecklist(XSSFWorkbook wb, XSSFSheet sheetDeviceMeasure, List<Checklist> listChecklist) {
    // Add fill data into dynamic sheet "list_devices"
    if (CollectionUtils.isNotEmpty(listChecklist)) {
      int indexChecklist = 1;
      for (final Checklist cklist : listChecklist) {
        // First copy row
        ExportFile.copyRow(wb, sheetDeviceMeasure, indexChecklist, indexChecklist + 1);
        final int rowSize = sheetDeviceMeasure.getLastRowNum();
        for (int i = 0; i < rowSize; i++) {
          if (sheetDeviceMeasure.getRow(i) == null) {
            continue;
          }
          final int colSize = sheetDeviceMeasure.getRow(i).getLastCellNum();
          for (int j = 0; j < colSize; j++) {
            if (sheetDeviceMeasure.getRow(i).getCell(j) == null) {
              continue;
            }
            if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE1)) {
              if (cklist.getMeasurement1() == null) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (cklist.getMeasurement1().getMDevice() == null) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(cklist.getMeasurement1().getMDevice().getName())) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
              } else {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue(cklist.getMeasurement1().getMDevice().getName());
              }
            }

            if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE2)) {
              if (cklist.getMeasurement2() == null) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
                continue;
              }

              if (cklist.getMeasurement2().getMDevice() == null) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(cklist.getMeasurement2().getMDevice().getName())) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
              } else {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue(cklist.getMeasurement2().getMDevice().getName());
              }
            }

            if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_SERIAL_NO1)) {
              if (cklist.getMeasurement1() == null) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(cklist.getMeasurement1().getSerialNo())) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
              } else {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue(cklist.getMeasurement1().getSerialNo());
              }
            }

            if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_SERIAL_NO2)) {
              if (cklist.getMeasurement2() == null) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
                continue;
              }
              if (ArrowStrUtils.isEmpty(cklist.getMeasurement2().getSerialNo())) {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
              } else {
                sheetDeviceMeasure.getRow(i).getCell(j).setCellValue(cklist.getMeasurement2().getSerialNo());
              }
            }
          }
        }
        indexChecklist++;
      }
      // Remove last row is copied
      sheetDeviceMeasure.removeRow(sheetDeviceMeasure.getRow(sheetDeviceMeasure.getLastRowNum()));
    } else {
      final int rowSize = sheetDeviceMeasure.getLastRowNum();
      for (int i = 0; i <= rowSize; i++) {
        final int colSize = sheetDeviceMeasure.getRow(i).getLastCellNum();
        if (sheetDeviceMeasure.getRow(i) == null) {
          continue;
        }
        for (int j = 0; j < colSize; j++) {
          if (sheetDeviceMeasure.getRow(i).getCell(j) == null) {
            continue;
          }
          if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE1)) {
            sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
          }
          if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_DEVICE_NAME_MEASURE2)) {
            sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
          }
          if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_SERIAL_NO1)) {
            sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
          }
          if (sheetDeviceMeasure.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_SERIAL_NO2)) {
            sheetDeviceMeasure.getRow(i).getCell(j).setCellValue("");
          }
        }
      }
      // Remove last row is copied
      sheetDeviceMeasure.removeRow(sheetDeviceMeasure.getRow(sheetDeviceMeasure.getLastRowNum()));
      sheetDeviceMeasure.createRow(rowSize);
    }
  }

  public static void fillDatabByPartOrder(XSSFWorkbook wb, XSSFSheet sheetPartOrder, List<PartOrder> listPartOrder) {
    // Add fill data into dynamic sheet "list_devices"
    // if (CollectionUtils.isNotEmpty(listPartOrder)) {
    // int indexReplPart = 1;
    // for (final PartOrder part : listPartOrder) {
    // // First copy row
    // ExportFile.copyRow(wb, sheetPartOrder, indexReplPart, indexReplPart + 1);
    // final int rowSize = sheetPartOrder.getLastRowNum();
    // for (int i = 0; i < rowSize; i++) {
    // final int colSize = sheetPartOrder.getRow(i).getLastCellNum();
    // for (int j = 0; j < colSize; j++) {
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PARTORDER_NAME))
    // {
    // if (part.getPartOrderItem() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // continue;
    // }
    // if (part.getPartOrderItem().getMDevice() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // }
    // if (ArrowStrUtils.isEmpty(part.getPartOrderItem().getMDevice().getName())) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // } else {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getPartOrderItem().getMDevice().getName());
    // }
    // }
    //
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_CATEGORY_NAME))
    // {
    // if (part.getPartOrderItem() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // continue;
    // }
    // if (part.getPartOrderItem().getMDevice() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // }
    // if (ArrowStrUtils.isEmpty(part.getPartOrderItem().getMDevice().getCatName())) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // } else {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getPartOrderItem().getMDevice().getCatName());
    // }
    // }
    //
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_ITEM_PRICE))
    // {
    // if (part.getPartOrderItem() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // continue;
    // }
    // sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getPartOrderItem().getItemPrice().toString());
    // }
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_ITEM))
    // {
    // if (part.getPartOrderItem() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // continue;
    // }
    // sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getPartOrderItem().getNumItems().toString());
    // }
    //
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_PRICE))
    // {
    // if (part.getPartOrderItem() == null) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // continue;
    // }
    // if ((part.getPartOrderItem().getItemPrice() == null) ||
    // (part.getPartOrderItem().getNumItems() == null)) {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // continue;
    // }
    // final Double totalPrice = part.getPartOrderItem().getItemPrice() *
    // part.getPartOrderItem().getNumItems();
    // sheetPartOrder.getRow(i).getCell(j).setCellValue(String.format("%.3f", totalPrice));
    // }
    // }
    // }
    // indexReplPart++;
    // }
    // // Remove last row is copied
    // sheetPartOrder.removeRow(sheetPartOrder.getRow(sheetPartOrder.getLastRowNum()));
    // } else {
    // final int rowSize = sheetPartOrder.getLastRowNum();
    // for (int i = 0; i <= rowSize; i++) {
    // final int colSize = sheetPartOrder.getRow(i).getLastCellNum();
    // if (sheetPartOrder.getRow(i) == null) {
    // continue;
    // }
    // for (int j = 0; j < colSize; j++) {
    // if (sheetPartOrder.getRow(i).getCell(j) == null) {
    // continue;
    // }
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PARTORDER_NAME))
    // {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // }
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_CATEGORY_NAME))
    // {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("");
    // }
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_ITEM_PRICE))
    // {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // }
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_ITEM))
    // {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // }
    // if
    // (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_PRICE))
    // {
    // sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
    // }
    // }
    // }
    // // Remove last row is copied
    // sheetPartOrder.removeRow(sheetPartOrder.getRow(sheetPartOrder.getLastRowNum()));
    // sheetPartOrder.createRow(rowSize);
    // }
  }

  public static void fillDatabByPartOrderItem(XSSFWorkbook wb, XSSFSheet sheetPartOrder, List<PartOrderItem> listPartOrderItem) {
    // Add fill data into dynamic sheet "list_devices"
    if (CollectionUtils.isNotEmpty(listPartOrderItem)) {
      int indexReplPart = 1;
      for (final PartOrderItem part : listPartOrderItem) {
        // First copy row
        ExportFile.copyRow(wb, sheetPartOrder, indexReplPart, indexReplPart + 1);
        final int rowSize = sheetPartOrder.getLastRowNum();
        for (int i = 0; i < rowSize; i++) {
          final int colSize = sheetPartOrder.getRow(i).getLastCellNum();
          for (int j = 0; j < colSize; j++) {
            if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PARTORDER_NAME)) {
              if (part.getMDevice() == null) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("");
              }
              if (ArrowStrUtils.isEmpty(part.getMDevice().getName())) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("");
              } else {
                sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getMDevice().getName());
              }
            }

            if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_CATEGORY_NAME)) {

              if (part.getMDevice() == null) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("");
              }
              if (ArrowStrUtils.isEmpty(part.getMDevice().getCatName())) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("");
              } else {
                sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getMDevice().getCatName());
              }
            }

            if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_ITEM_PRICE)) {
              if (part.getItemPrice() == null) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
                continue;
              } else {
                sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getItemPrice().toString());
              }
            }
            if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_ITEM)) {
              if (part.getNumItems() == null) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
                continue;
              }
              sheetPartOrder.getRow(i).getCell(j).setCellValue(part.getNumItems().toString());
            }

            if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_PRICE)) {

              if ((part.getItemPrice() == null) || (part.getNumItems() == null)) {
                sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
                continue;
              }
              final Double totalPrice = part.getItemPrice() * part.getNumItems();
              sheetPartOrder.getRow(i).getCell(j).setCellValue(String.format("%.3f", totalPrice));
            }
          }
        }
        indexReplPart++;
      }
      // Remove last row is copied
      sheetPartOrder.removeRow(sheetPartOrder.getRow(sheetPartOrder.getLastRowNum()));
    } else {
      final int rowSize = sheetPartOrder.getLastRowNum();
      for (int i = 0; i <= rowSize; i++) {
        final int colSize = sheetPartOrder.getRow(i).getLastCellNum();
        if (sheetPartOrder.getRow(i) == null) {
          continue;
        }
        for (int j = 0; j < colSize; j++) {
          if (sheetPartOrder.getRow(i).getCell(j) == null) {
            continue;
          }
          if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_PARTORDER_NAME)) {
            sheetPartOrder.getRow(i).getCell(j).setCellValue("");
          }
          if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_CATEGORY_NAME)) {
            sheetPartOrder.getRow(i).getCell(j).setCellValue("");
          }
          if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_ITEM_PRICE)) {
            sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
          }
          if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_ITEM)) {
            sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
          }
          if (sheetPartOrder.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(CommonConstants.PRINT_REPAIR_TOTAL_PRICE)) {
            sheetPartOrder.getRow(i).getCell(j).setCellValue("0.0");
          }
        }
      }
      // Remove last row is copied
      sheetPartOrder.removeRow(sheetPartOrder.getRow(sheetPartOrder.getLastRowNum()));
      sheetPartOrder.createRow(rowSize);
    }
  }


  public static void insertImageIntoCell(XSSFWorkbook wb, XSSFSheet sheet, byte[] bytes, int col, int row) {
    /* Convert picture to be added into a byte array */
    /* Add Picture to Workbook, Specify picture type as PNG and Get an Index */
    final int my_picture_id = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
    /* Close the InputStream. We are ready to attach the image to workbook now */
    /* Create the drawing container */
    final XSSFDrawing drawing = sheet.createDrawingPatriarch();
    /* Create an anchor point */
    final XSSFClientAnchor my_anchor = new XSSFClientAnchor();
    /* Define top left corner, and we can resize picture suitable from there */
    my_anchor.setCol1(col);
    my_anchor.setRow1(row);
    /* Invoke createPicture and pass the anchor point and ID */
    final XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
    /* Call resize method, which resizes the image */
    my_picture.resize();
  }

  private static void copyRow(XSSFSheet worksheet, int sourceRowNum, int destinationRowNum) {
    // Get the source / new row
    XSSFRow newRow = worksheet.getRow(destinationRowNum);
    final XSSFRow sourceRow = worksheet.getRow(sourceRowNum);

    // If the row exist in destination, push down all rows by 1 else create a new row
    if (newRow != null) {
      worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
    } else {
      newRow = worksheet.createRow(destinationRowNum);
    }

    // Loop through source columns to add to new row
    for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
      // Grab a copy of the old/new cell
      final Cell oldCell = sourceRow.getCell(i);
      Cell newCell = newRow.createCell(i);

      // If the old cell is null jump to next cell
      if (oldCell == null) {
        newCell = null;
        continue;
      }

      // Use old cell style
      newCell.setCellStyle(oldCell.getCellStyle());

      // If there is a cell comment, copy
      if (newCell.getCellComment() != null) {
        newCell.setCellComment(oldCell.getCellComment());
      }

      // If there is a cell hyperlink, copy
      if (oldCell.getHyperlink() != null) {
        newCell.setHyperlink(oldCell.getHyperlink());
      }

      // Set the cell data type
      newCell.setCellType(oldCell.getCellType());

      // Set the cell data value
      switch (oldCell.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
          break;
        case Cell.CELL_TYPE_BOOLEAN:
          newCell.setCellValue(oldCell.getBooleanCellValue());
          break;
        case Cell.CELL_TYPE_ERROR:
          newCell.setCellErrorValue(oldCell.getErrorCellValue());
          break;
        case Cell.CELL_TYPE_FORMULA:
          newCell.setCellFormula(oldCell.getCellFormula());
          break;
        case Cell.CELL_TYPE_NUMERIC:
          newCell.setCellValue(oldCell.getNumericCellValue());
          break;
        case Cell.CELL_TYPE_STRING:
          newCell.setCellValue(oldCell.getRichStringCellValue());
          break;
      }
    }
  }


  public static void deleteSheet(XSSFWorkbook workbook, XSSFSheet deletedSheet) {
    workbook.removeSheetAt(workbook.getSheetIndex(deletedSheet));
  };

  private static void copyRow(XSSFWorkbook workbook, XSSFSheet worksheet, int sourceRowNum, int destinationRowNum) {
    // Get the source / new row
    XSSFRow newRow = worksheet.getRow(destinationRowNum);
    final XSSFRow sourceRow = worksheet.getRow(sourceRowNum);

    // If the row exist in destination, push down all rows by 1 else create a new row
    if (newRow != null) {
      worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
    } else {
      newRow = worksheet.createRow(destinationRowNum);
    }

    // Loop through source columns to add to new row
    for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
      // Grab a copy of the old/new cell
      final XSSFCell oldCell = sourceRow.getCell(i);
      XSSFCell newCell = newRow.createCell(i);

      // If the old cell is null jump to next cell
      if (oldCell == null) {
        newCell = null;
        continue;
      }

      // Copy style from old cell and apply to new cell
      final XSSFCellStyle newCellStyle = workbook.createCellStyle();
      newCellStyle.cloneStyleFrom(oldCell.getCellStyle());;
      newCell.setCellStyle(newCellStyle);

      // If there is a cell comment, copy
      if (oldCell.getCellComment() != null) {
        newCell.setCellComment(oldCell.getCellComment());
      }

      // If there is a cell hyperlink, copy
      if (oldCell.getHyperlink() != null) {
        newCell.setHyperlink(oldCell.getHyperlink());
      }

      // Set the cell data type
      newCell.setCellType(oldCell.getCellType());

      // Set the cell data value
      switch (oldCell.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
          newCell.setCellValue(oldCell.getStringCellValue());
          break;
        case Cell.CELL_TYPE_BOOLEAN:
          newCell.setCellValue(oldCell.getBooleanCellValue());
          break;
        case Cell.CELL_TYPE_ERROR:
          newCell.setCellErrorValue(oldCell.getErrorCellValue());
          break;
        case Cell.CELL_TYPE_FORMULA:
          newCell.setCellFormula(oldCell.getCellFormula());
          break;
        case Cell.CELL_TYPE_NUMERIC:
          newCell.setCellValue(oldCell.getNumericCellValue());
          break;
        case Cell.CELL_TYPE_STRING:
          newCell.setCellValue(oldCell.getRichStringCellValue());
          break;
      }
    }

    // If there are are any merged regions in the source row, copy to new row
    for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
      final CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
      if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
        final CellRangeAddress newCellRangeAddress =
            new CellRangeAddress(newRow.getRowNum(), (newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
        worksheet.addMergedRegion(newCellRangeAddress);
      }
    }
  }

  /**
   * @param srcSheet the sheet to copy.
   * @param destSheet the sheet to create.
   * @param srcRow the row to copy.
   * @param destRow the row to create.
   * @param styleMap -
   */
  public static void copyRow(XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow, Map<Integer, XSSFCellStyle> styleMap) {
    // manage a list of merged zone in order to not insert two times a merged zone
    final Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
    destRow.setHeight(srcRow.getHeight());
    // reckoning delta rows
    final int deltaRows = destRow.getRowNum() - srcRow.getRowNum();
    // pour chaque row
    System.out.println(srcRow.getFirstCellNum());
    if (srcRow.getFirstCellNum() < 0)
      return;
    for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
      final XSSFCell oldCell = srcRow.getCell(j); // ancienne cell
      XSSFCell newCell = destRow.getCell(j); // new cell
      if (oldCell != null) {
        if (newCell == null) {
          newCell = destRow.createCell(j);
        }
        // copy chaque cell
        ExportFile.copyCell(oldCell, newCell, styleMap);
        // copy les informations de fusion entre les cellules
        // System.out.println("row num: " + srcRow.getRowNum() + " , col: " +
        // (short)oldCell.getColumnIndex());
        final CellRangeAddress mergedRegion = ExportFile.getMergedRegion(srcSheet, srcRow.getRowNum(), (short) oldCell.getColumnIndex());

        if (mergedRegion != null) {
          // System.out.println("Selected merged region: " + mergedRegion.toString());
          final CellRangeAddress newMergedRegion =
              new CellRangeAddress(mergedRegion.getFirstRow() + deltaRows, mergedRegion.getLastRow() + deltaRows, mergedRegion.getFirstColumn(),
                  mergedRegion.getLastColumn());
          // System.out.println("New merged region: " + newMergedRegion.toString());
          final CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
          if (ExportFile.isNewMergedRegion(wrapper, mergedRegions)) {
            mergedRegions.add(wrapper);
            destSheet.addMergedRegion(wrapper.range);
          }
        }
      }
    }
  }

  /**
   * @param oldCell
   * @param newCell
   * @param styleMap
   */
  public static void copyCell(XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {
    if (styleMap != null) {
      if (oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()) {
        newCell.setCellStyle(oldCell.getCellStyle());
      } else {
        final int stHashCode = oldCell.getCellStyle().hashCode();
        XSSFCellStyle newCellStyle = styleMap.get(stHashCode);
        if (newCellStyle == null) {
          newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
          newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
          styleMap.put(stHashCode, newCellStyle);
        }
        newCell.setCellStyle(newCellStyle);
      }
    }
    switch (oldCell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        newCell.setCellValue(oldCell.getStringCellValue());
        break;
      case Cell.CELL_TYPE_NUMERIC:
        newCell.setCellValue(oldCell.getNumericCellValue());
        break;
      case Cell.CELL_TYPE_BLANK:
        newCell.setCellType(Cell.CELL_TYPE_BLANK);
        break;
      case Cell.CELL_TYPE_BOOLEAN:
        newCell.setCellValue(oldCell.getBooleanCellValue());
        break;
      case Cell.CELL_TYPE_ERROR:
        newCell.setCellErrorValue(oldCell.getErrorCellValue());
        break;
      case Cell.CELL_TYPE_FORMULA:
        newCell.setCellFormula(oldCell.getCellFormula());
        break;
      default:
        break;
    }

  }

  /**
   *
   * @param sheet the sheet containing the data.
   * @param rowNum the num of the row to copy.
   * @param cellNum the num of the cell to copy.
   * @return the CellRangeAddress created.
   */
  public static CellRangeAddress getMergedRegion(XSSFSheet sheet, int rowNum, short cellNum) {
    for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
      final CellRangeAddress merged = sheet.getMergedRegion(i);
      if (merged.isInRange(rowNum, cellNum))
        return merged;
    }
    return null;
  }

  /**
   * Check that the merged region has been created in the destination sheet.
   *
   * @param newMergedRegion the merged region to copy or not in the destination sheet.
   * @param mergedRegions the list containing all the merged region.
   * @return true if the merged region is already in the list or not.
   */
  private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion, Set<CellRangeAddressWrapper> mergedRegions) {
    return !mergedRegions.contains(newMergedRegion);
  }

  /**
   * @param newSheet the sheet to create from the copy.
   * @param sheet the sheet to copy.
   * @param copyStyle true copy the style.
   */
  public static void copySheetsTest(XSSFSheet newSheet, XSSFSheet sheet, boolean copyStyle) {
    int maxColumnNum = 0;
    final Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;
    final int lastRowNumberOfMainSheet = newSheet.getLastRowNum();
    for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
      final XSSFRow srcRow = sheet.getRow(i);
      final XSSFRow destRow = newSheet.createRow(lastRowNumberOfMainSheet + 1 + i);
      if (srcRow != null) {
        ExportFile.copyRow(sheet, newSheet, srcRow, destRow, styleMap);
        if (srcRow.getLastCellNum() > maxColumnNum) {
          maxColumnNum = srcRow.getLastCellNum();
        }
      }
    }
    for (int i = 0; i <= maxColumnNum; i++) {
      newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
    }
  }

  /**
   * @param newSheet the sheet to create from the copy.
   * @param sheet the sheet to copy.
   * @param copyStyle true copy the style.
   */
  public static void copySheets(XSSFSheet newSheet, XSSFSheet sheet, boolean copyStyle) {
    int maxColumnNum = 0;
    final Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;
    final int lastRowNumberOfMainSheet = newSheet.getLastRowNum();
    for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
      final XSSFRow srcRow = sheet.getRow(i);
      final XSSFRow destRow = newSheet.createRow(lastRowNumberOfMainSheet + 1 + i);
      if (srcRow != null) {
        ExportFile.copyRow(sheet, newSheet, srcRow, destRow, styleMap);
        if (srcRow.getLastCellNum() > maxColumnNum) {
          maxColumnNum = srcRow.getLastCellNum();
        }
      }
    }
    for (int i = 0; i <= maxColumnNum; i++) {
      newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
    }
  }

}
