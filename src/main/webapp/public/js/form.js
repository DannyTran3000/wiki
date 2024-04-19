const login = () => {
  const form = document.querySelector('#login-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const emailInput = form.querySelector('input[name="email"]')
  const passwordInput = form.querySelector('input[name="password"]')

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
  }

  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')
  const submitBtn = form.querySelector('button[type="submit"]')
  if (submitBtn) submitBtn.setAttribute('data-loading', 'true')

  sendRequest(
    'http://localhost:8080/wiki-portal/login',
    'POST',
    JSON.stringify(data),
    (data) => {
      setTimeout(() => {
        body.setAttribute('data-loading', 'false')
        if (submitBtn) submitBtn.setAttribute('data-loading', 'false')
      }, 1500)

      console.log(data)
    })

  return false
}

const validateEmail = (email) => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailPattern.test(email);
}