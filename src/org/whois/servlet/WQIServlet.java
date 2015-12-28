package org.whois.servlet;

import java.io.IOException;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.whois.WhoisClient;
import org.whois.util.PropertiesUtil;

/**
 * Servlet implementation class WQIServlet
 */
@WebServlet("/WQIServlet")
public class WQIServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Pattern pattern;
	private Matcher matcher;

	// regex whois parser
	private static final String WHOIS_SERVER_PATTERN = "Whois Server:\\s(.*)";

	private static final String WHOIS£ﬂRESOURCES = "whois.properties";
	static {
		pattern = Pattern.compile(WHOIS_SERVER_PATTERN);
	}

	/**
	 * Default constructor.
	 */
	public WQIServlet() {
		// TODO Auto-generated constructor stub
		// String domainName = "west.cc";
		// System.out.println(getWhois(domainName));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Date startTime = new Date();
		String domainName = request.getParameter("domainName");
		Date endTime = new Date();
		System.out.println(getWhois(domainName));
		System.out.println("≤È—Ø”√ ±£∫" + ((endTime.getTime() - startTime.getTime())/1000.00 + "√Î"));
	}

	public String getWhois(String domainName) {
		StringBuilder result = new StringBuilder("");
		WhoisClient whois = new WhoisClient();
		try {
			String domainSuffix = domainName.substring(
					domainName.lastIndexOf("."), domainName.length());
			Properties dbProp = PropertiesUtil.readProperties(WHOIS£ﬂRESOURCES);
			String whoisConnectUrl = dbProp.getProperty(domainSuffix);
			if (whoisConnectUrl == null) {
				connectAllWhoisByDomain(dbProp, domainName);
			} else {
				whois.connect(whoisConnectUrl);
				String whoisData1 = whois.query("=" + domainName);
				result.append(whoisData1);
				whois.disconnect();
				String whoisServerUrl = getWhoisServer(whoisData1);
				if (!whoisServerUrl.equals("")) {
					String whoisData2 = queryWithWhoisServer(domainName,
							whoisServerUrl);
					result.append(whoisData2);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();

	}

	private String connectAllWhoisByDomain(Properties dbProp, String domainName)
			throws SocketException, IOException {
		StringBuilder result = new StringBuilder("");
		WhoisClient whois = new WhoisClient();
		for (Object whoisConnectKey : dbProp.keySet()) {
			String whoisConnectUrl = dbProp.getProperty(whoisConnectKey.toString());
			try {
				whois.connect(whoisConnectUrl);
			} catch (Exception e) {
				continue;
			}
			String whoisData1 = whois.query("=" + domainName);
			result.append(whoisData1);
			whois.disconnect();
			String whoisServerUrl = getWhoisServer(whoisData1);
			if (!whoisServerUrl.equals("")) {
				String whoisData2 = queryWithWhoisServer(domainName,
						whoisServerUrl);
				result.append(whoisData2);
			}
			System.out.println(result);
			System.out.println();
		}
		return result.toString();
	}

	private String queryWithWhoisServer(String domainName, String whoisServer) {
		String result = "";
		WhoisClient whois = new WhoisClient();
		try {
			whois.connect(whoisServer);
			result = whois.query(domainName);
			whois.disconnect();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	private String getWhoisServer(String whois) {
		String result = "";
		matcher = pattern.matcher(whois);
		while (matcher.find()) {
			result = matcher.group(1);
		}
		return result;
	}

}
