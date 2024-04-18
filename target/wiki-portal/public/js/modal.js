const showModal = (id) => {
  const modal = document.querySelector(`#${id}`)
  if (!modal) return

  modal.setAttribute('data-toggle', 'true')
}

const hideModal = (id) => {
  const modal = document.querySelector(`#${id}`)
  if (!modal) return
  modal.setAttribute('data-toggle', 'false')
}

const switchModal = (curr, next) => {
  hideModal(curr)
  setTimeout(() => showModal(next), 250)
}