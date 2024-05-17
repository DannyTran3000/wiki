const selects = document.querySelectorAll('.select')

selects.forEach(select => {
  const toggle = select.querySelector('.select__toggle')
  if (toggle) {
    toggle.addEventListener('click', () => {
      selects.forEach(item => item.setAttribute('data-toggle', "false"))

      select.setAttribute('data-toggle', "true")
    })
  }

  const options = select.querySelectorAll('.select__option')
  const selectTarget = select.getAttribute('data-target')
  options.forEach(option =>
    option.addEventListener('click', () => {
      const optionValue = option.getAttribute('data-value')
      const optionName = option.getAttribute('data-display')
      const hiddenInput = select.querySelector(`input[name="${selectTarget}"]`)
      if (hiddenInput) hiddenInput.value = optionValue
      if (toggle) toggle.innerHTML = optionName
    })
  )
})

window.addEventListener('click', (e) => {
    e.stopPropagation()

    const isToggle = e.target.classList.contains('select__toggle')
    const isContent = e.target.classList.contains('.select__options')
    const isInsideContent = !!e.target.closest('.select__options') && !e.target.classList.contains('select__option')

    if (!isToggle && !isContent && !isInsideContent) {
      setTimeout(() => {
        selects.forEach(item => item.setAttribute('data-toggle', "false"))
      }, 50);
    }
})