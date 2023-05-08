
// import { publicAuth,privateAuth} from './auth'
import axios from "axios"

class Users{
    login(data)
    {
        return axios.post('user/login',data) 
        // return publicAuth.post('user/login',data) 
    }
    created1(data){
        return axios.post(`user/createUser`,data) 
        // return privateAuth.post(`user/createUser`,data) 
    }
    getUsers(data){
        return axios.get(`user/getuser`,data) 
        // return privateAuth.get(`user/getuser`,data) 
    }

    getUserById(id){
        return axios.get(`user/edit/${id}`) 
        // return privateAuth.get(`user/edit/${id}`)
    }
    update(id, data){
        return axios.put(`user/${id}`,data)
        // return privateAuth.put(`user/${id}`,data)
    }
    remove(id){
        return axios.delete(`user/delete/${id}`) 
        // return privateAuth.delete(`user/delete/${id}`)
    }

    searchUser(data){
        return axios.get(`user/search`,data)
        // return privateAuth.get(`user/search`,data)
    }

    checkEmail(input){
        const email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        return email.test(input);
    }
}

export default new Users()