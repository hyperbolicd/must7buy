const fetchData = async (url, options = {}) => {
    
    const response = await fetch(url, {
        ...options,
    });

    if (!response.ok) {
        let errorMessage = `HTTP error! status: ${response.status}`;
        try {
            const errorData = await response.json();
            errorMessage = errorData.message || errorMessage;
        } catch (e) {
            // 忽略 JSON parse 錯誤
        }
        throw new Error(errorMessage + " from apiService");
    }

    return response.json();
}

export default fetchData;
