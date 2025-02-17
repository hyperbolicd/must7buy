import { env } from '../configs/config';
import fetchData from './apiService';

const PAYMENT_API_BASE_URL = `${env.API_URL}/api/v1/payments`

export async function createPayments(token, order, items, paymentMethod) {
    const body = {
        cartItems: items,
        totalPrice: order.totalPrice,
    }

    return fetchData(`${PAYMENT_API_BASE_URL}/${order.id}?paymentMethod=${paymentMethod}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(body)
    })
}