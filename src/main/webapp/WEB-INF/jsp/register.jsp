<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");
%>
<html lang="pl">

<head>
    <c:url value="/css/login.css" var="jstlCss" />
    <c:url value="/js/register.js" var="javaScript" />
    <c:url value="/img/icon.png" var="icon" />
    <link href="${jstlCss}" rel="stylesheet" />
    <link href="${icon}" rel="shortcut icon">
    <link href='http://fonts.googleapis.com/css?family=Barlow&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="${javaScript}"></script>
    <title>FilmRecommender - Register</title>
</head>

<body>
    <div id="backgroundContainer"></div>
    <div class="container">
        <div id="banner">
            ${banner.printBanner()}
        </div>
        <div id="mainbody">
            <div class="login-container">
                <h3 class="title">Rejestracja w systemie</h3>
                <form action="none">
                    <div id="err-msg">Podano błędny login lub hasło</div>
                    <input type="text" class="form-field" placeholder="Twój nick *" value="" name="nick" id="nick"/>
                    <input type="text" class="form-field" placeholder="Twoje imię *" value="" name="name" id="name"/>
                    <input type="text" class="form-field" placeholder="Twoje nazwisko *" value="" name="lastname" id="lastname"/>
                    <input type="email" class="form-field" placeholder="Twój e-mail *" value="" name="email" id="email"/>
                    <input type="email" class="form-field" placeholder="Potwierdź e-mail *" value="" name="confemail" id="confemail"/>
                    <input type="password" class="form-field" placeholder="Twoje hasło *" value="" name="pass" id="pass"/>
                    <input type="password" class="form-field" placeholder="Potwierdź hasło *" value="" name="confpass" id="confpass"/>
                    <input type="button" id="subbutton" class="form-btn-submit" value="Zarejestruj" />
                </form>
            </div>
        </div>
        <div id="footer">
            <!--© Wszelkie prawa zastrzeżone-->
        </div>
    </div>
</body>


</html>