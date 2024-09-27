import { useEffect, useState } from "react";
import {  Route, Routes, useNavigate } from "react-router-dom";

import useAppInitialLoad from "./hooks/useAppInitialLoad";

import { Authentication } from "./mainLayout/Authentication";
import { HomeSection } from "./mainLayout/HomeSection";
import { useDispatch, useSelector } from "react-redux";
import { getUserProfile } from "../Storage/Auth/Actions";

const App = () => {
  useAppInitialLoad();
  const jwt = localStorage.getItem("jwt");
  const { auth } = useSelector((store) => store);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (jwt) {
      dispatch(getUserProfile(jwt));
      navigate("/");
    }
  }, [auth.jwt]);


  return (
    <>
      <Routes>
        <Route
          path="/*"
          element={auth.user ? <HomeSection /> : <Authentication />}
        />
         {/* <Route
          path="/*"
          element={ <HomeSection /> }
        /> */}


        {/* <Route path='/*'
          // element={
          //   <>
          //     <Navbar navbarItem={navbarItem} handleClick={handleClick} showMenu={showMenu} />
          //     <Outlet />
          //     <FooterSection />
          //   </>
          // }
          >
          <Route path='/' element={<Authentication />} />
          <Route path='/logout' element={<Logout />} />
          <Route path='*' element={<NotFound />} />
        </Route>
        <Route path='/login' element={<LoginForm />} />
        <Route path='/signup' element={<SignupForm />} />

        <Route path='/dashboard/*' element={
          user ?
            <Dashboard />
            :
            <LoginForm />
        } /> */}
      </Routes>
    </>
  );
};

export default App;
