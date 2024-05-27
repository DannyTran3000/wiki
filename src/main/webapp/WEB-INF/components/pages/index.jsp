<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% request.setAttribute("page_name", "Home"); %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Wiki Portal - The Free Encyclopedia</title>

  <%@ include file="../meta/Links.jsp" %>
</head>

<body>
  <div id="${page_name}" class="wrapper">
    <%@ include file="../layouts/Header.jsp" %>
    <main>
      <%@ include file="../layouts/sections/Hero.jsp" %>
      <%@ include file="../layouts/sections/HotArticles.jsp" %>
      <%@ include file="../layouts/sections/LatestArticles.jsp" %>
      <%@ include file="../layouts/sections/Categories.jsp" %>
    </main>
    <%@ include file="../layouts/Footer.jsp" %>

    <%@ include file="../layouts/modals/ChangePasswordModal.jsp" %>
    <%@ include file="../layouts/modals/ForgotPasswordModal.jsp" %>
    <%@ include file="../layouts/modals/RegisterModal.jsp" %>
    <%@ include file="../layouts/modals/LoginModal.jsp" %>
  </div>

  <%@ include file="../meta/Scripts.jsp" %>
</body>

</html>