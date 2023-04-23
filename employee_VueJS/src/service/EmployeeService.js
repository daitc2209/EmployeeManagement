import axios from 'axios'
const EMPLOYEE_API_BASE_URL = 'http://localhost:8080/employees'
// const EMPLOYEE_DELETE_API_BASE_URL = 'http://localhost:8080/employees/delete/{id}'
class EmployeeService{
    getEmployees(){
        // return axios.get(EMPLOYEE_API_BASE_URL);
        return axios.get('employees')
    }
    getEmployeesById(id){
        return axios.get(`employees/edit/${id}`)
        // return privateAuth.get(`employees/edit/${id}`)
    }
    create(data){
        return axios.post('employees',data) 
        // return privateAuth.post('employees',data) 
    }
    update(id, data){
        return axios.put(`employees/${id}`,data)
        // return privateAuth.put(`employees/${id}`,data)
    }
    remove(id){
        return axios.delete(`employees/delete/${id}`)
        // return privateAuth.delete(`/employees/delete/${id}`)
    }
    
}


export default new EmployeeService()