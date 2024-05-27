<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% request.setAttribute("page_name", "Admin Articles"); %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Articles | Admin - Wiki Portal</title>

  <%@ include file="../../meta/Links.jsp" %>
</head>

<body>
  <div id="${page_name}" class="wrapper">
    <%@ include file="../../layouts/AdminHeader.jsp" %>
    <main>
      <section style="max-width: 114rem; min-height: calc(100vh - 28.6rem); margin: 0 auto; padding-top: 12rem;">
        <h2 class="section__heading" style="margin-bottom: 2.4rem;">Articles</h2>
        <%@ include file="../../layouts/tables/ArticleTable.jsp" %>
      </section>
    </main>
    <%@ include file="../../layouts/Footer.jsp" %>

    <%@ include file="../../layouts/modals/ChangePasswordModal.jsp" %>
    <%@ include file="../../layouts/modals/ForgotPasswordModal.jsp" %>
    <%@ include file="../../layouts/modals/RegisterModal.jsp" %>
    <%@ include file="../../layouts/modals/LoginModal.jsp" %>
  </div>

  <%@ include file="../../meta/Scripts.jsp" %>
</body>

</html>