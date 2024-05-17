<a class="category-card" href="/wiki-portal/articles?category=${categoryCard.slug}">
  <div class="category-card__left">
    <img src="${categoryCard.icon}" alt="${categoryCard.name}"
      class="category-card__icon">
    <span class="category-card__name">${categoryCard.name}</span>
  </div>
  <div class="category-card__right">
    <div class="category-card__badge">${categoryCard.formatArticleCount}</div>
    <span class="category-card__tag">${categoryCard.articleCount > 1 ? 'articles' : 'article'}</span>
  </div>
</a>