<c:if test="${not empty latestArticles}">
  <section class="article">
    <h2 class="section__heading">Latest Articles</h2>
    <ul class="article__list">
      <c:forEach var="articleSecondaryCard" items="${latestArticles}">
        <li class="article__list-item">
          <%@ include file="../../layouts/cards/ArticleSecondaryCard.jsp" %>
        </li>
      </c:forEach>
    </ul>
  </section>
</c:if>