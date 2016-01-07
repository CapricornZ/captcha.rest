package demo.captcha.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读标书&策略&机器
 * @author martin
 *
 */
public class ReadConfigXls {
	
	@SuppressWarnings("static-access")
    private String getValue(XSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
	
	public List<String[]> readXlsx(InputStream is) throws IOException {

		@SuppressWarnings("resource")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<String[]> list = new ArrayList<String[]>();
		
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null)
				continue;
			
		 	// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {

				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					try{
						List<String> rows = new ArrayList<String>();
						XSSFCell host = xssfRow.getCell(0);rows.add(this.getValue(host));
						
						//标书信息
						XSSFCell userName = xssfRow.getCell(1);rows.add(this.getValue(userName));
						XSSFCell identity = xssfRow.getCell(2);rows.add(this.getValue(identity));
						XSSFCell bid = xssfRow.getCell(3);rows.add(this.getValue(bid));
						XSSFCell pass = xssfRow.getCell(4);rows.add(this.getValue(pass));
						XSSFCell from = xssfRow.getCell(5);rows.add(this.getValue(from));
						
						//POLICY1
						XSSFCell priceTime = xssfRow.getCell(6);rows.add(this.getValue(priceTime));
						XSSFCell priceDelta = xssfRow.getCell(7);rows.add(this.getValue(priceDelta));
						XSSFCell submitTime = xssfRow.getCell(8);rows.add(this.getValue(submitTime));
						
						//POLICY2
						XSSFCell priceTime2 = xssfRow.getCell(9);rows.add(this.getValue(priceTime2));
						XSSFCell priceDelta2 = xssfRow.getCell(10);rows.add(this.getValue(priceDelta2));
						XSSFCell submitTime2 = xssfRow.getCell(11);rows.add(this.getValue(submitTime2));

						list.add(rows.toArray(new String[rows.size()]));
						
					}catch (Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}
		return list;
	}
}
