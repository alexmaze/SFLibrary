package com.successfactors.library.shared;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.successfactors.library.server.UserServiceImpl;
import com.successfactors.library.shared.model.SLUser;

public class UserImporter {

	UserServiceImpl userService = new UserServiceImpl();
	
	@SuppressWarnings("deprecation")
	public void loadXlsx(String filePath) {
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = null;
		try {
			xwb = new XSSFWorkbook(filePath);
		} catch (IOException e) {
			System.out.println("读取文件出错");
			e.printStackTrace();
		}
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		xwb.getSheetAt(1);

		// 定义 row、cell
		XSSFRow row;
		String cell;

		// 循环输出表格中的内容
		for (int i = 1; i < 167; i++) {
			row = sheet.getRow(i);

			String email = row.getCell(10).toString()+"@successfactors.com";
			String name = row.getCell(1).toString();
			String department = row.getCell(4).toString();
			String password = "123456";
			String type = "读者";
			
			SLUser newUser = new SLUser();
			newUser.setUserName(name);
			newUser.setUserEmail(email);
			newUser.setUserDepartment(department);
			newUser.setUserPassword(password);
			newUser.setUserType(type);
			
			if (userService.register(newUser) == null) {
				System.out.println("出错！！！！！！！");
				break;
			}
		}
	}

	public static void main(String[] args) {
		UserImporter readExcel = new UserImporter();
		readExcel.loadXlsx("D:/a.xlsx");
	}
}
