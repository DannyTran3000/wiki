const sendRequest = (url, method, body, callback, opt) => {
  fetch(url, {
    method: method,
    body: body,
  })
  .then(response => {
    return response.json()
  })
  .then(data => {
    callback(data)
  } );
}