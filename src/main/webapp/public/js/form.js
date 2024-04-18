const login = () => {
  const form = document.querySelector('#login-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const emailInput = form.querySelector('input[name="email"]')
  const passwordInput = form.querySelector('input[name="password"]')
  const rememberCheckbox = form.querySelector('input[name="remember"]')

  const isValidEmail = validateEmail(emailInput?.value)

  if (!isValidEmail) {
    console.log('Warning: This email is invalid.')
    const emailInputNote = emailInput.closest('.input-group').querySelector('.input-group__note')
    emailInputNote.innerHTML = 'Warning: This email is invalid.'
    emailInputNote.style.color = 'red'
    return false
  }

  const data = {
    email: emailInput?.value,
    password: passwordInput?.value,
    rememberMe: rememberCheckbox?.checked ? 1 : 0,
  }
  console.log(data)

  return false
}

const validateEmail = (email) => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailPattern.test(email);
}