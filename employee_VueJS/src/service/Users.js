
import { publicAuth,privateAuth} from './auth'

class Users{
    login(data)
    {
        // return axios.post('login',data) 
        return publicAuth.post('user/login',data) 
    }
    created1(data){
        // return axios.post(`createUser`,data) 
        return privateAuth.post(`user/createUser`,data) 
    }
    getUsers(data){
        // return axios.post(`createUser`,data) 
        return privateAuth.get(`user/userActive`,data) 
    }

    getUserById(id){
        return privateAuth.get(`user/edit/${id}`)
    }
    update(id, data){
        return privateAuth.put(`user/${id}`,data)
    }
    remove(id){
        return privateAuth.delete(`user/delete/${id}`)
    }

    searchUser(data){
        return privateAuth.get(`user/search`,data)
    }
}

export default new Users()