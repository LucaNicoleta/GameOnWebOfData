import React from 'react'
import { Route, Redirect, Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function PrivateRoute({ children }) {
  const { userIsAuthenticated } = useAuth()

  return userIsAuthenticated()?children : <Navigate to="/" />
  
}

export default PrivateRoute