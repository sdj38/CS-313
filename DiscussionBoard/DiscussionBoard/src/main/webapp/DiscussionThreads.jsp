<%-- 
    Document   : DiscussionThreads
    Created on : Jun 17, 2015, 9:35:46 PM
    Author     : Stephen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="discussion.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
       
    </head>
    <body>
       
        <div id="right">
            <h1> ${user} </h1>
            <form action="createThread.jsp" method="POST">
                <input type="submit" value="Create New Thread" class="submit" />
            </form>
        </div>
            <br/>
    <c:forEach var="thread" items="${boards}">
        <div class="thread">
            <div class="header">
                ${thread.name} <span class="date">${thread.date}</span>
            </div>
            ${thread.content}
        </div>
        <br/>
                
            </c:forEach>
         <br/>
        <br/>
        <img id="tree" src="http://i.imgur.com/ulSeelS.png"/>
    </body>
</html>
