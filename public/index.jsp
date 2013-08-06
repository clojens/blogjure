<%
// this is to redirect all traffic from / to /blog/front
response.sendRedirect(request.getContextPath() + "/blog/front");
%>