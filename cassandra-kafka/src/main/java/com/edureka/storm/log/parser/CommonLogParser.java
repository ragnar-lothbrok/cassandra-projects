package com.edureka.storm.log.parser;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.dto.LogStream;

public class CommonLogParser implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CommonLogParser.class);
	private static final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z");

	public static final String IP = "ip";
	public static final String TIMESTAMP = "timestamp";
	public static final String METHOD = "method";
	public static final String PAGE_URL = "pageurl";
	public static final String RESPONSE = "response";
	private static final int NUM_FIELDS = 8;
	private static final String YEAR = "YEAR";
	private static final String MONTH = "MONTH";
	private static final String DAY = "DAY";
	private static final String HOUR = "HOUR";
	private static final String MINUTE = "MINUTE";

	public static LogStream parse(String str) {
		Map<String, String> entry = parseLine(str);

		if (entry == null) {
			LOG.warn("Unable to parse log: {}", str);
		}

		return new LogStream(Integer.parseInt(entry.get(RESPONSE)), Long.parseLong(entry.get(TIMESTAMP)),
				entry.get(METHOD), entry.get(IP), entry.get(PAGE_URL), Integer.parseInt(entry.get(YEAR)),
				Integer.parseInt(entry.get(MONTH)), Integer.parseInt(entry.get(DAY)), Integer.parseInt(entry.get(HOUR)),
				Integer.parseInt(entry.get(MINUTE)), UUID.randomUUID().toString());
	}

	public static Map<String, String> parseLine(String logLine) {
		Map<String, String> entry = new HashMap<>();
		String logEntryPattern = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\S+)(.*?)";

		Pattern p = Pattern.compile(logEntryPattern);
		Matcher matcher = p.matcher(logLine);

		if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
			return null;
		}

		entry.put(IP, matcher.group(1));
		entry.put(TIMESTAMP, dtFormatter.parseDateTime(matcher.group(4)).toDate().getTime() + "");

		Calendar cal = Calendar.getInstance();
		cal.setTime(dtFormatter.parseDateTime(matcher.group(4)).toDate());

		entry.put(YEAR, cal.get(Calendar.YEAR) + "");
		entry.put(MONTH, cal.get(Calendar.MONTH) + "");
		entry.put(DAY, cal.get(Calendar.DAY_OF_MONTH) + "");
		entry.put(HOUR, cal.get(Calendar.HOUR) + "");
		entry.put(MINUTE, cal.get(Calendar.MINUTE) + "");

		String request = matcher.group(5);
		String method = request.split(" ")[0];
		request = request.replaceAll(method, "").trim();
		entry.put(METHOD, method);
		entry.put(PAGE_URL, request);
		entry.put(RESPONSE, matcher.group(6));

		return entry;
	}
}
