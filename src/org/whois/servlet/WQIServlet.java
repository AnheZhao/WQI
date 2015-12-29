package org.whois.servlet;

import java.io.IOException;
import java.net.SocketException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.whois.WhoisClient;
import org.whois.dao.WQRDao;
import org.whois.model.WhoisQueryRecord;
import org.whois.util.PropertiesUtil;
import org.whois.util.WebUtils;

/**
 * Servlet implementation class WQIServlet
 */
@WebServlet("/WQIServlet")
public class WQIServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("WQIServlet");

	private static Pattern pattern;
	private Matcher matcher;

	// regex whois parser
	private static final String WHOIS_SERVER_PATTERN = "Whois Server:\\s(.*)";

	private static final String WHOIS£ßRESOURCES = "whois.properties";
	static {
		pattern = Pattern.compile(WHOIS_SERVER_PATTERN);
	}

	public WQIServlet() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String domainName = request.getParameter("domainName");
		String result = getWhois(request, domainName);
		logger.info(result);
		response.getOutputStream().print(result);
	}

	public String getWhois(HttpServletRequest request,String domainName) {
		Date startTime = new Date();
		Date endTime = null;
		String domainSuffix = null;
		String domainPrefix = null;
		StringBuilder result = new StringBuilder("");
		WhoisClient whois = new WhoisClient();
		try {
			domainSuffix = domainName.substring(domainName.lastIndexOf("."),
					domainName.length());
			domainPrefix = domainName.substring(0,
					domainName.lastIndexOf("."));
			Properties dbProp = PropertiesUtil.readProperties(WHOIS£ßRESOURCES);
			String whoisConnectUrl = dbProp.getProperty(domainSuffix);
			if (whoisConnectUrl == null) {
				// connectAllWhoisByDomain(dbProp, domainName);
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
			endTime = new Date();
		} catch (SocketException e) {
			logger.info("SocketException :" + e.getMessage());
		} catch (IOException e) {
			logger.info("IOException :" + e.getMessage());
		} finally {
			try {
				WhoisQueryRecord wqr = new WhoisQueryRecord();
				wqr.setWqrDomain(domainName);
				wqr.setWqrUseTime(endTime.getTime() - startTime.getTime());
				wqr.setWqrSuffix(domainSuffix);
				wqr.setWqrPrefix(domainPrefix);
				wqr.setWqrStatus(1);
				wqr.setWqrIpAddress(WebUtils.getIpAddr(request));
				WQRDao wqrDao = new WQRDao();
				wqrDao.addWQR(wqr);
			} catch (Exception e) {
				logger.info("insert sql error :" + e.getMessage());
			}
		}
		return result.toString();

	}

	@SuppressWarnings("all")
	@Deprecated
	private String connectAllWhoisByDomain(Properties dbProp, String domainName)
			throws SocketException, IOException {
		StringBuilder result = new StringBuilder("");
		WhoisClient whois = new WhoisClient();
		for (Object whoisConnectKey : dbProp.keySet()) {
			String whoisConnectUrl = dbProp.getProperty(whoisConnectKey
					.toString());
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
