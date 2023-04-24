
import { publicAuth} from './auth'

class Users{
    created1(data){
        // return axios.post(`createUser`,data) 
        return publicAuth.post(`createUser`,data) 
    }
    login(data)
    {
        // return axios.post('login',data) 
        return publicAuth.post('login',data) 
    }
}

export default new Users()