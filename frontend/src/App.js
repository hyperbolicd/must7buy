import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Homepage from './components/HomePage/Homepage';
import Footer from './components/Footer/Footer';

function App() {
  return (
    <div>
      <Router>
        <Header />
        <div className="container" style={{marginTop: "20px"}}>
          <Routes> {/* http://localhost:3000/ */}
            <Route path='/' exact element={<Homepage />}></Route>
          </Routes>
        </div>
        <Footer />
      </Router>
    </div>
  );
}

export default App;
