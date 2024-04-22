<c:if test="${empty user_email}">
  <div id="forgot-password-modal" class="modal" data-toggle="false" onclick="hideModal('forgot-password-modal')">
    <div class="modal__dialog" onclick="event.stopPropagation()">
      <form id="forgot-password-form" onsubmit="return forgotPassword()">
        <div class="login-form">
          <div class="login-form__header">
            <img class="login-form__logo" src="https://wiki-portal.strawflag.com/images/logo.png"
              alt="Wiki Portal Logo">
            <h5 class="login-form__heading">Forgot Password</h5>
            <span class="login-form__description">Enter your email address below to reset your password.</span>
          </div>
          <div class="login-form__content">
            <div class="input-group login-form__input-group">
              <label>Email Address <b style="color: red">*</b></label>
              <input type="text" name="email">
              <span class="input-group__note"></span>
            </div>
          </div>
          <div class="login-form__footer">
            <button class="login-form__submit" type="submit">Send</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</c:if>