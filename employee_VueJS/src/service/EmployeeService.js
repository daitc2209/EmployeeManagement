
import {publicAuth,privateAuth} from './auth'
class EmployeeService{
    login(data){
        return publicAuth.post('employees/loginEmp',data)
    }
    getEmployees(){
        return privateAuth.get('employees')
    }
    getEmployeesById(id){
        return privateAuth.get(`employees/edit/${id}`)
    }
    create(data){
        return publicAuth.post('employees/createEmp',data) 
    }
    update(id, data){
        return privateAuth.put(`employees/${id}`,data)
    }
    remove(id){
        return privateAuth.delete(`employees/delete/${id}`)
    }
    
}


export default new EmployeeService()