import axios from 'axios'

export const bookApi = {
  authenticate,
  signup,
  startGame,
  loadGame,
  currentScene,
  nextScene,
  readInventory
}

function authenticate(username, password) {
  return instance.post('/login', { username, password },{
    headers: { 'Content-type': 'multipart/form' }
  })
}

function signup(user) {
  return instance.post('/auth/signup', user, {
    headers: { 'Content-type': 'application/json' }
  })
}

function startGame(user) {
  const url = 'api/v1/game/start'
  return instance.get(url, {
    headers: { 'Authorization': basicAuth(user) }
  })
}
function loadGame(user) {
  const url = 'api/v1/game/currentScene'
  return instance.get(url, {
    headers: { 'Authorization': basicAuth(user) }
  })
}
function currentScene(user) {
  const url = 'api/v1/game/currentScene'
  return instance.get(url, {
    headers: { 'Authorization': basicAuth(user) }
  })}
  function readInventory(user) {
    const url = 'api/v1/game/inventory'
    return instance.get(url, {
      headers: { 'Authorization': basicAuth(user) }
    })}

  function nextScene(user,currentStats) {
    console.log(user)
    console.log(currentStats)
    const url = 'api/v1/game/nextScene'
    return instance.post(url,currentStats, {
      headers: { 'Authorization': basicAuth(user),
      'Content-type': 'application/json' }
    })
}


// -- Axios

const instance = axios.create({
  baseURL: "http://localhost:8080"
})

// -- Helper functions

function basicAuth(user) {
  return `Basic ${user.authdata}`
}