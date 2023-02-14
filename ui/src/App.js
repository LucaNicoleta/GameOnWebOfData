import './App.css';
import GameScene from './gameScenes/game';
import Login from './login/login';
import Inventory from './inventory';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
function App() {

  return (
    <Router>
      <Routes>
        <Route path="/inventory" element={<Inventory />} />
        <Route path="/" element={<Login />} />
        <Route path="play/*" element={<GameScene />} />
      </Routes>
  </Router>);
}

export default App;
