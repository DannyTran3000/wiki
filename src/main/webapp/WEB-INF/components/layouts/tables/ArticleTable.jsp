<a href="/wiki-portal/admin/articles/create" class="table__link">Create a new article <b>+</b></a>
<div class="article__filter">
  <%@ include file="../forms/ArticleFilter.jsp" %>
</div>
<div class="table">
  <div class="table__tr">
    <div class="table__th" style="width: 5%;">#</div>
    <div class="table__th" style="flex: 1;">Title</div>
    <div class="table__th" style="width: 10%;">Thumbnail</div>
    <div class="table__th" style="width: 5%;">Views</div>
    <div class="table__th" style="width: 15%;">Slug</div>
    <div class="table__th" style="width: 15%;">Category</div>
    <div class="table__th" style="width: 10%;">Created Date</div>
    <div class="table__th" style="width: 10%;"></div>
  </div>
  <c:if test="${not empty filterArticles}">
    <c:forEach var="articleRow" items="${filterArticles}" varStatus="status">
      <div class="table__tr">
        <div class="table__td" style="width: 5%;">
          <div class="table__td-content">${pagination.offset + status.index + 1}</div>
        </div>
        <div class="table__td" style="flex: 1">
          <div class="table__td-content" title="${articleRow.title}">${articleRow.title}</div>
        </div>
        <div class="table__td" style="width: 10%;">
          <img src="${articleRow.thumbnail}" alt="${articleRow.title}" style="width: 4rem; height: 4rem;">
        </div>
        <div class=" table__td" style="width: 5%;">
          <div class="table__td-content" title="${articleRow.views}">${articleRow.views}</div>
        </div>
        <div class="table__td" style="width: 15%;">
          <div class="table__td-content" title="${articleRow.slug}">${articleRow.slug}</div>
        </div>
        <div class="table__td" style="width: 15%;">
          <div class="table__td-content" title="${articleRow.categoryName}">
            <a href="/wiki-portal/admin/categories/${articleRow.categorySlug}">${articleRow.categoryName}</a>
          </div>
        </div>
        <div class="table__td" style="width: 10%;">
          <div class="table__td-content" title="${articleRow.createdAt}">${articleRow.createdAt}</div>
        </div>
        <div class="table__td" style="width: 10%;">
          <div class="dropdown" style="display: inline;">
            <a href="javascript:void(0);" class="dropdown__toggle">Action</a>
            <div class="dropdown__content">
              <div class="dropdown__option">
                <a href="/wiki-portal/admin/articles/${articleRow.slug}" class="dropdown__option-content">Edit</a>
              </div>
              <div class="dropdown__option">
                <span class="dropdown__option-content" data-value="${articleRow.slug}"
                  onclick="deleteArticle(event)">Delete</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </c:if>
</div>

<c:if test="${not empty filterArticles}">
  <div class="article__pagination">
    <c:forEach var="page" items="${pagination.display}">
      <a href="javascript:void(0);" class="article__page-number ${pagination.currentPage eq page ? '--active' : ''}"
        data-value="${page}" onclick="paginationArticle(event, 'admin')">${page}</a>
    </c:forEach>
  </div>
</c:if>

<c:if test="${empty filterArticles}">
  <p>Not found</p>
</c:if>