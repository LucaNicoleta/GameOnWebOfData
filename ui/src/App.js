import './App.css';
import GameScene from './gameScenes/game';
import Login from './login/login';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="play/*" element={<GameScene />} />
      </Routes>
  </Router>);
}

export default App;
