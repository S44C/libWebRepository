package libWeb.beans;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import libWeb.DAO.implementation.AuditDAOImpl;
import libWeb.DAO.interfaces.AuditDAO;
import libWeb.entities.Audit;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@ManagedBean
@SessionScoped
public class AuditBean 
{

	private Audit audit;

	private final static Logger logger = Logger.getLogger(AuditBean.class);

	private DataModel<Audit> listaAuditoria;

	private DataModel<Audit> listaTrazoUsuario;

	public String adicionarAuditoria(Audit auditoria) 
	{
		AuditDAO dao = new AuditDAOImpl();
		dao.save(auditoria);
		return "listAudit";
	}

	public DataModel<Audit> getListarAuditoria() 
	{
		List<Audit> lista = new AuditDAOImpl().list();
		listaAuditoria = new ListDataModel<>(lista);
		return listaAuditoria;
	}

	public String prepararTrazabilidadUsuario()
	{
		audit = (Audit) (listaAuditoria.getRowData());
		return "trazabilidadUsuario";
	}

	public DataModel<Audit> getListaTrazoUsuario() 
	{
		List<Audit> lista = new AuditDAOImpl().listUser(audit.getUserId());
		listaTrazoUsuario = new ListDataModel<>(lista);
		return listaTrazoUsuario;
	}

	public String excelAuditorias()
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<Audit> list = new AuditDAOImpl().list();

		Sheet hoja = workbook.createSheet("auditoria");

		int noFila = 0;

		for (int i = 0; i < list.size(); i++)
		{
			Audit actual = list.get(i);
			Row fila = hoja.createRow(noFila++);
			int noCelda = 0;

			if(i==0)
			{
				fila.createCell(noCelda++).setCellValue("ID");
				fila.createCell(noCelda++).setCellValue("USER ID");
				fila.createCell(noCelda++).setCellValue("OPERATION");
				fila.createCell(noCelda++).setCellValue("TABLE NAME");
				fila.createCell(noCelda++).setCellValue("TABLE ID");
				fila.createCell(noCelda++).setCellValue("CREATE DATE");

				fila = hoja.createRow(noFila++);
				noCelda = 0;
				fila.createCell(noCelda++).setCellValue(actual.getId());
				fila.createCell(noCelda++).setCellValue(actual.getUserId());
				fila.createCell(noCelda++).setCellValue(actual.getOperation());
				fila.createCell(noCelda++).setCellValue(actual.getTableName());
				fila.createCell(noCelda++).setCellValue(actual.getTableId());
			}

			else
			{
				fila.createCell(noCelda++).setCellValue(actual.getId());
				fila.createCell(noCelda++).setCellValue(actual.getUserId());
				fila.createCell(noCelda++).setCellValue(actual.getOperation());
				fila.createCell(noCelda++).setCellValue(actual.getTableName());
				fila.createCell(noCelda++).setCellValue(actual.getTableId());

				Date date = actual.getCreateDate();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
				String reportDate = df.format(date);

				fila.createCell(noCelda++).setCellValue(reportDate);
			}
		}

		try
		{
			String home = System.getProperty("user.home");
			File file = new File(home + "/Downloads/auditoria.xlsx"); 
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		}
		catch(FileNotFoundException e)
		{
			logger.error("No se encontró el directorio destino", e);	
		}
		catch(IOException e)
		{
			logger.error(e.getMessage());
		}

		return "";
	}

	public String pdfAuditorias()
	{
		List<Audit> list = new AuditDAOImpl().list();
		Document document = new Document();
		PdfPTable table = new PdfPTable(new float[] { 2, 2, 2, 2, 2 ,2 });


		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		table.addCell("ID");
		table.addCell("USER ID");
		table.addCell("OPERATION");
		table.addCell("TABLE NAME");
		table.addCell("TABLE ID");
		table.addCell("CREATE DATE");

		table.setHeaderRows(1);

		PdfPCell[] cells = table.getRow(0).getCells();

		for (int j=0; j < cells.length; j++)
		{
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}

		for (int i=0; i < list.size(); i++)
		{
			Audit actual = list.get(i);

			table.addCell(actual.getId()+"");
			table.addCell(actual.getUserId()+"");
			table.addCell(actual.getOperation()+"");
			table.addCell(actual.getTableName());
			table.addCell(actual.getTableId()+"");

			Date date = actual.getCreateDate();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
			String reportDate = df.format(date);

			table.addCell(reportDate);
		}

		try
		{
			String home = System.getProperty("user.home");
			File file = new File(home + "/Downloads/auditoria.pdf"); 
			PdfWriter.getInstance(document, new FileOutputStream(file));
		} 
		catch (FileNotFoundException e)
		{
			logger.error(e.getMessage());
		} 
		catch (DocumentException e)
		{
			logger.error(e.getMessage());
		}

		document.open();

		try
		{
			document.add(table);
		} 
		catch (DocumentException e)
		{
			logger.error(e.getMessage());
		}
		document.close();

		return "";
	}

	public String excelAuditoriasUsuarioUnico()
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<Audit> list = new AuditDAOImpl().listUser(audit.getUserId());

		Sheet hoja = workbook.createSheet("auditoria_usuario_" + audit.getUserId());

		int noFila = 0;

		for (int i = 0; i < list.size(); i++)
		{
			Audit actual = list.get(i);
			Row fila = hoja.createRow(noFila++);
			int noCelda = 0;

			if(i==0)
			{
				fila.createCell(noCelda++).setCellValue("ID");
				fila.createCell(noCelda++).setCellValue("USER ID");
				fila.createCell(noCelda++).setCellValue("OPERATION");
				fila.createCell(noCelda++).setCellValue("TABLE NAME");
				fila.createCell(noCelda++).setCellValue("TABLE ID");
				fila.createCell(noCelda++).setCellValue("CREATE DATE");

				fila = hoja.createRow(noFila++);
				noCelda = 0;
				fila.createCell(noCelda++).setCellValue(actual.getId());
				fila.createCell(noCelda++).setCellValue(actual.getUserId());
				fila.createCell(noCelda++).setCellValue(actual.getOperation());
				fila.createCell(noCelda++).setCellValue(actual.getTableName());
				fila.createCell(noCelda++).setCellValue(actual.getTableId());
			}

			else
			{
				fila.createCell(noCelda++).setCellValue(actual.getId());
				fila.createCell(noCelda++).setCellValue(actual.getUserId());
				fila.createCell(noCelda++).setCellValue(actual.getOperation());
				fila.createCell(noCelda++).setCellValue(actual.getTableName());
				fila.createCell(noCelda++).setCellValue(actual.getTableId());

				Date date = actual.getCreateDate();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
				String reportDate = df.format(date);

				fila.createCell(noCelda++).setCellValue(reportDate);
			}
		}

		try
		{
			String home = System.getProperty("user.home");
			File file = new File(home + "/Downloads/auditoria_usuario_"+ audit.getUserId() +".xlsx"); 
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		}
		catch(FileNotFoundException e)
		{
			logger.error("No se encontró el directorio destino", e);	
		}
		catch(IOException e)
		{
			logger.error(e.getMessage());
		}

		return "";
	}

	public String pdfAuditoriasUsuarioUnico()
	{
		List<Audit> list = new AuditDAOImpl().listUser(audit.getUserId());
		Document document = new Document();
		PdfPTable table = new PdfPTable(new float[] { 2, 2, 2, 2, 2 ,2 });


		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		table.addCell("ID");
		table.addCell("USER ID");
		table.addCell("OPERATION");
		table.addCell("TABLE NAME");
		table.addCell("TABLE ID");
		table.addCell("CREATE DATE");

		table.setHeaderRows(1);

		PdfPCell[] cells = table.getRow(0).getCells();

		for (int j=0; j < cells.length; j++)
		{
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}

		for (int i=0; i < list.size(); i++)
		{
			Audit actual = list.get(i);

			table.addCell(actual.getId()+"");
			table.addCell(actual.getUserId()+"");
			table.addCell(actual.getOperation()+"");
			table.addCell(actual.getTableName());
			table.addCell(actual.getTableId()+"");

			Date date = actual.getCreateDate();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
			String reportDate = df.format(date);

			table.addCell(reportDate);
		}

		try
		{
			String home = System.getProperty("user.home");
			File file = new File(home + "/Downloads/auditoria_usuario_"+ audit.getUserId()+".pdf"); 
			PdfWriter.getInstance(document, new FileOutputStream(file));
		} 
		catch (FileNotFoundException e)
		{
			logger.error(e.getMessage());
		} 
		catch (DocumentException e)
		{
			logger.error(e.getMessage());
		}

		document.open();

		try
		{
			document.add(table);
		} 
		catch (DocumentException e)
		{
			logger.error(e.getMessage());
		}
		document.close();

		return "";
	}


}