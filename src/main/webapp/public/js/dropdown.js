const dropdowns = document.querySelectorAll('.dropdown')

dropdowns.forEach(dropdown => {
  const toggle = dropdown.querySelector('.dropdown__toggle')
  if (toggle) {
    toggle.addEventListener('click', () => {
      dropdowns.forEach(item => item.setAttribute('data-toggle', "false"))

      dropdown.setAttribute('data-toggle', "true")
    })
  }
})

window.addEventListener('click', (e) => {
    e.stopPropagation()
    const isToggle = e.target.classList.contains('dropdown__toggle')
    const isContent = e.target.classList.contains('.dropdown__content')
    const isInsideContent = !!e.target.closest('.dropdown__content') && !e.target.classList.contains('dropdown__option-content')

    if (!isToggle && !isContent && !isInsideContent) {
      setTimeout(() => {
        dropdowns.forEach(item => item.setAttribute('data-toggle', "false"))
      }, 50);
    }
})