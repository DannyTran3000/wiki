const selects = document.querySelectorAll('.select')

selects.forEach(select => {
  const toggle = select.querySelector('.select__toggle')
  if (toggle) {
    toggle.addEventListener('click', () => {
      select.setAttribute('data-toggle', "true")
    })
  }
})

window.addEventListener('click', (e) => {
    e.stopPropagation()

    const isShow = e.target.closest('.select')?.getAttribute('data-toggle') === 'true'

    const isToggle = e.target.classList.contains('select__toggle')
    const isContent = e.target.classList.contains('.select__options')
    const isInsideContent = !!e.target.closest('.select__options') && !e.target.classList.contains('select__option')

    if (isShow && (!isToggle && !isContent && !isInsideContent)) {
      setTimeout(() => {
        selects.forEach(item => item.setAttribute('data-toggle', "false"))
      }, 50);
    }
})