const passwordToggles = document.querySelectorAll('.input-group__trigger-password')
passwordToggles.forEach(item => {
  item.addEventListener('click', () => {
    const passwordInputContainer = item.closest('.input-group')
    if (passwordInputContainer) {
      const passwordInput = passwordInputContainer.querySelector('input')
      if (passwordInput) {
        const currentStatus = item.getAttribute("data-toggle")
        if (currentStatus === 'true') {
          passwordInput.setAttribute('type', 'password')
          item.innerHTML = 'Show'
          item.setAttribute('data-toggle', 'false')
        } else if (currentStatus === 'false') {
          passwordInput.setAttribute('type', 'text')
          item.innerHTML = 'Hide'
          item.setAttribute('data-toggle', 'true')
        }
      }
    }
  })
})