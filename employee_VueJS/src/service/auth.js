import axios from "axios"
axios.defaults.headers.get['Accepts'] = 'application/json';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
// axios.defaults.headers.common['Access-Control-Allow-Method'] = ['GET, POST, PUT, DELETE'];
axios.defaults.headers.common['Access-Control-Allow-Headers'] = 'Origin, X-Requested-With, Content-Type, Accept';
axios.defaults.baseURL = 'http://localhost:8080/'
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem("jwtToken");

const BASE_URL = 'http://localhost:8080/'
export const publicAuth = axios.create({
    // baseURL: BASE_URL,
})

export const privateAuth = axios.create({
    // baseURL: BASE_URL,
    // headers: {
    //     'Content-Type': 'application/json',
    //     'Access-Control-Allow-Origin': '*',
    //     Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
    // },
});