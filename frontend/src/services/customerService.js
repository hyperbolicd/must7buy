import { env } from '../configs/config';
import fetchData from './apiService';

const CUSTOMER_API_BASE_URL = `${env.API_URL}/api/v1/customers`

export async function getCustomers(token) {
    return fetchData(CUSTOMER_API_BASE_URL, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function createCustomer(customer) {
    return fetchData(`${CUSTOMER_API_BASE_URL}/registry`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(customer)
    })
}

export async function getCustomerById(token, customerId) {
    return fetchData(`${CUSTOMER_API_BASE_URL}/${customerId}`, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function getCustomerByUsername(token, username) {
    return fetchData(`${CUSTOMER_API_BASE_URL}/search?username=${username}`, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function updateCustomer(token, customerId, customer) {
    return fetchData(`${CUSTOMER_API_BASE_URL}/${customerId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(customer)
    })
}

export async function loginCustomer(customer) {
    return fetchData(`${CUSTOMER_API_BASE_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(customer)
    })
}