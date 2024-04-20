const showModal = (id) => {
  const modal = document.querySelector(`#${id}`)
  if (!modal) return

  modal.setAttribute('data-toggle', 'true')
}

const hideModal = (id) => {
  const modal = document.querySelector(`#${id}`)
  if (!modal) return
  modal.setAttribute('data-toggle', 'false')

  const modalInputs = modal.querySelectorAll('input')
  modalInputs.forEach(item => {
    const type = item.getAttribute('type')
    if (['checkbox', 'radio'].includes(type))
      item.checked = false
    else
      item.value = ''
  })

}

const switchModal = (curr, next) => {
  hideModal(curr)
  setTimeout(() => showModal(next), 250)
}