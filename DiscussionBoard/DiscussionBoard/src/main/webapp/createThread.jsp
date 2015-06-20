<%-- 
    Document   : createThread
    Created on : Jun 18, 2015, 4:34:15 PM
    Author     : Stephen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="discussion.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Thread</title>
    </head>
    <body>
        <br/>
        <div id="create">
            <h1>New Thread</h1>
            <br/>
            
        <form action ="insertThread" method="POST">
            <textarea autofocus="true" rows="4" name="content"></textarea>
            <input type ="submit" value="Add Post" class="submit" />
        </form>
        </div>
        <br/>
    </body>
</html>
