import {env} from '../configs/config';

const EMPLOYEE_API_BASE_URL = `${env.API_URL}/api/v1/employees`

class EmployeeService {

  getEmployees() {
    return fetch(EMPLOYEE_API_BASE_URL)
        .then((response) => {
        if(response.ok)
            return response.json()
        else
            throw new Error(`HTTP error! status: ${response.status}`)
        })
        .catch((error => {
            return null
        })
    )
  }

  createEmployee(employee) {
    return fetch(EMPLOYEE_API_BASE_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(employee),
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json(); // 返回 JSON 格式的響應
    })
    .catch((error) => {
        console.error("There was an error creating the employee:", error);
        throw error; // 向上拋出錯誤
    });
  }

  getEmplooyeeById(employeeId) {
    return fetch(`${EMPLOYEE_API_BASE_URL}/${employeeId}`)
        .then((response) => {
        if(response.ok)
            return response.json()
        else
            throw new Error(`HTTP error! status: ${response.status}`)
        })
        .catch((error => {
            return null
        })
    )
  }

  updateEmployee(employeeId, employee) {
    // return axios.put(`${EMPLOYEE_API_BASE_URL}/${employeeId}`, employee)
  }

  deleteEmployee(employeeId) {
    // return axios.delete(`${EMPLOYEE_API_BASE_URL}/${employeeId}`)
  }
}

export default new EmployeeService()
