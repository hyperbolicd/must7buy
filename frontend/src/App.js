import './App.css';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Homepage from './pages/HomePage/Homepage';
import TestPage from './pages/TestPage';
import ErpPage from './pages/ErpPage';
import ErpProductPage from './pages/ErpProductPage/ErpProductPage';
import MainPage from './pages/MainPage';
import ErpHomepage from './pages/ErpHomepage/ErpHomepage';

function App() {
  return (
    <div>
      <Router>
          <Routes> 
            {/* http://localhost:3000/ */}
            <Route path='/' exact element={<MainPage />}>
              <Route index element={<Homepage />}></Route>
              <Route path='test' exact element={<TestPage />}></Route>

              <Route path="*" element={<Homepage />}></Route>
            </Route>
            {/* http://localhost:3000/erp */}
            <Route path='erp' exact element={<ErpPage />}>
              <Route index element={<ErpHomepage />}></Route>
              <Route path='products' exact element={<ErpProductPage />}></Route>
              <Route path="*" element={<ErpHomepage />}></Route>
            </Route>
          </Routes>
      </Router>
    </div>
  );
}

export default App;