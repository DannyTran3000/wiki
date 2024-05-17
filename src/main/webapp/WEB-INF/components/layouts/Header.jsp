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
    <c:if test="page_name not eq 'Articles'">
      <div class="header__nav-list" data-align="center">
        <%@ include file="./forms/SearchBox.jsp" %>
      </div>
    </c:if>
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