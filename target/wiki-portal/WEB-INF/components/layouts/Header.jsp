<header class="header">
  <nav class="header__nav">
    <ul class="header__nav-list" data-align="start">
      <li class="header__nav-item header__nav-item--active"><a href="/wiki-portal" class="header__nav-link">Home</a></li>
      <!-- <li class="header__nav-item"><a href="/categories" class="header__nav-link">Categories</a></li>
      <li class="header__nav-item"><a href="/articles" class="header__nav-link">Articles</a></li> -->
    </ul>
    <ul class="header__nav-list" data-align="end">
      <c:if test="${empty user_fullname}">
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
      <c:if test="${not empty user_fullname}">
        <li class="header__nav-item">
          <div class="dropdown">
            <a href="javascript:void(0);" class="dropdown__toggle header__nav-link">
              Hello <b>${user_fullname}</b>
            </a>
            <div class="dropdown__content">
              <div class="dropdown__option">
                <span class="dropdown__option-content">Profile</span>
              </div>
              <div class="dropdown__option">
                <span class="dropdown__option-content">Change password</span>
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