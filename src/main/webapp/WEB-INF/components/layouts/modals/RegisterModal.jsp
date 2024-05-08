<c:if test="${empty user.email}">
  <div id="register-modal" class="modal" data-toggle="false" onclick="hideModal('register-modal')">
    <div class="modal__dialog" onclick="event.stopPropagation()">
      <form id="register-form" onsubmit="return register()">
        <div class="login-form">
          <div class="login-form__header">
            <img class="login-form__logo" src="https://wiki-portal.strawflag.com/images/logo.png"
              alt="Wiki Portal Logo">
            <h5 class="login-form__heading">Create an Account</h5>
            <span class="login-form__description">Sign up now to get started with an account.</span>
          </div>
          <div class="login-form__content">
            <div class="input-group login-form__input-group">
              <label>Full Name <b style="color: red">*</b></label>
              <input type="text" name="fullname">
              <span class="input-group__note"></span>
            </div>
            <div class="input-group login-form__input-group">
              <label>Email Address <b style="color: red">*</b></label>
              <input type="text" name="email">
              <span class="input-group__note"></span>
            </div>
            <div class="input-group input-group--action login-form__input-group">
              <label>Password <b style="color: red">*</b></label>
              <input type="password" name="password">
              <span class="input-group__trigger-password" data-toggle="false">Show</span>
              <span class="input-group__note"></span>
            </div>
            <div class="input-group input-group--action login-form__input-group">
              <label>Confirm Password <b style="color: red">*</b></label>
              <input type="password" name="confirm-password">
              <span class="input-group__trigger-password" data-toggle="false">Show</span>
              <span class="input-group__note"></span>
            </div>
          </div>
          <div class="login-form__footer">
            <button class="login-form__submit" type="submit">Get Started</button>
            <p class="login-form__credit">Already have an account? <b
                onclick="switchModal('register-modal', 'login-modal')">Login</b></p>
          </div>
        </div>
      </form>
    </div>
  </div>
</c:if>