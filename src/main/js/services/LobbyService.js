const createUser = async(name) => {
  return (await fetch('/api/users', {
    method: 'POST',
    body: JSON.stringify({
      name,
    }),
    headers:{
      'Content-Type': 'application/json'
    }
  })).json()
};

export default {
  createUser,
}