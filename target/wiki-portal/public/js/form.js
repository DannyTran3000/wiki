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