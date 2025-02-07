import { Navigate, useLocation } from 'react-router-dom';
import { useUser } from '../contexts/UserContext';

const PrivateRoute = ({ children }) => {
  const { user } = useUser()
  const location = useLocation()
  
  console.log('location:'+ location.pathname)
  if (location.pathname === '/erp/login') {
    return children;
  }

  return user ? children : <Navigate to="/erp/login" replace />
}

export default PrivateRoute;