<c:if test="${not empty user_email}">
  <div id="change-password-modal" class="modal" data-toggle="false" onclick="hideModal('change-password-modal')">
    <div class="modal__dialog" onclick="event.stopPropagation()">
      <form id="change-password-form" onsubmit="return changePassword()">
        <div class="login-form">
          <div class="login-form__header">
            <img class="login-form__logo" src="https://wiki-portal.strawflag.com/images/logo.png"
              alt="Wiki Portal Logo">
            <h5 class="login-form__heading">Change Your Password</h5>
            <span class="login-form__description">Change password to make your account more secure.</span>
          </div>
          <div class="login-form__content">
            <input type="hidden" name="email" value="${user_email}">
            <div class="input-group input-group--action login-form__input-group">
              <label>Current Password <b style="color: red">*</b></label>
              <input type="password" name="password">
              <span class="input-group__trigger-password" data-toggle="false">Show</span>
              <span class="input-group__note"></span>
            </div>
            <div class="input-group input-group--action login-form__input-group">
              <label>New Password <b style="color: red">*</b></label>
              <input type="password" name="new-password">
              <span class="input-group__trigger-password" data-toggle="false">Show</span>
              <span class="input-group__note"></span>
            </div>
            <div class="input-group input-group--action login-form__input-group">
              <label>Confirm New Password <b style="color: red">*</b></label>
              <input type="password" name="confirm-new-password">
              <span class="input-group__trigger-password" data-toggle="false">Show</span>
              <span class="input-group__note"></span>
            </div>
          </div>
          <div class="login-form__footer">
            <button class="login-form__submit" type="submit">Save</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</c:if>