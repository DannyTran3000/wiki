<header class="header">
  <nav class="header__nav">
    <ul class="header__nav-list" data-align="start">
      <li class="header__nav-item">
        <a href="/wiki-portal" class="header__nav-link">
          <img class="header__logo" src="https://wiki-portal.strawflag.com/images/logo.png" alt="Wiki Portal Logo">
        </a>
      </li>
      <li class="header__nav-item ${page_name eq 'Home' ? 'header__nav-item--active' : ''}">
        <a href="/wiki-portal" class="header__nav-link">Home</a>
      </li>
      <li class="header__nav-item ${page_name eq 'Categories' ? 'header__nav-item--active' : ''}">
        <a href="/wiki-portal/categories" class="header__nav-link">Categories</a>
      </li>
      <li class="header__nav-item ${page_name eq 'Articles' ? 'header__nav-item--active' : ''}">
        <a href="/wiki-portal/articles" class="header__nav-link">Articles</a>
      </li>
    </ul>
    <div class="header__nav-list" data-align="center">
      <div class="search-box">
        <div class="search-box__pre-icon">
          <svg width="37" height="39" viewBox="0 0 37 39" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M27.1322 15.1099C27.1322 21.8062 21.7218 27.2197 15.0661 27.2197C8.41038 27.2197 3 21.8062 3 15.1099C3 8.41356 8.41038 3 15.0661 3C21.7218 3 27.1322 8.41356 27.1322 15.1099ZM23.0749 27.9104C20.7549 29.3735 18.009 30.2197 15.0661 30.2197C6.74532 30.2197 0 23.4548 0 15.1099C0 6.76492 6.74532 0 15.0661 0C23.3869 0 30.1322 6.76492 30.1322 15.1099C30.1322 19.4148 28.3371 23.2993 25.4566 26.0514L35.7949 36.4616L33.6809 38.5903L23.0749 27.9104Z"
              fill="#C4C4C4" />
          </svg>
        </div>
        <input id="search-box" type="text" class="search-box__input" placeholder="Hello world from Wiki Portal ...">
      </div>
    </div>
    <ul class="header__nav-list" data-align="end">
      <c:if test="${empty user.fullname}">
        <li class="header__nav-item">
          <a href="javascript:void(0);" class="header__nav-link" onclick="showModal('register-modal')">
            Create an account
          </a>
        </li>
        <li class="header__nav-item">
          <a href="javascript:void(0);" class="header__nav-link" onclick="showModal('login-modal')">
            Login
          </a>
        </li>
      </c:if>
      <c:if test="${not empty user.fullname}">
        <li class="header__nav-item">
          <div class="dropdown">
            <a href="javascript:void(0);" class="dropdown__toggle header__nav-link">
              Hello <b>${user.fullname}</b>
            </a>
            <div class="dropdown__content">
              <c:if test="${user.role eq 0}">
                <div class="dropdown__option">
                  <a href="/wiki-portal/admin" class="dropdown__option-content">Admin</a>
                </div>
              </c:if>
              <div class="dropdown__option">
                <span class="dropdown__option-content" onclick="showModal('change-password-modal')">Change
                  password</span>
              </div>
              <div class="dropdown__option">
                <span class="dropdown__option-content" onclick="logout()">Logout</span>
              </div>
            </div>
          </div>
        </li>
      </c:if>
    </ul>
  </nav>
</header>