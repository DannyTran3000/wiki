<div id="article-filter" class="article-filter">
  <div class="article-filter__list">
    <div class="article-filter__item">
      <div class="input">
        <input type="text" name="keyword" placeholder="Search a keyword ...">
      </div>
    </div>
    <div class="article-filter__item">
      <input type="hidden" name="category">
      <div class="select" data-target="category">
        <div class="select__toggle">Choose a category</div>
        <div class="select__options">
          <c:forEach var="category" items="${categories}">
            <div class="select__option" data-value="${category.slug}">${category.name}</div>
          </c:forEach>
        </div>
      </div>
    </div>
    <div class="article-filter__item">
      <input type="hidden" name="sort">
      <div class="select" data-target="sort">
        <div class="select__toggle">Sort by date</div>
        <div class="select__options">
          <div class="select__option" data-value="date">Date</div>
          <div class="select__option" data-value="title">Name</div>
          <div class="select__option" data-value="views">Views</div>
          <div class="select__option" data-value="views">Views</div>
        </div>
      </div>
    </div>
  </div>
</div>