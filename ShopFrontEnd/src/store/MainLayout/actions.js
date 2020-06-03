import axios from "axios"

const login = ({ commit, state }, { id, password }) => {
    return new Promise((resolve, reject) => {
        axios.get('/user', {
            params: {
                id: id,
                password: password
            }
        }).then(res => {
            resolve(res);
        }).catch(err => {
            reject(err)
        })
    })
}

const register = ({ commit, state }, { id, name, password }) => {
    return new Promise((resolve, reject) => {
        axios.post('/user', {
            data: {
                id: id,
                name: name,
                password: password
            }
        }).then(res => {
            resolve(res);
        }).catch(err => {
            reject(err)
        })
    })
}
export {
    login,
    register
}