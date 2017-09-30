package ua.training.project4;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import ua.training.project4.tomcat.EmbededContextConfig;
import ua.training.project4.tomcat.EmbededStandardJarScanner;
import ua.training.project4.tomcat.TomcatUtil;
import ua.training.project4.tomcat.WebXmlMountListener;

/**
 *
 * @author hengyunabc
 *
 */
public class Main {

	public static void main(String[] args) throws ServletException, LifecycleException, IOException {

		String hostName = "localhost";
		int port = 8080;
		String contextPath = "";

		String tomcatBaseDir = TomcatUtil.createTempDir("tomcat", port).getAbsolutePath();
		String contextDocBase = TomcatUtil.createTempDir("tomcat-docBase", port).getAbsolutePath();

		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(tomcatBaseDir);

		tomcat.setPort(port);
		tomcat.setHostname(hostName);

		Host host = tomcat.getHost();
		Context context = tomcat.addWebapp(host, contextPath, contextDocBase, new EmbededContextConfig());

		context.setJarScanner(new EmbededStandardJarScanner());

		ClassLoader classLoader = Main.class.getClassLoader();
		context.setParentClassLoader(classLoader);

		// context load WEB-INF/web.xml from classpath
		context.addLifecycleListener(new WebXmlMountListener());

		tomcat.start();
		tomcat.getServer().await();
	}
}
