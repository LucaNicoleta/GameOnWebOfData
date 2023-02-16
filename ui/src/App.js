import './App.css';
import GameScene from './gameScenes/game';

import Inventory from './inventory';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
import Homepage from './homepage/homepage';
import { AuthProvider } from './context/AuthContext';
import { SceneProvider } from './context/SceneContext';

import PrivateRoute from './misc/PrivateRoute'

function App() {

  return (
    <AuthProvider>
      <SceneProvider>
    <Router>
      <Routes>
        <Route path="/inventory" element={<PrivateRoute><Inventory /></PrivateRoute>} />
        <Route path="/" element={<Homepage />} />
        <Route path="play/*" element={<PrivateRoute><GameScene /></PrivateRoute>} />
      </Routes>
  </Router>
  </SceneProvider>
  </AuthProvider>);
}

export default App;
