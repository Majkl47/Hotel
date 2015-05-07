package cz.muni.fi.pv168.hotel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotelUtils {
	
	final static Logger log = LoggerFactory.getLogger(HotelUtils.class);

	public static String convertDateToString(Date date)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(date);
		return formattedDate;
	}

	public static Date convertStringToDate(String dateString) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
			myDate = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myDate;
	}

	public static void executeSQLScript(DataSource dataSource, URL scriptURL) throws SQLException{
		 Connection conn = null;
	        try {
	            conn = dataSource.getConnection();
	            for (String sqlStatement : readSqlStatements(scriptURL)) {
	                if (!sqlStatement.trim().isEmpty()) {
	                    conn.prepareStatement(sqlStatement).executeUpdate();
	                }
	            }
	        } finally {
	        	if (conn != null) {
	                try {
	                    conn.setAutoCommit(true);
	                } catch (SQLException ex) {
	                    log.error("Error when switching autocommit mode back to true", ex);
	                }
	                try {
	                    conn.close();
	                } catch (SQLException ex) {
	                    log.error("Error when closing connection", ex);
	                }
	            }
	        }
	}
    
	private static String[] readSqlStatements(URL url) {
        try {
            char buffer[] = new char[256];
            StringBuilder result = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            while (true) {
                int count = reader.read(buffer);
                if (count < 0) {
                    break;
                }
                result.append(buffer, 0, count);
            }
            return result.toString().split(";");
        } catch (IOException ex) {
            throw new RuntimeException("Cannot read " + url, ex);
        }
    }
}
