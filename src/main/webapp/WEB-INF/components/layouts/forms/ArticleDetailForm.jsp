<c:if test="${mode eq 'edit'}">
  <c:if test="${not empty single}">
    <div id="article-detail-form" class="detail-form">
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Title</label>
          <input type="text" name="title" value="${single.title != null ? single.title : ''}"
            placeholder="Enter article title">
        </div>
      </div>
      <div class="detail-form__group">
        <div class="select" data-target="category">
          <input type="hidden" name="category" value="${single.categoryId != null ? single.categoryId : ''}">
          <div class="select__toggle">${single.categoryName != null ? single.categoryName : 'Choose a category'}</div>
          <div class="select__options">
            <c:forEach var="category" items="${categories}">
              <div class="select__option" data-value="${category.id}" data-display="${category.name}">
                ${category.name}
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Thumbnail</label>
          <input type="text" name="thumbnail" value="${single.thumbnail != null ? single.thumbnail : ''}"
            placeholder="Enter article thumbnail URL">
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Description</label>
          <textarea name="description"
            placeholder="Enter article description">${single.description != null ? single.description : ''}</textarea>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Content</label>
          <textarea name="content"
            placeholder="Enter article content">${single.content != null ? single.content : ''}</textarea>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Views</label>
          <input type="text" name="views" value="${single.views != null ? single.views : ''}" disabled>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Slug</label>
          <input type="text" name="slug" value="${single.slug != null ? single.slug : ''}" disabled>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input">
          <label class="input__label">Created Date</label>
          <input type="text" value="${single.createdAt != null ? single.createdAt : ''}" disabled>
        </div>
      </div>
      <div class="detail-form__group">
        <div class="input input--checkbox">
          <c:if test="${single.status != 1}">
            <input id="show-hidden" type="checkbox" name="show-hidden" checked>
          </c:if>
          <c:if test="${single.status == 1}">
            <input id="show-hidden" type="checkbox" name="show-hidden">
          </c:if>
          <label for="show-hidden">Hide this article</label>
        </div>
      </div>
      <div class="detail-form__action">
        <button class="detail-form__primary" type="submit" onclick="updateArticle()">Save</button>
        <button class="detail-form__secondary" type="button" onclick="window.location.reload()">Reset</button>
        <button class="detail-form__secondary" type="button"
          onclick="window.location.href='/wiki-portal/admin/articles'">Back to list</button>
      </div>
    </div>
  </c:if>
</c:if>

<c:if test="${mode eq 'create'}">
  <div id="article-detail-form" class="detail-form">
    <div class="detail-form__group">
      <div class="input">
        <label class="input__label">Title</label>
        <input type="text" name="title" value="${single.title != null ? single.title : ''}"
          placeholder="Enter article title">
      </div>
    </div>
    <div class="detail-form__group">
      <div class="select" data-target="category">
        <input type="hidden" name="category" value="${single.categoryId != null ? single.categoryId : ''}">
        <div class="select__toggle">${single.categoryName != null ? single.categoryName : 'Choose a category'}</div>
        <div class="select__options">
          <c:forEach var="category" items="${categories}">
            <div class="select__option" data-value="${category.id}" data-display="${category.name}">
              ${category.name}
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
    <div class="detail-form__group">
      <div class="input">
        <label class="input__label">Thumbnail</label>
        <input type="text" name="thumbnail" value="${single.thumbnail != null ? single.thumbnail : ''}"
          placeholder="Enter article thumbnail URL">
      </div>
    </div>
    <div class="detail-form__group">
      <div class="input">
        <label class="input__label">Description</label>
        <textarea name="description"
          placeholder="Enter article description">${single.description != null ? single.description : ''}</textarea>
      </div>
    </div>
    <div class="detail-form__group">
      <div class="input">
        <label class="input__label">Content</label>
        <textarea name="content"
          placeholder="Enter article content">${single.content != null ? single.content : ''}</textarea>
      </div>
    </div>
    <div class="detail-form__action">
      <button class="detail-form__primary" type="submit" onclick="createArticle()">Save</button>
      <button class="detail-form__secondary" type="button"
        onclick="window.location.href='/wiki-portal/admin/articles'">Back to list</button>
    </div>
  </div>
</c:if>