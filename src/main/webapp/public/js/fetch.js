const sendRequest = (url, method, body, callback, opt) => {
  console.log('go here')
  fetch(url, {
    method: method,
    body: body,
  })
  .then(response => {
    console.log('go there', response)
    return response.json()
  })
  .then(data => {
    console.log('go over there', data)
    callback(data)
  } );
}