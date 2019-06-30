//package com.interactive.classroom.utils.excel;
//
//import jxl.*;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * @author Z-P-J
// */
//public class ExcelWriter {
//
////    private String dbase = "";
////    private String tablename = "";
//
//    public static void creExcel(JSONObject jsonObj, String tableNick, String[] cols, String filePath) throws JSONException {
//        JSONArray arr = jsonObj.getJSONArray("aaData");
//        WritableWorkbook workbook;
//        try {
//
//            OutputStream os = new FileOutputStream(filePath);
//            workbook = Workbook.createWorkbook(os);
//
//            WritableSheet sheet = workbook.createSheet(tableNick, 0);
//            jxl.write.Label label;
//
//            List<String> excelColName = new ArrayList<>();
//            List<String> excelColType = new ArrayList<>();
//            List<String> excelColNick = new ArrayList<>();
//
//            for (String col : cols) {
//
//                excelColName.add(col.split("⊙")[1]);
//                excelColType.add(col.split("⊙")[0]);
//                excelColNick.add(col.split("⊙")[2]);
//
//            }
//
//            for (int i = 0; i < excelColNick.size(); i++) {
//                String colNick = excelColNick.get(i);
//
//                label = new jxl.write.Label(i, 0, colNick); // put the title in
//                // row1
//                sheet.addCell(label);
//
//            }
//
//            List data = new ArrayList();
//
//            int n = 1;
//
//            for (int j = 0; j < arr.length(); j++) {
//                ArrayList list = (ArrayList) arr.get(j);
//                JSONObject jsonObject = (JSONObject) arr.get(j);
//                for (int i = 0; i < excelColName.size(); i++) {
//                    String colType = excelColType.get(i);
//
//                    if (colType.contains("text") || colType.contains("mediumtext") || colType.contains("varchar")) {
//
//                        label = new jxl.write.Label(i, n, list.get(i) + "");
//                        sheet.addCell(label);
//                    } else {
//
//                        label = new jxl.write.Label(i, n, list.get(i) + "");
//                        sheet.addCell(label);
//                    }
//                }
//                n++;
//            }
//            workbook.write();
//            workbook.close();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void creExcel(JSONObject jsonObj, String[] labels, String[] labelsZh, String tableNick, String filePath) throws JSONException {
//        try {
//            OutputStream os = new FileOutputStream(filePath);
//            WritableWorkbook workbook = Workbook.createWorkbook(os);
//            WritableSheet sheet = workbook.createSheet(tableNick, 0);
//
//            for (int i = 0; i < labelsZh.length; i++) {
//                sheet.addCell(new Label(i, 0, labelsZh[i]));
//            }
//
//            JSONArray arr = jsonObj.getJSONArray("aaData");
//            int col = 1;
//            for (int j = 0; j < arr.length(); j++) {
//                JSONObject jsonObject = (JSONObject) arr.get(j);
//                for (int i = 0; i < labels.length; i++) {
//                    sheet.addCell(new Label(i, col, jsonObject.getString(labels[i])));
//                }
//                col++;
//            }
//            workbook.write();
//            workbook.close();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    public static void main(String[] args) {
////
////        // 输出的excel文件名
////        String targetfile = "c:/out.xls";
////        // 输出的excel文件工作表名
////        String worksheet = "List";
////        // excel工作表的标题
////        String[] title = {"ID", "NAME", "DESCRIB"};
////
////        WritableWorkbook workbook;
////        try {
////            // 创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下
////            // workbook = Workbook.createWorkbook(new FileBean("output.xls"));
////            OutputStream os = new FileOutputStream(targetfile);
////            workbook = Workbook.createWorkbook(os);
////
////            WritableSheet sheet = workbook.createSheet(worksheet, 0);
////
////            jxl.write.Label label;
////            for (int i = 0; i < title.length; i++) {
////                for (int j = 0; j < 50; j++) {
////                    // Label(列号,行号 ,内容 )
////                    label = new jxl.write.Label(i, j, title[i]);
////                    // title in
////                    // row1
////                    sheet.addCell(label);
////                }
////            }
////            workbook.write();
////            workbook.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////    }
//}