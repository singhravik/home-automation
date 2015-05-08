package com.ravi.automation.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * @author rsing34
 * 
 */
public class NetworkUtils {

	private final static Logger logger = LoggerFactory.getLogger(NetworkUtils.class);
	final static String iface = "wlan0"; //eth0

	private static String hostname = System.getenv("HOSTNAME");
	private static String hostip = "";

	static {
		try {
			if (!StringUtils.hasText(hostname))
				hostname = InetAddress.getLocalHost().getHostName();
			logger.info("hostname: " + hostname);
			//
			for (InetAddress add : InetAddress.getAllByName(hostname)) {
				// ignore local
				if (add.getHostAddress().startsWith("127."))
					continue;
				// ignore ipv6
				if (add.getHostAddress().contains(":"))
					continue;
				hostip = add.getHostAddress();
			}
			// new way
			if (!StringUtils.hasText(hostip)) {
				NetworkInterface ni = NetworkInterface.getByName(iface);
				Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress ia = inetAddresses.nextElement();
					hostip = ia.getHostAddress();
				}
			}
			logger.info("hostip: " + hostip);
		} catch (Exception e) {
			// safe to EAT
		}
	}

	public static void main(String... strings) {
		System.out.println(getHostAddress());
	}

	public static String getHostname() {
		return hostname;
	}

	public static String getHostAddress() {
		return hostip;
	}

	public static String getHostname(String name) {
		try {
			return InetAddress.getByName(name).getCanonicalHostName();
		} catch (UnknownHostException e) {
			return name;
		}
	}

	public static String getHostAddress(String name) {
		try {
			return InetAddress.getByName(name).getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	public static String getHostString() {
		return hostname + " <" + hostip + ">";
	}

	public static List<String> checkSocketHealths(List<String> nodes, int port, int timeout) {
		List<String> respones = new ArrayList<String>();
		for (String node : nodes) {
			respones.add(checkSocketHealth(node, port, timeout));
		}
		Collections.sort(respones);
		return respones;
	}

	public static List<String> checkSocketHealths(List<URI> uris, int timeout) {
		List<String> respones = new ArrayList<String>();
		for (URI uri : uris) {
			respones.add(checkSocketHealth(uri, timeout));
		}
		Collections.sort(respones);
		return respones;
	}

	public static String checkSocketHealth(String node, int port, int timeout) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(node, port), timeout);
			socket.setSoTimeout(timeout);
			return "SUCCESS";
		} catch (Exception e) {
			return "FAILED";
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				// EAT
			}
		}
	}

	public static String checkSocketHealth(URI uri, int timeout) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(uri.getHost(), uri.getPort()), timeout);
			socket.setSoTimeout(timeout);
			return "SUCCESS";
		} catch (Exception e) {
			return "FAILED";
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				// EAT
			}
		}
	}

	public static void showRequestDetails(HttpServletRequest request) {
		//
		logger.info("ServerName: " + request.getServerName());
		logger.info("ServerPort: " + request.getServerPort());
		logger.info("ContextPath: " + request.getContextPath());
		logger.info("PathInfo: " + request.getPathInfo());
		logger.info("PathTranslated: " + request.getPathTranslated());
		logger.info("QueryString: " + request.getQueryString());
		logger.info("RequestURI: " + request.getRequestURI());
		logger.info("RemoteAddr: " + request.getRemoteAddr());
		logger.info("LocalAddr: " + request.getLocalAddr());
		logger.info("LocalName: " + request.getLocalName());

		Enumeration attrEnum = request.getAttributeNames();
		while (attrEnum.hasMoreElements()) {
			String attr = (String) attrEnum.nextElement();
			logger.info("Attribute: " + attr + " = " + request.getAttribute(attr));
		}

		Enumeration headerEnum = request.getHeaderNames();
		while (headerEnum.hasMoreElements()) {
			String header = (String) headerEnum.nextElement();
			logger.info("Header: " + header + " = " + request.getHeader(header));
		}
	}

}
