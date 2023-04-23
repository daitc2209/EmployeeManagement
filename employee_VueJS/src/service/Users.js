import axios from 'axios'
import {privateAuth, publicAuth} from './auth'

const USER_API_BASE_URL = 'http://localhost:8080/'
// const EMPLOYEE_DELETE_API_BASE_URL = 'http://localhost:8080/employees/delete/{id}'
class Users{
    created1(data){
        // return axios.post(`${USER_API_BASE_URL}createUser`,data) 
        return axios.post(`createUser`,data) 
    }
    login(data)
    {
        return axios.post('login',data) 
    }
    
    
}
// window.localStorage.clear();

// const token = localStorage.getItem("jwtToken");
// console.log("JSON parse: " + token); 
// axios.interceptors.request.use(     // cho phép tái sử dụng logic xử lý trước khi gửi các request HTTP.
//     config => {
//             config.headers.Authorization = `Bearer ${localStorage.getItem("jwtToken")}`;
//         console.log("config.headers.Authorization: " + config.headers.Authorization)
//         return config;
//     },
//     error => {
//         Promise.reject(error)
//     }
// );

export default new Users()