const getCookie = (name) => {
  const cookies = document.cookie.split(';')
  for (let cookie of cookies) {
    const [cookieName, cookieValue] = cookie.trim().split('=')
    if (cookieName === name) {
      return decodeURIComponent(cookieValue)
    }
  }
  return null;
}

const setCookie = (name, value, daysToExpire) => {
  let cookieValue
  if (daysToExpire === 0)
    cookieValue = encodeURIComponent(value) + '; expires=0; path=/'
  else if (daysToExpire < 0)
    cookieValue = encodeURIComponent(value) + '; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/'
  else {
    const expirationDate = new Date()
    expirationDate.setDate(expirationDate.getDate() + daysToExpire)
    cookieValue = encodeURIComponent(value) + '; expires=' + expirationDate.toUTCString() + '; path=/'
  }
  document.cookie = name + '=' + cookieValue
}