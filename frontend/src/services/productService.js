import { env } from '../configs/config';
import fetchData from './apiService';

const PRODUCT_API_BASE_URL = `${env.API_URL}/api/v1/products`

export async function getProducts(token) {
    return fetchData(PRODUCT_API_BASE_URL, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function createProduct(token, product) {
    return fetchData(PRODUCT_API_BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(product)
    })
}

export async function uploadProductImage(token, file) {
    const formData = new FormData()
    formData.append("file", file)

    return fetchData(`${PRODUCT_API_BASE_URL}/upload`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
        body: formData
    })
}

export async function getProductById(token, productId) {
    return fetchData(`${PRODUCT_API_BASE_URL}/${productId}`, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function updateProduct(token, productId, product) {
    return fetchData(`${PRODUCT_API_BASE_URL}/${productId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(product)
    })
}

export async function deleteProduct(token, productId) {
    return fetchData(`${PRODUCT_API_BASE_URL}/${productId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
}

export async function searchProducts(query) {
    return fetchData(`${PRODUCT_API_BASE_URL}/search?${query}`)
}