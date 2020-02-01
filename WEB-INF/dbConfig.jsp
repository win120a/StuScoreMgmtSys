<%@ page import="javax.servlet.ServletContext" %>

<%!
	String driver = "";
	String serverAddr = "";
	String userName = "";
	String password = "";
	String serverTimeZone = "";
	String db = "";

	boolean configured = false;
%>

<%
	driver = (String) application.getAttribute("driver");
	driver = driver != null ? (String) driver : "";

	serverAddr = (String) application.getAttribute("serverAddr");
	serverAddr = driver != null ? (String) serverAddr : "";

	userName = (String) application.getAttribute("userName");
	
	Object confO = application.getAttribute("configured");
	configured = confO == null ? false : (Boolean) confO;
	
	userName = userName != null ? (String) userName : "";

	password = (String) application.getAttribute("password");
	password = password != null ? (String) password : "";

	serverTimeZone = (String) application.getAttribute("serverTimeZone");
	serverTimeZone = serverTimeZone != null ? (String) serverTimeZone : "";

	db = (String) application.getAttribute("db");
	db = db != null ? (String) db : "";
%>

<%!
	private void saveConfigurations(ServletContext application, boolean configured)
	{
		application.setAttribute("driver", driver);
		application.setAttribute("userName", userName);
		application.setAttribute("password", password);
		application.setAttribute("serverTimeZone", serverTimeZone);
		application.setAttribute("serverAddr", serverAddr);
		application.setAttribute("db", db);

		userName = (String) application.getAttribute("userName");
		this.configured = configured;
		application.setAttribute("configured", configured);
	}

%>

<%!
	private void getParameters(HttpServletRequest request)
	{
		driver = (String) request.getParameter("driver");
		driver = driver != null ? (String) driver : "";

		serverAddr = (String) request.getParameter("serverAddr");
		serverAddr = driver != null ? (String) serverAddr : "";

		userName = (String) request.getParameter("userName");
		configured = Boolean.parseBoolean((String) request.getParameter("configured"));
		userName = userName != null ? (String) userName : "";

		password = (String) request.getParameter("password");
		password = password != null ? (String) password : "";

		serverTimeZone = (String) request.getParameter("serverTimeZone");
		serverTimeZone = serverTimeZone != null ? (String) serverTimeZone : "";

		db = (String) request.getParameter("db");
		db = db != null ? (String) db : "";
	}
%>