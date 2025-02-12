import { createContext, useContext, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { getCookie } from "../utils/util";


const UserContext = createContext()

export const UserProvider = ({ children }) => {
    // 嘗試從 localStorage 讀取 User 資料
    const location = useLocation()
    const isBackend = location.pathname.startsWith('/erp')
    const storageKey = isBackend ? 'must7buy-backend-user' : 'must7buy-frontend-user'

    const [user, setUser] = useState(() => {
        return JSON.parse(localStorage.getItem(storageKey)) || null
    })

    function login(user) {
        setUser(user)
    }

    function logout() {
        setUser(null)
    }

     // 當 user 變更時，儲存到 localStorage
    useEffect(() => {
        if(user) {
            localStorage.setItem(storageKey, JSON.stringify(user))
        } else {
            localStorage.removeItem(storageKey)
        }
    }, [user, storageKey])

    // 提供 setUser 方法讓其他 Component 也可以更新 User 狀態
    return (
        <UserContext.Provider value={{ user, isBackend, login, logout }}>
            {children}
        </UserContext.Provider>
    )
}

// 建立一個自訂 Hook，讓其他 Component 方便存取
export const useUser = () => useContext(UserContext)