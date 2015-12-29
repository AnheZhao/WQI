package org.whois.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

	private static final Logger LOGGER = Logger.getLogger("webUtils");

	/**
	 * 获取客户端的ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = "unknown";
		ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if ("0:0:0:0:0:0:0:1".equals(ip)) {// 在win7下通过localhost访问本机web服务时会得到此值
				ip = "127.0.0.1";
			}
			if ("127.0.0.1".equals(ip)) {
				// 根据网卡取本机配置的ip
				InetAddress inetAddress = null;
				try {
					inetAddress = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					LOGGER.info("获取ip地址失败!");
				}
				ip = inetAddress.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
}
