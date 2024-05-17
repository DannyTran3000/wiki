<c:if test="${not empty categories}">
  <section class="category ${page_name eq 'Categories' ? 'category--full' : ''}">
    <h2 class="section__heading">Categories</h2>
    <ul class="category__list">
      <c:forEach var="categoryCard" items="${categories}">
        <li class="category__list-item">
          <%@ include file="../../layouts/cards/CategoryCard.jsp" %>
        </li>
      </c:forEach>
    </ul>
  </section>
</c:if>