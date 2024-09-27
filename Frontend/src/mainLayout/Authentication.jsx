import React from 'react'
import LoginForm from '../layout/form/LoginForm'
import { Route, Routes } from 'react-router-dom'
import SignupForm from '../layout/form/SignupForm'

export const Authentication = () => {
  return (
    <div>
        <Routes>
            <Route path='/*' element={<LoginForm/>}/>
            <Route path='/login' element={<LoginForm/>}/>
            <Route path='/signup' element={<SignupForm/>}/>
        </Routes>
        
    </div>
  )
}
