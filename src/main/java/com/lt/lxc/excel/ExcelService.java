package com.lt.lxc.excel;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lt.lxc.dao.IOpenDataRepository;
import com.lt.lxc.entity.OpenData;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Component
public class ExcelService {
	
	@Autowired
	private IOpenDataRepository iOpenDataRep;
	
	public void FromDBToExcel() {
		try {
			WritableWorkbook wwb = null;
			// 创建可写入的Excel工作簿
			String fileName = "E://data//OpenData.xls";
			File file=new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			//以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);

			// 创建工作表
			WritableSheet ws = wwb.createSheet("OpenData1", 0);
			//查询数据库中所有的数据
			List<OpenData> list= iOpenDataRep.findByLimit(20);
			//要插入到的Excel表格的行号，默认从0开始
			Label labelId= new Label(0, 0, "编号(id)");//表示第
			Label labelOpenTime= new Label(1, 0, "开奖时间(openTime)");
			Label labelIssueNumber= new Label(2, 0, "期号(issueNumber)");
			Label labelOpenCode= new Label(3, 0, "开奖号码(openCode)");
			Label labelType= new Label(4, 0, "彩种(type)");
			
			ws.addCell(labelId);
			ws.addCell(labelOpenTime);
			ws.addCell(labelIssueNumber);
			ws.addCell(labelOpenCode);
			ws.addCell(labelType);
			for (int i = 0; i < list.size(); i++) {
				Label labelId_i= new Label(0, i+1, list.get(i).getId()+"");
				Label labelOpenTime_i = new Label(1, i+1, list.get(i).getOpenTime()+"");
				Label labelIssueNumber_i= new Label(2, i+1, list.get(i).getIssueNumber());
				Label labelOpenCode_i= new Label(3, i+1, list.get(i).getOpenCode());
				Label labelType_i= new Label(4, i+1, list.get(i).getType());
				
				ws.addCell(labelId_i);
				ws.addCell(labelOpenTime_i);
				ws.addCell(labelIssueNumber_i);
				ws.addCell(labelOpenCode_i);
				ws.addCell(labelType_i);
			}
			//写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			System.out.println("数据导出成功!");
			wwb.close();
		} catch (Exception e) {
			throw new RuntimeException("导出excel错误"+e.getMessage());
		}finally {
			
		}
		
	}
}
