package com.edureka.kafka.dto;

import java.io.Serializable;

public class LogStream implements Serializable {

	private static final long serialVersionUID = 4913933159110420730L;

	private Integer response;
	private long timeStamp;
	private String method;
	private String server;
	private String pageUrl;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	private String cookieId;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getCookieId() {
		return cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Integer getResponse() {
		return response;
	}

	public void setResponse(Integer response) {
		this.response = response;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public LogStream(Integer response, long timeStamp, String method, String server, String pageUrl, int year,
			int month, int day, int hour, int minute, String cookieId) {
		super();
		this.response = response;
		this.timeStamp = timeStamp;
		this.method = method;
		this.server = server;
		this.pageUrl = pageUrl;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.cookieId = cookieId;
	}

	@Override
	public String toString() {
		return "LogStream [response=" + response + ", timeStamp=" + timeStamp + ", method=" + method + ", server="
				+ server + ", pageUrl=" + pageUrl + "]";
	}

}
