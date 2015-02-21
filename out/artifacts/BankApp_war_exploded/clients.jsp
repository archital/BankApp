

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
<head>

    <meta http-equiv="CONTENT-TYPE" content="text/html; charset = utf-8">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="style/bank.css">
    <script type="text/javascript" src="style/validation1.js"></script>
</head>
<body>

<form action="/clients" method="post" onsubmit="поиск">





    <div class="results">



        <table>
            <tr>
                <td colspan=3 style="font-weight:bold;">
                    <h3 class="explainText">Поиск клиента:</h3>
                </td>
            </tr>

            <tr>
                <td>Город: <input type="text" name="city" id = "cityFind"/>
                </td>
                <td>Имя: <input type="text" name="name" id = "nameFind"/>
                </td>
                <td> <input type="submit" onclick="return checkClient();" value="поиск" class="buttons"></td>
            </tr>

            <tr id="cityFindError" class="error"></tr>
            <tr id="nameFindError" class="error"></tr>
            <tr>
                <th>Город</th>
                <th>Имя клиента</th>
                <th>Баланс</th>
            </tr>


            <c:forEach var="client" items="${clients}">

            <tr>
                <td> <c:out value="${client.city}"/></td>
                <td><a href="#"> <c:out value="${client.name}"/></a></td>
                <td><c:out value="${client.balance}"/><br></td>
            </tr>

            </c:forEach>

        </table>

    </div>

</form>

</body>
</html>
