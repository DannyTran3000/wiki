<div id="article-filter" class="article-filter">
  <form id="article-filter-form" data-type="${page_name == 'Admin Articles' ? 'admin' : 'none'}"
    onsubmit="return filterArticle()">
    <div class="article-filter__list">
      <div class="article-filter__item">
        <div class="input">
          <input type="text" name="keyword" placeholder="Search a keyword ..."
            value="${filter.keyword != null ? filter.keyword : ''}">
        </div>
      </div>
      <div class="article-filter__item">
        <div class="select" data-target="category">
          <input type="hidden" name="category" value="${filter.category != null ? filter.category : ''}">
          <div class="select__toggle">${filter.category != null ? filter.categoryValue : 'Choose a category'}</div>
          <div class="select__options">
            <c:forEach var="category" items="${categories}">
              <div class="select__option" data-value="${category.slug}" data-display="${category.name}">
                ${category.name}
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
      <div class="article-filter__item">
        <div class="select" data-target="sort">
          <input type="hidden" name="sort" value="${filter.sort != null ? filter.sort : 'date'}">
          <div class="select__toggle">${filter.sort != null ? filter.sortValue : 'Sort by date'}</div>
          <div class="select__options">
            <div class="select__option" data-value="date" data-display="Sort by date">Date</div>
            <div class="select__option" data-value="title" data-display="Sort by name">Name</div>
            <div class="select__option" data-value="views" data-display="Sort by views">Views</div>
          </div>
        </div>
      </div>
      <div class="article-filter__item">
        <button class="article-filter__secondary" type="button" onclick="return resetFilterArticle()">Reset
          filter</button>
        <button class="article-filter__primary" type="submit">Search</button>
      </div>
      <c:if test="${page_name eq 'Admin Articles'}">
        <div class="article-filter__item">
          <div class="input input--checkbox">
            <c:if test="${filter.showHidden == 'true'}">
              <input id="show-hidden" type="checkbox" name="show-hidden" checked>
            </c:if>
            <c:if test="${filter.showHidden != 'true'}">
              <input id="show-hidden" type="checkbox" name="show-hidden">
            </c:if>
            <label for="show-hidden">Show hidden articles only</label>
          </div>
        </div>
      </c:if>
    </div>
  </form>
</div>