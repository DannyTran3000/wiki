<section class="article ${page_name eq 'Articles' ? 'article--full' : ''}">
  <h2 class="section__heading">Articles</h2>
  <div class="article__filter">
    <%@ include file="../forms/ArticleFilter.jsp" %>
  </div>
  <c:if test="${not empty filterArticles}">
    <ul class="article__list">
      <c:forEach var="articleCard" items="${filterArticles}">
        <li class="article__list-item">
          <%@ include file="../../layouts/cards/ArticleCard.jsp" %>
        </li>
      </c:forEach>
    </ul>
    <div class="article__pagination">
      <c:forEach var="page" items="${pagination.display}">
        <a href="javascript:void(0);" class="article__page-number ${pagination.currentPage eq page ? '--active' : ''}" data-value="${page}"
          onclick="paginationArticle(event)">${page}</a>
      </c:forEach>
    </div>
  </c:if>
  <c:if test="${empty filterArticles}">
    <p>Not found</p>
  </c:if>
</section>