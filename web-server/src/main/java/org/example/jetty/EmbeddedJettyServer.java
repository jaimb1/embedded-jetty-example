package org.example.jetty;

import org.eclipse.jetty.ee8.servlet.ServletContextHandler;
import org.eclipse.jetty.ee8.servlet.ServletHandler;
import org.eclipse.jetty.ee8.servlet.ServletHolder;
import org.eclipse.jetty.ee8.servlet.ServletMapping;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.example.jetty.servlets.HelloServlet;

/**
 * Bootstrap Jetty server
 */
public class EmbeddedJettyServer {

  public static void main(final String[] args) throws Exception {
    final int port = Integer.parseInt(System.getProperty("PORT", "8080"));

    // Create server and set handler
    final Server server = new Server(port);
    final ContextHandlerCollection handlerCollection = new ContextHandlerCollection();
    server.setHandler(handlerCollection);

    // Configure servlet context handlers, holders, and mappings
    // (this is highly unintuitive)
    final ServletContextHandler contextHandler = new ServletContextHandler();
    final ServletHolder servletHolder = new ServletHolder(new HelloServlet());
    final ServletHandler servletHandler = contextHandler.getServletHandler();
    final ServletMapping servletMapping = new ServletMapping();
    handlerCollection.addHandler(contextHandler);
    contextHandler.setContextPath("/" + HelloServlet.CONTEXT);
    contextHandler.setServer(server);
    servletMapping.setServletName(HelloServlet.CONTEXT);
    servletMapping.setPathSpec("/*");
    servletHolder.setServletHandler(servletHandler);
    servletHandler.setServlets(new ServletHolder[]{servletHolder});
    servletHolder.setName(HelloServlet.CONTEXT);
    servletHandler.setServletMappings(new ServletMapping[]{servletMapping});
    servletHolder.start();
    contextHandler.start();

    // Start server and attach
    server.start();
    System.out.println("Jetty started on http://localhost:" + port);
    server.join();
  }
}
