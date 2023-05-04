
import {publicAuth,privateAuth} from './auth'
class EmployeeService{
    login(data){
        return publicAuth.post('employees/loginEmp',data)
    }
    getEmployees(){
        return privateAuth.get('employees/getEmp')
    }
    getEmployeesById(id){
        return privateAuth.get(`employees/edit/${id}`)
    }
    create(data){
        return publicAuth.post('employees/registerEmp',data) 
    }
    update(id, data){
        return privateAuth.put(`employees/${id}`,data)
    }
    remove(id){
        return privateAuth.delete(`employees/delete/${id}`)
    }

    searchEmployees(data){
        return privateAuth.get(`employees/search`,data)
    }
    
}


export default new EmployeeService()