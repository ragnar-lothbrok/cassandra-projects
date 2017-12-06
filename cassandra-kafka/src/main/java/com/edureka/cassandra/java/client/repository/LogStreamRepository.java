package com.edureka.cassandra.java.client.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Session;
import com.edureka.kafka.dto.LogStream;

@Service
public class LogStreamRepository {

	private static final String TABLE_NAME = "logstream";

	private static final String TABLE_NAME_REQUEST_COUNT = "logstream_requestcount";

	private static final String TABLE_NAME_REQUEST_STATUS_COUNT = "logstream_request_status_count";

	private static final String TABLE_NAME_REQUEST_PAGE_STATUS_COUNT = "logstream_page_request_status_count";

	@Autowired
	private Session session;

	public LogStreamRepository(Session session) {
		this.session = session;
	}

	public void insert(LogStream log) {
		StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME)
				.append("(year,month,day,hour,minute,timestamp,request,pageurl,response,server,cookieid) ")
				.append("VALUES (").append(log.getYear() + ",").append(log.getMonth() + ",").append(log.getDay() + ",")
				.append(log.getHour() + ",").append(log.getMinute() + ",").append(log.getTimeStamp() + ",")
				.append("'" + log.getMethod() + "'").append(", '").append(log.getPageUrl()).append("',")
				.append(log.getResponse()).append(", '").append(log.getServer()).append("', '")
				.append(log.getCookieId()).append("');");

		final String query = sb.toString();
		session.execute(query);

	}

	public void updateRequestCount(LogStream log) {
		final String updateQuery = "update " + TABLE_NAME_REQUEST_COUNT
				+ " set requestCount = requestCount + 1 where year =" + log.getYear() + " and month = " + log.getMonth()
				+ " and day = " + log.getDay() + " and hour = " + log.getHour() + " and minute = " + log.getMinute()
				+ " and server = '" + log.getServer() + "'";
		session.execute(updateQuery);

	}

	public void updateRequestStatusCount(LogStream log) {
		String update = "status_" + log.getResponse();
		final String updateQuery = "update " + TABLE_NAME_REQUEST_STATUS_COUNT
				+ " set requestCount = requestCount + 1, " + update + " =  " + update + " + 1 where year ="
				+ log.getYear() + " and month = " + log.getMonth() + " and day = " + log.getDay() + " and hour = "
				+ log.getHour() + " and minute = " + log.getMinute() + " and server = '" + log.getServer() + "'";
		session.execute(updateQuery);

	}

	public void updateRequestPageUrlStatusCount(LogStream log) {
		String update = "status_" + log.getResponse();
		final String updateQuery = "update " + TABLE_NAME_REQUEST_PAGE_STATUS_COUNT
				+ " set requestCount = requestCount + 1, " + update + " =  " + update + " + 1 where year ="
				+ log.getYear() + " and month = " + log.getMonth() + " and day = " + log.getDay() + " and hour = "
				+ log.getHour() + " and minute = " + log.getMinute() + " and server = '" + log.getServer()
				+ "' and pageurl = '" + log.getPageUrl() + "'";
		session.execute(updateQuery);
	}

}
