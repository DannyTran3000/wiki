<a href="/wiki-portal/admin/categories/create" class="table__link">Create a new category <b>+</b></a>
<div class="table">
  <div class="table__tr">
    <div class="table__th" style="width: 5%;">#</div>
    <div class="table__th" style="flex: 1;">Name</div>
    <div class="table__th" style="width: 10%;">Icon</div>
    <div class="table__th" style="width: 25%;">Slug</div>
    <div class="table__th" style="width: 15%;">Created Date</div>
    <div class="table__th" style="width: 10%;"></div>
  </div>
  <c:if test="${not empty categories}">
    <c:forEach var="categoryRow" items="${categories}" varStatus="status">
      <div class="table__tr">
        <div class="table__td" style="width: 5%;">
          <div class="table__td-content">${status.index + 1}</div>
        </div>
        <div class="table__td" style="flex: 1">
          <div class="table__td-content" title="${categoryRow.name}">${categoryRow.name}</div>
        </div>
        <div class="table__td" style="width: 10%;">
          <img src="${categoryRow.icon}" alt="${categoryRow.name}" style="width: 4rem; height: 4rem;">
        </div>
        <div class="table__td" style="width: 25%;">
          <div class="table__td-content" title="${categoryRow.slug}">${categoryRow.slug}</div>
        </div>
        <div class="table__td" style="width: 15%;">
          <div class="table__td-content" title="${categoryRow.createdAt}">${categoryRow.createdAt}</div>
        </div>
        <div class="table__td" style="width: 10%;">
          <div class="dropdown" style="display: inline;">
            <a href="javascript:void(0);" class="dropdown__toggle">Action</a>
            <div class="dropdown__content">
              <div class="dropdown__option">
                <a href="/wiki-portal/admin/categories/${categoryRow.slug}" class="dropdown__option-content">Edit</a>
              </div>
              <div class="dropdown__option">
                <span class="dropdown__option-content" data-value="${categoryRow.slug}"
                  onclick="deleteCategory(event)">Delete</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </c:if>
</div>