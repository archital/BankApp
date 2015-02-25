<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 21.02.15
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; charset = utf-8">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="style/bank.css">
    <script type="text/javascript" src="style/validation.js"></script>
</head>
<body>



<form id="form" action="saveClient" method="POST">
    <div class="tableClient">
        <table>
            <tr>
                <td colspan=3 style="font-weight:bold;">
                    <a class="explainText">Пожалуйста заполните форму для добавления клиента:</a>
                </td>
            </tr>

            <tr>
                <td colspan=2>
                    <input type="hidden" name="id" value="${client.id}">

                </td>
            </tr>

            <tr>
                <td>
                    Полное Имя:
                </td>
                <td colspan=2>
                    <input type="text" name="name" value="${client.name}" class="inputClient" id = "addName"/>
                </td>
            </tr>
            <tr id="addNameFindError" class="error"></tr>
            <tr>
                <td>
                    Город:
                </td>
                <td colspan=2>
                    <input type="text" name= "city" class="inputClient" id="addCity" value="${client.city}"/>

                </td>
            </tr>
            <tr id="addCityError" class="error"></tr>
            <tr>
                <td>Пол:

                </td>
                <td>
                    <input type="radio" name="gender" id="f" ${client.gender=="FEMALE"?"checked":""}/>
                    Женский </td>
                <td>
                    <input type="radio" name="gender" id="m" ${client.gender=="MALE"?"checked":""}/>
                    Мужской</td>
            </tr>
            <tr id="genderError" class="error"></tr>

            <tr>
                <td>E-mail:
                </td>
                <td colspan=2 style="font-weight:bold;">
                    <input type="text" name= "email" class="inputClient" id="email" value="${client.email}"/>
                <td id="emailError" class="error"></td>
                </td>
            </tr>

            <tr>
                <td>
                    Кредитный счет:
                </td>
                <td><input type="checkbox" name="check" id="check"/>
                </td>
                <td>
                    Открыть
                </td>
            </tr>
            <tr id="checkError" class="error"></tr>

            <tr>
                <td>Начальный баланс:
                </td>
                <td colspan=2 style="font-weight:bold;">
                    <input type="text" name= "balance" class="inputClient" id="startBalance" value="${client.balance}"/>
                </td>
            </tr>

            </tr>

            <td colspan=3 style="font-weight:bold;"  >
                <input type="submit" onclick="return addClient();" value="добавить"  style=" margin-left:35%;" class="buttons">
            </td>
            </tr>

        </table>

    </div>

</form>

</body>
</html>













