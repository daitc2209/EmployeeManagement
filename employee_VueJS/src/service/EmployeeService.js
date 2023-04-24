
import {privateAuth} from './auth'
class EmployeeService{
    getEmployees(){
        return privateAuth.get('employees')
    }
    getEmployeesById(id){
        return privateAuth.get(`employees/edit/${id}`)
    }
    create(data){
        return privateAuth.post('employees',data) 
    }
    update(id, data){
        return privateAuth.put(`employees/${id}`,data)
    }
    remove(id){
        return privateAuth.delete(`/employees/delete/${id}`)
    }
    
}


export default new EmployeeService()