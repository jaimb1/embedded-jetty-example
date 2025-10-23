package org.example.jetty;

import org.example.jetty.servlets.HelloServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;

public class EmbeddedJettyServletContainerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(final Set<Class<?>> set, final ServletContext servletContext) {
    servletContext.addServlet("hello-servlet", new HelloServlet()).addMapping("/hello");
  }
}
