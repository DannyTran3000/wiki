<c:if test="${not empty highestViewsArticles}">
  <section class="hot-article">
    <h2 class="section__heading">Trend Article</h2>
    <ul class="hot-article__list">
      <c:forEach var="articlePrimaryCard" items="${highestViewsArticles}">
        <li class="hot-article__list-item">
          <%@ include file="../../layouts/cards/ArticlePrimaryCard.jsp" %>
        </li>
      </c:forEach>
    </ul>
  </section>
</c:if>