package org.example.jetty;

import org.eclipse.jetty.ee8.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;

import javax.servlet.ServletContainerInitializer;

/**
 * Bootstrap Jetty server
 */
public class EmbeddedJettyServer {

  public static void main(final String[] args) throws Exception {
    final int port = Integer.parseInt(System.getProperty("PORT", "8080"));

    // Create server and register the servlet container initializer (avoid Jetty's handler API)
    final Server server = new Server(port);
    final ServletContextHandler servletContextHandler = new ServletContextHandler();
    final ServletContainerInitializer servletContainerInitializer = new EmbeddedJettyServletContainerInitializer();
    server.setHandler(servletContextHandler);
    servletContextHandler.addServletContainerInitializer(servletContainerInitializer);

    // Start server and attach
    server.start();
    System.out.println("Jetty started on http://localhost:" + port);
    server.join();
  }
}
