<c:if test="${not empty filterArticles}">
  <section class="article ${page_name eq 'Articles' ? 'article--full' : ''}">
    <h2 class="section__heading">Articles</h2>
    <div class="article__filter">
      <%@ include file="../forms/ArticleFilter.jsp" %>
    </div>
    <ul class="article__list">
      <c:forEach var="articleCard" items="${filterArticles}">
        <li class="article__list-item">
          <%@ include file="../../layouts/cards/ArticleCard.jsp" %>
        </li>
      </c:forEach>
    </ul>
  </section>
</c:if>