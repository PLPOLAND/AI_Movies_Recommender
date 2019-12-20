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
    <c:url value="/css/films.css" var="filmy" />
    <c:url value="/js/main.js" var="javaScript" />
    <c:url value="/img/icon.png" var="icon" />
    <c:url value="/css/fontello.css" var="fontello" />
    <link href="${fontello}" rel="stylesheet" />
    <link href="${jstlCss}" rel="stylesheet" />
    <link href="${filmy}" rel="stylesheet" />
    <link href="${icon}" rel="shortcut icon">
    <link href='http://fonts.googleapis.com/css?family=Barlow&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="${javaScript}"></script>
    <title>FilmRecommender</title>
</head>

<body>
    <div id="backgroundContainer"></div>
    <div class="container">
        <div id="banner">
            ${banner.printBanner()}
        </div>
        <div id="mainbody">
            <div class="film">
                <div class="film-img" style="background-image: url(https://assets.upflix.pl/media/plakat/2017/realityhigh__300_427.jpg)">
                    <div class="ocena">
                        <div class="ocena-icon">
                            <i class="icon icon-like"></i>
                        </div>
                        <div class="ocena-icon">
                            <i class="icon icon-dislike"></i>
                        </div>
                    </div>
                </div>
                <!-- <div class="film-title">
                    Avatar
                </div>
                <div class="film-gatunek">
                    Komedia
                </div>
                <div class="film-rok">
                    1998
                </div> -->
            </div>
        </div>
        <div id="footer">
            © Wszelkie prawa zastrzeżone
        </div>
    </div>
</body>


</html>