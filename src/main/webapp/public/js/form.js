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
    '/wiki-portal/auth?action=change-password',
    'POST',
    JSON.stringify(data),
    (data) => {
      setTimeout(() => {
        body.setAttribute('data-loading', 'false')
        if (submitBtn) submitBtn.setAttribute('data-loading', 'false')

        console.log(data)
        switch (data?.status) {
          case 200:
              console.log(data?.message)
              window.location.reload()
            break

          case 401:
            noteInput(passwordInput, data?.message)
            break

          default:
            console.log('Oops! Something went wrong!')
            break
        }
      }, 1000)
    })

  return false
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
    '/wiki-portal/auth?action=forgot-password',
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
    '/wiki-portal/auth?action=login',
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
  sendRequest('/wiki-portal/auth?action=logout', 'POST')
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
    '/wiki-portal/auth?action=register',
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

// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
const filterArticle = (p, pageType = 'none') => {
  const filterForm = document.querySelector('#article-filter-form')
  if (!filterForm) return false

  const keywordInput = filterForm.querySelector('input[name="keyword"]')
  const categoryInput = filterForm.querySelector('input[name="category"]')
  const sortInput = filterForm.querySelector('input[name="sort"]')
  const showHiddenInput = filterForm.querySelector('input[name="show-hidden"]')

  const keyword = keywordInput?.value ? `keyword=${keywordInput.value}` : ''
  const category = categoryInput?.value ? `category=${categoryInput.value}` : ''
  const sort = sortInput?.value ? `sort=${sortInput.value}` : ''
  const showHidden = showHiddenInput?.checked ? 'show-hidden=true' : ''
  const page = `page=${p || 1}`

  const params = [keyword, category, sort, showHidden, page].filter(item => !!item)

  const type = filterForm.getAttribute('data-type')

  const isAdmin = [type, pageType].includes('admin')
  const path = isAdmin
    ? `/wiki-portal/admin/articles${params.length > 0 ? `?${params.join('&')}` : ''}`
    : `/wiki-portal/articles${params.length > 0 ? `?${params.join('&')}` : ''}`

  window.location.href = path

  return false
}

const paginationArticle = (e, type = 'none') => {
  const page = e.target.getAttribute('data-value')
  if (parseInt(page))
  filterArticle(parseInt(page), type)
}

const resetFilterArticle = () => {
  const filterForm = document.querySelector('#article-filter-form')
  if (!filterForm) return false

  const keywordInput = filterForm.querySelector('input[name="keyword"]')
  const categoryInput = filterForm.querySelector('input[name="category"]')
  const sortInput = filterForm.querySelector('input[name="sort"]')
  const showHiddenInput = filterForm.querySelector('input[name="show-hidden"]')

  if (keywordInput) keywordInput.value = ""
  if (categoryInput) categoryInput.value = ""
  if (sortInput) sortInput.value = "date"
  if (showHiddenInput) showHiddenInput.checked = false

  const categorySelect = filterForm.querySelector('.select[data-target="category"]')
  if (categorySelect) {
    const categoryToggle = categorySelect.querySelector('.select__toggle')
    if (categoryToggle) categoryToggle.innerHTML = "Choose a category"
  }
  const sortSelect = filterForm.querySelector('.select[data-target="sort"]')
  if (sortSelect) {
    const sortToggle = sortSelect.querySelector('.select__toggle')
    if (sortToggle) sortToggle.innerHTML = "Sort by date"
  }

  return false
}

// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
const createCategory = () => {
  const form = document.querySelector('#category-detail-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const nameInput = form.querySelector('input[name="name"]')
  const iconInput = form.querySelector('input[name="icon"]')

  const name = nameInput?.value
  const icon = iconInput?.value
  const data = {name, icon}
  
  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')

  sendRequest(
    `/wiki-portal/admin/categories`,
    'POST',
    JSON.stringify(data)
  )

  setTimeout(() => window.location.href = '/wiki-portal/admin/categories', 1500)
}

const updateCategory = () => {
  const form = document.querySelector('#category-detail-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const nameInput = form.querySelector('input[name="name"]')
  const iconInput = form.querySelector('input[name="icon"]')
  const slugInput = form.querySelector('input[name="slug"]')

  const name = nameInput?.value
  const icon = iconInput?.value
  const slug = slugInput?.value
  const data = {name, icon}
  
  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')

  sendRequest(
    `/wiki-portal/admin/categories/${slug}`,
    'PUT',
    JSON.stringify(data)
  )

  setTimeout(() => window.location.reload(), 1500)
}

const deleteCategory = (e) => {
  const slug = e.target?.getAttribute('data-value')

  if (!slug) {
    console.log('Slug not found!!!')
    return
  }
  
  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')

  sendRequest(`/wiki-portal/admin/categories/${slug}`, 'DELETE')

  setTimeout(() => window.location.reload(), 1500)
}

// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
// ================================================================================
const createArticle = () => {
  const form = document.querySelector('#article-detail-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const titleInput = form.querySelector('input[name="title"]')
  const categoryInput = form.querySelector('input[name="category"]')
  const thumbnailInput = form.querySelector('input[name="thumbnail"]')
  const descriptionInput = form.querySelector('textarea[name="description"]')
  const contentInput = form.querySelector('textarea[name="content"]')

  const title = titleInput?.value
  const category = parseInt(categoryInput?.value || 0)
  const thumbnail = thumbnailInput?.value
  const description = descriptionInput?.value
  const content = contentInput?.value

  const data = {title, category, thumbnail, description, content}
  console.log(data)
  
  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')

  sendRequest(
    `/wiki-portal/admin/articles`,
    'POST',
    JSON.stringify(data)
  )

  setTimeout(() => window.location.href = '/wiki-portal/admin/articles', 1500)
}

const updateArticle = () => {
  const form = document.querySelector('#article-detail-form')

  if (!form) {
    console.log('Warning: Form not found.')
    return false
  }

  const titleInput = form.querySelector('input[name="title"]')
  const categoryInput = form.querySelector('input[name="category"]')
  const thumbnailInput = form.querySelector('input[name="thumbnail"]')
  const descriptionInput = form.querySelector('textarea[name="description"]')
  const contentInput = form.querySelector('textarea[name="content"]')
  const showHiddenInput = form.querySelector('input[name="show-hidden"]')
  const slugInput = form.querySelector('input[name="slug"]')

  const title = titleInput?.value
  const category = parseInt(categoryInput?.value || 0)
  const thumbnail = thumbnailInput?.value
  const description = descriptionInput?.value
  const content = contentInput?.value
  const showHidden = showHiddenInput?.checked ? 0 : 1
  const slug = slugInput?.value

  const data = {title, category, thumbnail, description, content, showHidden}
  
  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')

  sendRequest(
    `/wiki-portal/admin/articles/${slug}`,
    'PUT',
    JSON.stringify(data)
  )

  setTimeout(() => window.location.reload(), 1500)
}

const deleteArticle = (e) => {
  const slug = e.target?.getAttribute('data-value')

  if (!slug) {
    console.log('Slug not found!!!')
    return
  }
  
  const body = document.querySelector('body')
  body.setAttribute('data-loading', 'true')

  sendRequest(`/wiki-portal/admin/articles/${slug}`, 'DELETE')

  setTimeout(() => window.location.reload(), 1500)
}