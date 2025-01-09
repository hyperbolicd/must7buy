import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import HomepageComponent from './components/HomepageComponent';

function App() {
  return (
    <div>
      <Router>
        <HeaderComponent />
        <div className="container" style={{marginTop: "20px"}}>
          <Routes> {/* http://localhost:3000/ */}
            <Route path='/' exact element={<HomepageComponent />}></Route>
          </Routes>
        </div>
        <FooterComponent />
      </Router>
    </div>
  );
}

export default App;
