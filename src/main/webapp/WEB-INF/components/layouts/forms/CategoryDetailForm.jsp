<c:if test="${mode eq 'edit'}">
  <c:if test="${not empty single}">
    <div id="category-detail-form" class="detail-form">
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Name</label>
          <input type="text" name="name" value="${single.name != null ? single.name : ''}"
            placeholder="Enter category name">
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">icon</label>
          <input type="text" name="icon" value="${single.icon != null ? single.icon : ''}"
            placeholder="Enter category icon URL">
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">slug</label>
          <input type="text" name="slug" value="${single.slug != null ? single.slug : ''}" disabled>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Created Date</label>
          <input type="text" value="${single.createdAt != null ? single.createdAt : ''}" disabled>
        </div>
      </div>
      <div class="detail-form__action">
        <button class="detail-form__primary" type="submit" onclick="updateCategory()">Save</button>
        <button class="detail-form__secondary" type="button" onclick="window.location.reload()">Reset</button>
        <button class="detail-form__secondary" type="button"
          onclick="window.location.href='/wiki-portal/admin/categories'">Back to list</button>
      </div>
    </div>
  </c:if>
</c:if>

<c:if test="${mode eq 'create'}">
  <div id="category-detail-form" class="detail-form">
    <div class="detail-form__group">
      <div class="input">
        <label class="input__label">Name</label>
        <input type="text" name="name" placeholder="Enter category name">
      </div>
    </div>
    <div class="detail-form__group">
      <div class="input">
        <label class="input__label">icon</label>
        <input type="text" name="icon" placeholder="Enter category icon URL">
      </div>
    </div>
    <div class="detail-form__action">
      <button class="detail-form__primary" type="submit" onclick="createCategory()">Save</button>
      <button class="detail-form__secondary" type="button"
        onclick="window.location.href='/wiki-portal/admin/categories'">Back to list</button>
    </div>
  </div>
</c:if>