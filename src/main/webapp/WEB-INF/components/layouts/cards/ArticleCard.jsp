<div class="article-card">
  <div class="article-card__thumbnail">
    <img src="${article.thumbnail}" alt="${article.title}">
  </div>
  <div class="article-card__info">
    <h5 class="article-card__title">${article.title}</h5>
    <div class="article-card__figure">
      <div class="article-card__figure-view">
        <svg width="16" height="17" viewBox="0 0 16 17" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M8 3.54407C4.36363 3.54407 1.25819 5.61738 0 8.54407C1.25819 11.4707 4.36363 13.5441 8 13.5441C11.64 13.5441 14.7418 11.4707 16 8.54407C14.7418 5.61738 11.64 3.54407 8 3.54407ZM8 11.8774C5.99273 11.8774 4.36363 10.384 4.36363 8.54404C4.36363 6.70404 5.99273 5.21072 8 5.21072C10.0073 5.21072 11.6364 6.70407 11.6364 8.54407C11.6364 10.3841 10.0073 11.8774 8 11.8774Z"
            fill="#999FAA" />
          <circle cx="8" cy="8.54407" r="3" stroke="#999FAA" stroke-width="2" />
        </svg>
        <span>${article.views}</span>
      </div>
      <div class="article-card__figure-date">
        Published on ${article.createdAt}
      </div>
    </div>
    <p class="article-card__description">${article.description}</p>
    <div class="article-card__action">
      <a href="/wiki-portal/articles${article.pathname}" class="article-card__link">Read more</a>
    </div>
  </div>
</div>