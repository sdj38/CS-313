<%-- 
    Document   : Login
    Created on : Jun 17, 2015, 8:53:26 PM
    Author     : Stephen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <link rel="stylesheet" href="discussion.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>JSP Page</title>
    </head>
    <body>
        <br/>
        <br/>
        <div id="login">
            <br/>
            <form action="ValidateLogin" method="POST">
            <table>
                <th id="th">Sign in</th>
                <tr><td>User Name: </td><td><input type="text" name="user" /></td></tr>
                <c:if test="${!empty theResult}">
                     <tr><td colspan="2"><c:out value="${theResult}" /> </td></tr>
                </c:if>
                <tr><td></td><td><input type="submit" value="Login" class="submit"></td></tr>
                
                
            </table>
            </form>
            <br/>
        </div>
        <br />
    
        
        <img src="http://www.mcdonalds.com/us_assets/value_all_assets/GOC_TreeSilhouettes.png" width="100%"/>
        
    </body>
</html>
