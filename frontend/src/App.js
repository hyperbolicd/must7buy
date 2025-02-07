import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Homepage from './pages/HomePage/Homepage';
import TestPage from './pages/TestPage';
import ErpPage from './pages/ErpPage';
import ErpProductPage from './pages/ErpProductPage/ErpProductPage';
import MainPage from './pages/MainPage';
import ErpHomepage from './pages/ErpHomepage/ErpHomepage';
import ErpEmployeePage from './pages/ErpEmployeePage/ErpEmployeePage';
import SaveEmployeePage from './pages/SaveEmployeePage/SaveEmployeePage';
import ErpLoginPage from './pages/ErpLoginPage/ErpLoginPage';
import { UserProvider } from './contexts/UserContext';

function App() {
  return (
    <div>
      <Router>
          <Routes> 
            {/* http://localhost:3000/ */}
            <Route path='/' exact element={<UserProvider><MainPage /></UserProvider>}>
              <Route index element={<Homepage />}></Route>
              <Route path='test' exact element={<TestPage />}></Route>

              <Route path="*" element={<Homepage />}></Route>
            </Route>
            {/* http://localhost:3000/erp */}
            <Route path='erp' exact element={<UserProvider><ErpPage /></UserProvider>}>
              <Route index element={<ErpHomepage />}></Route>
              <Route path='login' element={<ErpLoginPage />}></Route>
              <Route path='products' exact element={<ErpProductPage />}></Route>
              <Route path='employees' exact element={<ErpEmployeePage />}></Route>
              <Route path='employee/:id' element={<SaveEmployeePage />}></Route>
              <Route path="*" element={<ErpHomepage />}></Route>
            </Route>
          </Routes>
      </Router>
    </div>
  );
}

export default App;