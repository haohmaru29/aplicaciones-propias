package cl.acgp.commons.helper;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public final class DateUtils {

	private static final Logger logger = Logger.getLogger(DateUtils.class);
	
	public static String DateToString(Date date) {
		String d = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		d = sd.format(date);
		logger.info("Fecha parseada: " + d);
		return sd.format(date);
	}
	
	public static String DateToString(Date date, String pattern) {
		String d = null;
		SimpleDateFormat sd = new SimpleDateFormat(pattern);
		d = sd.format(date);
		logger.info("Fecha parseada: " + d);
		return sd.format(date);
	}
	
	public static Date stringToDate(String date) {
		Date d = null;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
			d = sd.parse(date);
			logger.info("Fecha parseada: " + d);
		} catch (ParseException e) {
			logger.error("[Intranet]", e);
		}
		return d;
   }
	
	public static Timestamp stringToTimeStamp(String date) {
		Timestamp d = null;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
			Date dat = sd.parse(date);
			d = new Timestamp(dat.getTime());
			logger.info("Fecha parseada: " + d);
		} catch (ParseException e) {
			logger.error("[Intranet]", e);
		}
		return d;
   }
	
    public static String Mes(Integer mes) {
        String s = "";
        switch (mes) {
            case 1:
                s = "Enero";
                break;
            case 2:
                s = "Febrero";
                break;
            case 3:
                s = "Marzo";
                break;
            case 4:
                s = "Abril";
                break;
            case 5:
                s = "Mayo";
                break;
            case 6:
                s = "Junio";
                break;
            case 7:
                s = "Julio";
                break;
            case 8:
                s = "Agosto";
                break;
            case 9:
                s = "Septiembre";
                break;
            case 10:
                s = "Octubre";
                break;
            case 11:
                s = "Noviembre";
                break;
            case 12:
                s = "Diciembre";
                break;
            default:
                s = "undefined";
                break;
        }
        return s;
    }

    public static Integer Mes(String mes) {
        Integer mesId = 0;
        if (mes.toLowerCase().trim().equals("enero")) {
            mesId = 1;
        }
        if (mes.toLowerCase().trim().equals("febrero")) {
            mesId = 2;
        }
        if (mes.toLowerCase().trim().equals("marzo")) {
            mesId = 3;
        }
        if (mes.toLowerCase().trim().equals("abril")) {
            mesId = 4;
        }
        if (mes.toLowerCase().trim().equals("mayo")) {
            mesId = 5;
        }
        if (mes.toLowerCase().trim().equals("junio")) {
            mesId = 6;
        }
        if (mes.toLowerCase().trim().equals("julio")) {
            mesId = 7;
        }
        if (mes.toLowerCase().trim().equals("agosto")) {
            mesId = 8;
        }
        if (mes.toLowerCase().trim().equals("septiembre")) {
            mesId = 9;
        }
        if (mes.toLowerCase().trim().equals("octubre")) {
            mesId = 10;
        }
        if (mes.toLowerCase().trim().equals("noviembre")) {
            mesId = 11;
        }
        if (mes.toLowerCase().trim().equals("diciembre")) {
            mesId = 12;
        }

        return mesId;
    }

    public static String parseTimestamp(String date) {
        DateFormat df;
        df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date tmp = null;
        try {
            tmp = df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(tmp);
    }
}
