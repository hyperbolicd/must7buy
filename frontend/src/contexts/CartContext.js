import { createContext, useReducer, useContext, useEffect } from "react";
import { updateCart } from "../services/customerService";
import { useUser } from "./UserContext";
import { defaultDebounce } from "../utils/util";

// CartItem 型別
const CartContext = createContext();

const initialState = {
  cartItems: [],
  totalPrice: 0,
};

// 初始化函數：從 localStorage 載入資料
const init = () => {
  const storedCart = localStorage.getItem("must7buy-frontend-cart");
  if (storedCart) {
      try {
      return JSON.parse(storedCart);
      } catch (error) {
      console.error("Error parsing stored cart:", error);
      return initialState;
      }
  }
  return initialState;
};

// 定義 Reducer
const cartReducer = (state, action) => {
  switch (action.type) {
    case "ADD_ITEM": {
      const existingItemIndex = state.cartItems.findIndex(
        (item) => item.id === action.payload.id && item.style === action.payload.style
      );

      let updatedCart;
      if (existingItemIndex !== -1) {
        updatedCart = [...state.cartItems];
        updatedCart[existingItemIndex].quantity += action.payload.quantity;
      } else {
        updatedCart = [...state.cartItems, action.payload];
      }

      return {
        cartItems: updatedCart,
        totalPrice: updatedCart.reduce((total, item) => total + item.price * item.quantity, 0),
      };
    }

    case "REMOVE_ITEM": {
      const updatedCart = state.cartItems.filter((item) => item.id !== action.payload);
      return {
        cartItems: updatedCart,
        totalPrice: updatedCart.reduce((total, item) => total + item.price * item.quantity, 0),
      };
    }

    case "UPDATE_QUANTITY": {
      const updatedCart = state.cartItems.map((item) =>
        item.id === action.payload.id ? { ...item, quantity: action.payload.quantity } : item
      );
      return {
        cartItems: updatedCart,
        totalPrice: updatedCart.reduce((total, item) => total + item.price * item.quantity, 0),
      };
    }

    case "CLEAR_CART":
      return initialState;

    default:
      return state;
  }
};

// CartProvider
export const CartProvider = ({ children }) => {
  const [state, dispatch] = useReducer(cartReducer, initialState, init);
  const { user } = useUser(); 

  useEffect(() => {
    localStorage.setItem("must7buy-frontend-cart", JSON.stringify(state));
  }, [state]);

  // 當使用者是會員時，購物車變動後透過 debounce 同步到資料庫
  useEffect(() => {
    if (user) {
      // 如果有使用者登入（會員），則同步購物車
      const syncCart = defaultDebounce(async () => {
        try {
          // 呼叫 API 將 state.cartItems 同步到資料庫
          await updateCart(user.token, state)
          // 例如：await syncCartToApi(user.token, state.cartItems);
          console.log("Syncing cart to DB:", state);
        } catch (error) {
          console.error("Sync cart error:", error);
        }
      }, 1500); // 等待 1500 毫秒內無新更新後呼叫 API

      syncCart();
    }
  }, [state, user]);

  const addItem = (item) => {
    dispatch({ type: "ADD_ITEM", payload: item });
  };

  const removeItem = (id) => {
    dispatch({ type: "REMOVE_ITEM", payload: id });
  };

  const updateQuantity = (id, quantity) => {
    dispatch({ type: "UPDATE_QUANTITY", payload: { id, quantity } });
  };

  const clearCart = () => {
    dispatch({ type: "CLEAR_CART" });
  };

  return (
    <CartContext.Provider value={{ ...state, addItem, removeItem, updateQuantity, clearCart }}>
      {children}
    </CartContext.Provider>
  );
};

// 使用 CartContext 的 Hook
export const useCart = () => {
  return useContext(CartContext);
};
