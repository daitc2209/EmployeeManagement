
// import {publicAuth,privateAuth} from './auth'
import axios from 'axios'
class EmployeeService{
    login(data){
        return axios.post(`employees/loginEmp`,data) 
        // return publicAuth.post('employees/loginEmp',data)
    }
    getEmployees(){
        return axios.get('employees/getEmp') 
        // return privateAuth.get('employees/getEmp')
    }
    getEmployeesById(id){
        return axios.get(`employees/edit/${id}`) 
        // return privateAuth.get(`employees/edit/${id}`)
    }
    create(data){
        return axios.post('employees/registerEmp',data) 
        // return publicAuth.post('employees/registerEmp',data) 
    }
    update(id, data){
        return axios.put(`employees/${id}`,data) 
        // return privateAuth.put(`employees/${id}`,data)
    }
    remove(id){
        return axios.delete(`employees/delete/${id}`) 
        // return privateAuth.delete(`employees/delete/${id}`)
    }

    searchEmployees(data){
        return axios.get(`employees/search`,data)
    }
    
    checkEmail(input){
        const email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        return email.test(input);
    }
}


export default new EmployeeService()