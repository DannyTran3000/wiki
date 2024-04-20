<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Wiki Portal - The Free Encyclopedia</title>

  <%@ include file="../molecules/meta/Links.jsp" %>
</head>

<body>
  <div id="home" class="wrapper">
    <%@ include file="../molecules/Header.jsp" %>
      <main>
        <%@ include file="../organisms/Hero.jsp" %>
      </main>
      <%@ include file="../molecules/modals/RegisterModal.jsp" %>
      <%@ include file="../molecules/modals/LoginModal.jsp" %>
  </div>

  <%@ include file="../molecules/meta/Scripts.jsp" %>
</body>

</html>