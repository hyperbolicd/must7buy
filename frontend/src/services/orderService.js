import { env } from '../configs/config';
import fetchData from './apiService';

const ORDER_API_BASE_URL = `${env.API_URL}/api/v1/orders`

export async function getMyOrders(token) {
    return fetchData(`${ORDER_API_BASE_URL}/my`, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function getOrderById(token, id) {
    return fetchData(`${ORDER_API_BASE_URL}/${id}`, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function getProductByOrderId(token, orderId) {
    return fetchData(`${ORDER_API_BASE_URL}/${orderId}/products`, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function createOrders(token, cartItems, type) {
    return fetchData(`${ORDER_API_BASE_URL}?type=${type}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(cartItems)
    })
}

export async function cancelOrders(token, orderId) {
    return fetchData(`${ORDER_API_BASE_URL}/${orderId}/cancel`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    })
}