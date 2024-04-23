const changePassword = () => {
  const form = document.querySelector('#change-password-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const emailInput = form.querySelector('input[name="email"]')
  const passwordInput = form.querySelector('input[name="password"]')
  const newPasswordInput = form.querySelector('input[name="new-password"]')
  const confirmNewPasswordInput = form.querySelector('input[name="confirm-new-password"]')

  const email = emailInput?.value
  const password = passwordInput?.value
  const newPassword = newPasswordInput?.value
  const confirmNewPassword = confirmNewPasswordInput?.value

  if (!email) {
    console.log('Email not found');
    return false
  }

  if ([password, newPassword, confirmNewPassword].includes('')) {
    if (!password) noteInput(passwordInput, 'Please ensure this field is not empty!')
    if (!newPassword) noteInput(newPasswordInput, 'Please ensure this field is not empty!')
    if (!confirmNewPassword) noteInput(confirmNewPasswordInput, 'Please ensure this field is not empty!')
    return false
  }

  if (newPassword !== confirmNewPassword) {
    noteInput(newPasswordInput, 'The new passwords entered do not match!')
    noteInput(confirmNewPasswordInput, 'The new passwords entered do not match!')
    return false
  }

  if (password === newPassword && password === confirmNewPassword) {
    noteInput(passwordInput, 'The new password is as same as your current password!')
    noteInput(newPasswordInput, 'The new password is as same as your current password!')
    noteInput(confirmNewPasswordInput, 'The new password is as same as your current password!')
    return false
  }

  const data = {email, password, newPassword}

  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')
  const submitBtn = form.querySelector('button[type="submit"]')
  if (submitBtn) submitBtn.setAttribute('data-loading', 'true')

  sendRequest(
    '/wiki-portal/change-password',
    'POST',
    JSON.stringify(data),
    (data) => {
      setTimeout(() => {
        body.setAttribute('data-loading', 'false')
        if (submitBtn) submitBtn.setAttribute('data-loading', 'false')

        console.log(data)
      }, 1000)
    })
}

const forgotPassword = () => {
  const form = document.querySelector('#forgot-password-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const emailInput = form.querySelector('input[name="email"]')

  const email = emailInput?.value
  if (!email) {
    noteInput(emailInput, 'Please ensure this field is not empty!')
    return false
  }

  const isValidEmail = validateEmail(email)
  if (!isValidEmail) {
    noteInput(emailInput, 'This email is invalid.')
    return false
  }

  const data = {email}

  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')
  const submitBtn = form.querySelector('button[type="submit"]')
  if (submitBtn) submitBtn.setAttribute('data-loading', 'true')

  sendRequest(
    '/wiki-portal/forgot-password',
    'POST',
    JSON.stringify(data),
    (data) => {
      setTimeout(() => {
        console.log(data)
        switch (data?.status) {
          case 200:
            let confirm = true
            do {
              confirm = window.confirm("A message has been sent to your email. Do you want to return to home page?")
            } while(!confirm)

            if (confirm) window.location.reload()
            break

          case 404:
            noteInput(emailInput, data?.message)
            break

          default:
            console.log('Oops! Something went wrong!')
            break
        }
      }, 1000)
    })

  return false
}

const login = () => {
  const form = document.querySelector('#login-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const emailInput = form.querySelector('input[name="email"]')
  const passwordInput = form.querySelector('input[name="password"]')
  const rememberCheckbox = form.querySelector('input[name="remember"]')

  const email = emailInput?.value
  const password = passwordInput?.value
  if ([email, password].includes('')) {
    if (!email) noteInput(emailInput, 'Please ensure this field is not empty!')
    if (!password) noteInput(passwordInput, 'Please ensure this field is not empty!')
    return false
  }

  const isValidEmail = validateEmail(email)
  if (!isValidEmail) {
    noteInput(emailInput, 'This email is invalid.')
    return false
  }

  const data = {email, password}

  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')
  const submitBtn = form.querySelector('button[type="submit"]')
  if (submitBtn) submitBtn.setAttribute('data-loading', 'true')

  sendRequest(
    '/wiki-portal/login',
    'POST',
    JSON.stringify(data),
    (data) => {
      setTimeout(() => {
        body.setAttribute('data-loading', 'false')
        if (submitBtn) submitBtn.setAttribute('data-loading', 'false')

        console.log(data)
        switch (data?.status) {
          case 200:
            const jsonData = JSON.parse(data?.data || '')
            const accessToken = jsonData?.accessToken || null
            if (accessToken) {
              const shouldRemember = rememberCheckbox?.checked
              setCookie('authToken', accessToken, shouldRemember ? 365 : 0)
              window.location.reload()
            } else console.log("Error: Access token not found.")
            break

          case 401:
            noteInput(passwordInput, data?.message)
            break

          case 404:
            noteInput(emailInput, data?.message)
            break

          default:
            console.log('Oops! Something went wrong!')
            break
        }
      }, 1000)
    })

  return false
}

const logout = () => {
  setCookie('authToken', '', -1)
  setTimeout(() => window.location.reload(), 500)
}

const register = () => {
  const form = document.querySelector('#register-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const fullnameInput = form.querySelector('input[name="fullname"]')
  const emailInput = form.querySelector('input[name="email"]')
  const passwordInput = form.querySelector('input[name="password"]')
  const confirmPasswordInput = form.querySelector('input[name="confirm-password"]')

  const fullname = fullnameInput?.value
  const email = emailInput?.value
  const password = passwordInput?.value
  const confirmPassword = confirmPasswordInput?.value

  if ([fullname, email, password, confirmPassword].includes('')) {
    if (!fullname) noteInput(fullnameInput, 'Please ensure this field is not empty!')
    if (!email) noteInput(emailInput, 'Please ensure this field is not empty!')
    if (!password) noteInput(passwordInput, 'Please ensure this field is not empty!')
    if (!confirmPassword) noteInput(confirmPasswordInput, 'Please ensure this field is not empty!')
    return false
  }

  if (password !== confirmPassword) {
    noteInput(passwordInput, 'The passwords entered do not match!')
    noteInput(confirmPasswordInput, 'The passwords entered do not match!')
    return false
  }

  const isValidEmail = validateEmail(email)
  if (!isValidEmail) {
    noteInput(emailInput, 'This email is invalid.')
    return false
  }

  const data = {fullname, email, password}

  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')
  const submitBtn = form.querySelector('button[type="submit"]')
  if (submitBtn) submitBtn.setAttribute('data-loading', 'true')

  sendRequest(
    '/wiki-portal/register',
    'POST',
    JSON.stringify(data),
    (data) => {
      setTimeout(() => {
        body.setAttribute('data-loading', 'false')
        if (submitBtn) submitBtn.setAttribute('data-loading', 'false')

        console.log(data)
        switch (data?.status) {
          case 201:
            const jsonData = JSON.parse(data?.data || '')
            const accessToken = jsonData?.accessToken || null
            if (accessToken) {
              setCookie('authToken', accessToken, 0)
              window.location.reload()
            } else console.log("Error: Access token not found.")
            break

          case 409:
            noteInput(emailInput, data?.message)
            break

          default:
            console.log('Oops! Something went wrong!')
            break
        }
      }, 1000)
    })

  return false
}

const validateEmail = (email) => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailPattern.test(email);
}

const noteInput = (inputElement, msg) => {
  console.log(msg)
  const noteElement = inputElement.closest('.input-group').querySelector('.input-group__note')
  if (noteElement) noteElement.innerHTML = msg
}

const resetNoteInputs = document.querySelectorAll('.input-group input')
resetNoteInputs.forEach(input => {
  input.addEventListener('keyup', () => {
    noteInput(input, '')
  })
})