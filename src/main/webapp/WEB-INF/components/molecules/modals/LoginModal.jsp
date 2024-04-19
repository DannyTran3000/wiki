<div id="login-modal" class="modal" data-toggle="false" onclick="hideModal('login-modal')">
  <div class="modal__dialog" onclick="event.stopPropagation()">
    <form id="login-form" onsubmit="return login()">
      <div class="login-form">
        <div class="login-form__header">
          <img class="login-form__logo" src="https://wiki-portal.strawflag.com/images/logo.png" alt="Wiki Portal Logo">
          <h5 class="login-form__heading">Login to your Account</h5>
          <span class="login-form__description">Welcome back, please enter your details.</span>
        </div>
        <div class="login-form__content">
          <div class="input-group login-form__input-group">
            <label>Email Address</label>
            <input type="text" name="email">
            <span class="input-group__note"></span>
          </div>
          <div class="input-group input-group--action login-form__input-group">
            <label>Password</label>
            <input type="password" name="password">
            <span class="input-group__trigger-password" data-toggle="false">Show</span>
          </div>
          <div class="checkbox-group login-form__input-group">
            <input type="checkbox" name="remember" id="remember-login-checkbox">
            <label for="remember-login-checkbox">Remember me</label>
          </div>
        </div>
        <div class="login-form__footer">
          <button class="login-form__submit" type="submit">Login</button>
          <p class="login-form__credit">Don't have an account? <b
              onclick="switchModal('login-modal', 'register-modal')">Sign Up</b></p>
        </div>
      </div>
    </form>
  </div>
</div>