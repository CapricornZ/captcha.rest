package demo.captcha.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import demo.captcha.model.CaptchaExamClient;

public class ReadExcel {
	
	@SuppressWarnings("static-access")
    private String getValue(XSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue()).trim();
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue()).trim();
        } else {
            return String.valueOf(hssfCell.getStringCellValue()).trim();
        }
    }
	
	public List<CaptchaExamClient> readXlsx(InputStream is) throws IOException {

		@SuppressWarnings("resource")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		CaptchaExamClient client = null;
		List<CaptchaExamClient> list = new ArrayList<CaptchaExamClient>();
		
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				
			if (xssfSheet == null)
				continue;
			
		 	// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					try{
						client = new CaptchaExamClient();
						XSSFCell host = xssfRow.getCell(0);
						XSSFCell pass = xssfRow.getCell(1);
						XSSFCell userName = xssfRow.getCell(2);
						XSSFCell mail = xssfRow.getCell(3);
						XSSFCell phoneNo = xssfRow.getCell(4);
	
						client.setHost(this.getValue(host));
						client.setCode(this.getValue(pass));
						client.setMailAddress(this.getValue(mail));
						client.setUserName(this.getValue(userName));
						client.setPhoneNo(this.getValue(phoneNo));
						client.setRoles("user");
	
						if(!client.getHost().trim().isEmpty())
							list.add(client);
						
					}catch (Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}
		return list;
	}
}
