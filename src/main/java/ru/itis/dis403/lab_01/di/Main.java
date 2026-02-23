package ru.itis.dis403.lab_01.di;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import ru.itis.dis403.lab_01.di.config.*;
import ru.itis.dis403.lab_01.di.servlet.DispatcherServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        ru.itis.dis403.lab_01.di.config.Context diContext = new ru.itis.dis403.lab_01.di.config.Context();

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        Connector conn = new Connector();
        conn.setPort(8090);
        tomcat.setConnector(conn);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context tomcatContext = tomcat.addContext(contextPath, docBase);

        DispatcherServlet dispatcher = new DispatcherServlet(diContext);
        String servletName = "dispatcherServlet";
        tomcat.addServlet(contextPath, servletName, dispatcher);
        tomcatContext.addServletMappingDecoded("/*", servletName);

        try {
            tomcat.start();
            System.out.println("Сервер: http://localhost:8090/home");
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
