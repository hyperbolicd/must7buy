import { env } from '../configs/config';

const EMPLOYEE_API_BASE_URL = `${env.API_URL}/api/v1/employees`

export async function login(employee) {
    try {
        const response = await fetch(`${EMPLOYEE_API_BASE_URL}/login`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(employee)
                        })
        if (!response.ok) {
            return null
        }
        return response.json()
    } catch (error) {
        console.error('Error in login:', error);
        throw error
    }
}

export async function getEmployees() {
    try {
        const response = await fetch(EMPLOYEE_API_BASE_URL)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json()
    } catch (error) {
        console.error('Error in getEmployees:', error);
        throw error
    }
}

export async function createEmployee(employee) {
    try {
        employee.id = null
        const response = await fetch(EMPLOYEE_API_BASE_URL, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(employee)
                        })
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}, body: ${response.json()}`);
        }
        return response.json()
    } catch (error) {
        console.error('Error in createEmployee:', error);
        throw error
    }
}

export async function getEmplooyeeById(employeeId) {
    try {
        const response = await fetch(`${EMPLOYEE_API_BASE_URL}/${employeeId}`)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json()
    } catch (error) {
        console.error('Error in getEmplooyeeById:', error);
        throw error
    }
}

export async function checkEmail(email) {
    try {
        const response = await fetch(`${EMPLOYEE_API_BASE_URL}/check-email?email=${email}`)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json()
    } catch (error) {
        console.error('Error in getEmplooyeeById:', error);
        throw error
    }
}

export async function updateEmployee(employeeId, employee) {
    try {
        const response = await fetch(`${EMPLOYEE_API_BASE_URL}/${employeeId}`, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(employee)
                        })
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}, body: ${response.json()}`);
        }
        return response.json()
    } catch (error) {
        console.error('Error in updateEmployee:', error);
        throw error
    }
}

export async function deleteEmployee(employeeId) {
    try {
        const response = await fetch(`${EMPLOYEE_API_BASE_URL}/${employeeId}`, {
                            method: 'DELETE'
                        })
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return true
    } catch (error) {
        console.error('Error in getEmplooyeeById:', error);
        throw error
    }
}