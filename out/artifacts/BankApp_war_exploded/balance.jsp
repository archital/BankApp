<%--
  Created by IntelliJ IDEA.
  User: SCJP
  Date: 20.02.2015
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; charset = utf-8">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="style/bank.css">
    <script type="text/javascript" src="style/validation1.js"></script>
</head>
<body>

<form action="menu.html" onsubmit="back">


    <div class="loginTable">
        <table>
            <tr>
                <td colspan="2"><a  class="explainText" style="display: block; margin: 0 auto;">  Client: <%=session.getAttribute("clientName")%> </a></td>
            </tr>
            <tr>
                <td colspan="2"><a  class="explainText" style="display: block; margin: 0 auto;">Balance: <%=request.getAttribute("balance")%></a></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="back" class="buttons" style="display: block; margin: 0 auto;"></td>
            </tr>
        </table>

    </div>



</form>

</body>
</html>
