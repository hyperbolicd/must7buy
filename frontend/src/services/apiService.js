const fetchData = async (url, options = {}) => {

    const response = await fetch(url, {
        ...options,
    });

    if (!response.ok) {
        let errorMessage = `HTTP error! status: ${response.status}`;

        try {
            const errorData = await response.json();
            errorMessage = errorData;
        } catch (e) {
            // 忽略 JSON parse 錯誤
        }
        
        console.error(errorMessage);
        return { error: errorMessage };
    }

    return response.json();
}

export default fetchData;
