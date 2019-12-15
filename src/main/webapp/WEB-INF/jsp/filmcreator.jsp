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
    <c:url value="/css/main.css" var="jstlCss" />
    <c:url value="/js/main.js" var="javaScript" />
    <c:url value="/js/filmcreator.js" var="filmcreator" />
    <c:url value="/img/icon.png" var="icon" />
    <link href="${jstlCss}" rel="stylesheet" />
    <link href="${icon}" rel="shortcut icon">
    <link href='http://fonts.googleapis.com/css?family=Barlow&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="${javaScript}"></script>
    <script src="${filmcreator}"></script>
    <title>FilmRecommender - Film Creator</title>
</head>

<body>
    <div id="backgroundContainer"></div>
    <div class="container">
        <div id="mainbody">
            <input id="tytul" name="tytul" placeholder="Tytuł" required><br>
            <input id="zdjecie" name="zdjecie" placeholder="Adres Zdjęcia" required><div id="zdj"></div><br>
            <input id="gatunek" name="gatunek" placeholder="Gatunek Filmu" required><br>
            <input id="rokProdukcji" type="number" name="rokProdukcji" placeholder="Rok Produkcji" required><br>
            <input id="submit" type="submit">
        </div>
        <div id="footer">
            © Wszelkie prawa zastrzeżone
        </div>
    </div>
</body>

</html>